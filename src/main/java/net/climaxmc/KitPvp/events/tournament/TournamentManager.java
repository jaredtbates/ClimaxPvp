package net.climaxmc.KitPvp.events.tournament;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Kits.PvpKit2;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.events.EventManager;
import net.climaxmc.KitPvp.events.EventState;
import net.climaxmc.KitPvp.events.EventType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class TournamentManager {

    private ClimaxPvp plugin;
    public TournamentManager(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private int countdown = 30;

    public HashMap<Integer, Player> playerPoint = new HashMap<>();
    public ArrayList<Player> tourneyWinners = new ArrayList<>();

    public void start(UUID host, Kit kit) {
        EventManager eventManager = plugin.eventManager;
        eventManager.startEvent(host, EventType.TOURNAMENT, kit);

        BukkitTask countdownTimer = new BukkitRunnable() {
            @Override
            public void run() {
                countdown--;
                if (countdown <= 0) {
                    if (eventManager.getPlayers().size() < 4) {
                        end();
                        this.cancel();
                        return;
                    } else {
                        this.cancel();
                        startPreGame();
                    }
                }
                for (UUID uuid : eventManager.getPlayers()) {
                    Player player = Bukkit.getPlayer(uuid);
                    player.setLevel(countdown);
                    if (countdown <= 5 && countdown != 0) {
                        player.sendMessage(ChatUtils.color("&f\u00BB &7Starting in &f" + countdown + " &7seconds!"));
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void end() {
        EventManager eventManager = plugin.eventManager;
        resetVariables();
        eventManager.cancelEvent();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThe tournament has been canceled!"));
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
        }
    }
    public void end(Player winner) {
        EventManager eventManager = plugin.eventManager;
        resetVariables();
        eventManager.cancelEvent();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.color("&f\u00BB &6" + winner.getName() + " &7has won the tournament and received &a$200"));
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
        }
    }

    public void resetVariables() {
        countdown = 30;
    }

    public void join(UUID uuid) {
        EventManager eventManager = plugin.eventManager;
        Player player = Bukkit.getPlayer(uuid);

        //

        if (eventManager.getState().equals(EventState.LOBBY)) {
            if (eventManager.getPlayers().size() < 10) {
                eventManager.getEvent().getPlayers().add(uuid);
                player.teleport(tournamentFiles.getLobbyPoint());
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                for (UUID uuids : eventManager.getAllPlayers()) {
                    Player players = Bukkit.getPlayer(uuids);
                    players.sendMessage(ChatUtils.color("&f\u00BB &6" + player.getName() + " &7has joined the lobby [&b" + eventManager.getPlayers().size() + "&7/"
                            + "&b10" + "&7]"));
                }
            } else {
                player.sendMessage(ChatUtils.color("&f\u00BB &cThe tournament is full!"));
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            }
        } else {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThe tournament is currently in-game, do /event spec to spectate!"));
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }
    public void leave(UUID uuid) {
        EventManager eventManager = plugin.eventManager;
        Player player = Bukkit.getPlayer(uuid);

        if (eventManager.isRunning()) {
            eventManager.getEvent().getPlayers().remove(uuid);
            plugin.respawn(player);
            for (UUID uuids : eventManager.getAllPlayers()) {
                Player players = Bukkit.getPlayer(uuids);
                players.sendMessage(ChatUtils.color("&f\u00BB &6" + player.getName() + " &7has left the lobby [&b" + eventManager.getPlayers().size() + "&7/"
                        + "&b10" + "&7]"));
            }
        } else {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThere is no tournament hosted to leave!"));
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }

    public void spectate(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        EventManager eventManager = plugin.eventManager;
        eventManager.getEvent().getSpectators().add(uuid);
        player.teleport(tournamentFiles.getLobbyPoint());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        //

        for (UUID uuids : eventManager.getPlayers()) {
            Player players = Bukkit.getPlayer(uuids);
            players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is spectating the lobby");
        }
        for (UUID uuids : eventManager.getSpectators()) {
            Player spectators = Bukkit.getPlayer(uuids);
            spectators.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is spectating the lobby");
        }
    }

    TournamentFiles tournamentFiles = new TournamentFiles();

    public void startPreGame() {
        resetVariables();
        EventManager eventManager = plugin.eventManager;
        TPManager tpManager = plugin.tpManager;

        int point = 0;
        for (UUID uuid : eventManager.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(tournamentFiles.getArenaPoint(++point));
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.setHealth(20);
            playerPoint.put(point, player);
            tpManager.getTP(player).setState(TPState.INGAME);
        }
        eventManager.setState(EventState.LOBBY);
        nextMatchTimer();
    }

    private int matchCountdown = 5;

    public void nextMatchTimer() {
        BukkitTask matchTimer = new BukkitRunnable() {
            @Override
            public void run() {
                matchCountdown--;
                if (matchCountdown <= 0) {
                    this.cancel();
                    startNextMatch();
                    matchCountdown = 5;
                    return;
                }
                EventManager eventManager = plugin.eventManager;
                for (UUID uuid : eventManager.getPlayers()) {
                    Player player = Bukkit.getPlayer(uuid);
                    player.setLevel(matchCountdown);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public boolean areSlotsEmpty() {
        for (int i = 1; i <= 10; i++) {
            if (playerPoint.get(i) != null && playerPoint.get(i + 1) != null) {
                return false;
            } else if (playerPoint.get(i) != null && playerPoint.get(i + 1) == null) {
                return false;
            }
        }
        return true;
    }

    public void startNextMatch() {
        EventManager eventManager = plugin.eventManager;
        for (int i = 1; i <= 10; i++) {
            if (playerPoint.get(i) != null && playerPoint.get(i + 1) != null) {
                Player player1 = playerPoint.get(i);
                Player player2 = playerPoint.get(i + 1);

                player1.teleport(tournamentFiles.getDuelPoint(1));
                player2.teleport(tournamentFiles.getDuelPoint(2));

                PvpKit2 pvpKit2 = new PvpKit2();
                pvpKit2.wear(player1);
                pvpKit2.wear(player2);

                playerPoint.remove(i, playerPoint.get(i));
                playerPoint.remove(i + 1, playerPoint.get(i + 1));

                for (UUID uuid : eventManager.getPlayers()) {
                    Player player = Bukkit.getPlayer(uuid);
                    player.sendMessage(ChatColor.GRAY + "Match: " + ChatColor.GOLD + player1.getName() + ChatColor.GRAY + " vs " + ChatColor.GOLD + player2.getName());
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 2);
                }
                break;
            } else if (playerPoint.get(i) != null && playerPoint.get(i + 1) == null) {
                Player player = playerPoint.get(i);
                player.sendMessage("You don't appear to have an opponent so... you automatically win!");
                tourneyWinners.add(player);
                player.teleport(tournamentFiles.getWinPoint());
                player.setHealth(20);
                player.setFireTicks(0);
                playerPoint.remove(i, playerPoint.get(i));
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.getInventory().clear();
                nextMatchTimer();
            } else if (i >= 10) {
                if (tourneyWinners != null && tourneyWinners.size() > 0) {
                    Player winner = tourneyWinners.get(0);
                    winner.teleport(tournamentFiles.getDuelPoint(1));
                    Player winner2 = tourneyWinners.get(1);
                    winner2.teleport(tournamentFiles.getDuelPoint(2));

                    PvpKit2 pvpKit2 = new PvpKit2();
                    pvpKit2.wear(winner);
                    pvpKit2.wear(winner2);

                    for (UUID uuid : eventManager.getPlayers()) {
                        Player player = Bukkit.getPlayer(uuid);
                        player.sendMessage(ChatColor.GRAY + "Match: " + ChatColor.GOLD + winner.getName() + ChatColor.GRAY + " vs " + ChatColor.GOLD + winner2.getName());
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 2);
                    }

                    tourneyWinners.remove(winner);
                    tourneyWinners.remove(winner2);
                }
            }
        }
    }
}
