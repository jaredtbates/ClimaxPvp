package net.climaxmc.KitPvp.Utils.Tournaments;


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Kits.PvpKit2;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Iterator;

public class TournamentUtils {

    private ClimaxPvp plugin;

    public TournamentUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private Player host;
    private String kit = "";
    private int prize;

    private int taskid;
    private int taskid2;
    private int countdown = 25;
    private int countdownOriginal = 25;
    private int matchCountdown = 5;

    TournamentFiles tournamentFiles = new TournamentFiles();

    public void createTourney(Player player, int prize) {
        if (ClimaxPvp.isTourneyRunning) {
            player.sendMessage(ChatColor.RED + "A tournament is already running!");
            return;
        }
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
        if (playerData.getBalance() >=  prize) {
            playerData.withdrawBalance(prize);
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Insufficient funds!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        }
        this.host = player;
        this.prize = prize;
        ClimaxPvp.tourneyPrize = prize;
        ClimaxPvp.isTourneyHosted = true;
        Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is hosting "
                + ChatColor.GOLD + "Tourney" + ChatColor.GRAY + " for " + ChatColor.GREEN + "$" + prize + "!");
        Bukkit.broadcastMessage(ChatColor.GRAY + "Do " + ChatColor.AQUA + "" + ChatColor.BOLD + "/tourney join" + ChatColor.GRAY + " to join!");
        taskid = ClimaxPvp.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                Bukkit.broadcastMessage("debug0");
                final int taskid2 = taskid;
                countdown--;
                if (countdown <= 0) {
                    if (ClimaxPvp.inTourney.size() < 3) {
                        endTourney();
                        Bukkit.getServer().getScheduler().cancelTask(taskid2);
                        countdown = countdownOriginal;
                        return;
                    } else {
                        startTourney();
                        Bukkit.getServer().getScheduler().cancelTask(taskid2);
                        countdown = countdownOriginal;
                    }
                }
                for (Player players : ClimaxPvp.inTourney) {
                    players.setLevel(countdown);
                    if (countdown <= 5 && countdown != 0) {
                        players.sendMessage(ChatColor.GRAY + "Starting in " + ChatColor.WHITE + countdown + ChatColor.GRAY + " seconds!");
                        players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                    }
                }
            }
        },0L, 20L);
    }

    public void startTourney() {
        ClimaxPvp.isTourneyRunning = true;
        int point = 0;
        for (Player players : ClimaxPvp.inTourney) {
            point++;
            if (ClimaxPvp.inTourneyLobby.contains(players)) {
                players.teleport(tournamentFiles.getArenaPoint(point));
                players.setFoodLevel(20);
                players.setFireTicks(0);
                players.setHealth(20);
                ClimaxPvp.playerPoint.put(point, players);
                ClimaxPvp.inTourneyLobby.remove(players);
            }
        }
        startNextMatchTimer();
    }

    public void startNextMatch() {
        for (int i = 1; i <= 10; i++) {
            if (ClimaxPvp.playerPoint.get(i) != null && ClimaxPvp.playerPoint.get(i + 1) != null) {
                Player player1 = ClimaxPvp.playerPoint.get(i);
                Player player2 = ClimaxPvp.playerPoint.get(i + 1);

                player1.teleport(tournamentFiles.getDuelPoint(1));
                player2.teleport(tournamentFiles.getDuelPoint(2));

                PvpKit2 pvpKit2 = new PvpKit2();
                pvpKit2.wear(player1);
                pvpKit2.wear(player2);

                ClimaxPvp.playerPoint.remove(i, ClimaxPvp.playerPoint.get(i));
                ClimaxPvp.playerPoint.remove(i + 1, ClimaxPvp.playerPoint.get(i + 1));

                for (Player players : ClimaxPvp.inTourney) {
                    players.sendMessage(ChatColor.GRAY + "Match: " + ChatColor.GOLD + player1.getName() + ChatColor.GRAY + " vs " + ChatColor.GOLD + player2.getName());
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 2);
                }
                break;
            } else if (ClimaxPvp.playerPoint.get(i) != null && ClimaxPvp.playerPoint.get(i + 1) == null) {
                Player player = ClimaxPvp.playerPoint.get(i);
                player.sendMessage("You don't appear to have an opponent so... you automatically win!");
                ClimaxPvp.tourneyWinners.add(player);
                player.teleport(tournamentFiles.getWinPoint());
                ClimaxPvp.playerPoint.remove(i, ClimaxPvp.playerPoint.get(i));
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.getInventory().clear();
                startNextMatchTimer();
            } else if (i >= 10) {
                ClimaxPvp.getInstance().getServer().getScheduler().cancelTask(taskid2);
                if (ClimaxPvp.tourneyWinners != null && ClimaxPvp.tourneyWinners.size() > 0) {
                    Player winner = ClimaxPvp.tourneyWinners.get(0);
                    winner.teleport(tournamentFiles.getDuelPoint(1));
                    Player winner2 = ClimaxPvp.tourneyWinners.get(1);
                    winner2.teleport(tournamentFiles.getDuelPoint(2));

                    PvpKit2 pvpKit2 = new PvpKit2();
                    pvpKit2.wear(winner);
                    pvpKit2.wear(winner2);

                    for (Player players : ClimaxPvp.inTourney) {
                        players.sendMessage(ChatColor.GRAY + "Match: " + ChatColor.GOLD + winner.getName() + ChatColor.GRAY + " vs " + ChatColor.GOLD + winner2.getName());
                        players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 2);
                    }

                    ClimaxPvp.tourneyWinners.remove(winner);
                    ClimaxPvp.tourneyWinners.remove(winner2);
                }
            }
        }
    }
    public void endTourney() {
        if (countdown == 0) {
            Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "The tournament has ended! Not enough players (Needs at least 4)");
        }
        ClimaxPvp.isTourneyRunning = false;
        ClimaxPvp.isTourneyHosted = false;
        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTourney.contains(players)) {
                        ClimaxPvp.inTourney.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tourneySpectators.contains(players)) {
                        ClimaxPvp.tourneySpectators.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tourneyWinners.contains(players)) {
                        ClimaxPvp.tourneyWinners.remove(players);
                    }
                }
            }
        }, 20L * 3);
        countdown = countdownOriginal;
        host.sendMessage(ChatColor.GRAY + "You have been given your money back.");
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(host);
        playerData.depositBalance(ClimaxPvp.tourneyPrize);
    }
    public void endTourney(Player winner) {
        Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "The tournament has ended! Winner: " + ChatColor.GOLD + winner.getName());
        ClimaxPvp.isTourneyRunning = false;
        ClimaxPvp.isTourneyHosted = false;
        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTourney.contains(players)) {
                        ClimaxPvp.inTourney.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tourneySpectators.contains(players)) {
                        ClimaxPvp.tourneySpectators.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tourneyWinners.contains(players)) {
                        ClimaxPvp.tourneyWinners.remove(players);
                    }
                }
            }
        }, 20L * 5);
        countdown = countdownOriginal;
        winner.sendMessage(ChatColor.GRAY + "You have received " + ChatColor.GREEN + "$" + ClimaxPvp.tourneyPrize + ChatColor.GRAY + " for winning!");
        winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1, 1);
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(winner);
        playerData.depositBalance(ClimaxPvp.tourneyPrize);
    }
    public void cancelTourney(Player player) {
        if (ClimaxPvp.isTourneyHosted) {
            Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "The tournament has been cancelled by " + ChatColor.GOLD + player.getName());
            ClimaxPvp.isTourneyRunning = false;
            ClimaxPvp.isTourneyHosted = false;
            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                public void run() {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (ClimaxPvp.inTourney.contains(players)) {
                            ClimaxPvp.inTourney.remove(players);
                            ClimaxPvp.getInstance().respawn(players);
                        }
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (ClimaxPvp.tourneySpectators.contains(players)) {
                            ClimaxPvp.tourneySpectators.remove(players);
                            ClimaxPvp.getInstance().respawn(players);
                        }
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (ClimaxPvp.tourneyWinners.contains(players)) {
                            ClimaxPvp.tourneyWinners.remove(players);
                        }
                    }
                }
            }, 20L * 3);
            countdown = countdownOriginal;
            host.sendMessage(ChatColor.GRAY + "You have been given your money back.");
            PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(host);
            playerData.depositBalance(ClimaxPvp.tourneyPrize);
        } else {
            player.sendMessage(ChatColor.RED + "There is no tournament currently running!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }
    public void joinTourney(Player player) {
        if (ClimaxPvp.isTourneyRunning) {
            player.sendMessage(ChatColor.RED + "A tournament is currently in progress, do /tourney spec to join!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        }
        if (!ClimaxPvp.isTourneyHosted) {
            player.sendMessage(ChatColor.RED + "There is no tournament currently running! Host one with /tourney host!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        } else {
            if (ClimaxPvp.inTourney.size() < 10) {
                ClimaxPvp.inTourney.add(player);
                ClimaxPvp.inTourneyLobby.add(player);
                player.teleport(tournamentFiles.getLobbyPoint());
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                for (Player players : ClimaxPvp.inTourney) {
                    players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has joined the lobby ["
                            + ChatColor.AQUA + ClimaxPvp.inTourney.size() + ChatColor.GRAY + "/" + ChatColor.AQUA + "10" + ChatColor.GRAY + "]");
                }
            } else {
                player.sendMessage(ChatColor.RED + "The tournament is full!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            }
        }
    }
    public void spectateTourney(Player player) {
        if (!ClimaxPvp.isTourneyHosted) {
            player.sendMessage(ChatColor.RED + "A tournament must be hosted to spectate!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        } else {
            ClimaxPvp.tourneySpectators.add(player);
            player.teleport(tournamentFiles.getLobbyPoint());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            for (Player players : ClimaxPvp.inTourney) {
                players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is spectating the lobby");
            }
        }
    }
    public void startNextMatchTimer() {
        matchCountdown = 3;
        taskid2 = ClimaxPvp.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                final int taskid3 = taskid2;
                matchCountdown--;
                if (matchCountdown <= 0) {
                    startNextMatch();
                    Bukkit.getServer().getScheduler().cancelTask(taskid3);
                    matchCountdown = 5;
                }
                for (Player players : ClimaxPvp.inTourney) {
                    players.setLevel(matchCountdown);
                }
            }
        },0L, 20L);
    }
    public boolean areSlotsEmpty() {
        for (int i = 1; i <= 10; i++) {
            if (ClimaxPvp.playerPoint.get(i) != null && ClimaxPvp.playerPoint.get(i + 1) != null) {
                return false;
            } else if (ClimaxPvp.playerPoint.get(i) != null && ClimaxPvp.playerPoint.get(i + 1) == null) {
                return false;
            }
        }
        return true;
    }
}
