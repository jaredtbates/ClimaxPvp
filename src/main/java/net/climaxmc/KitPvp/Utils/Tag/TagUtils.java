package net.climaxmc.KitPvp.Utils.Tag;


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Kits.PvpKit2;
import net.climaxmc.KitPvp.Listeners.ScoreboardListener;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Tag.TagFiles;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.*;

public class TagUtils {

    private ClimaxPvp plugin;

    public TagUtils(ClimaxPvp plugin) {
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

    List<Player> deadPlayers = new ArrayList<Player>();
    List<Integer> deathCounts = new ArrayList<Integer>();

    TagFiles tagFiles = new TagFiles();

    public void createTag(Player player, int prize) {
        if (ClimaxPvp.isTagRunning || ClimaxPvp.isTagHosted) {
            player.sendMessage(ChatColor.RED + "Tag is already hosted!");
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
        ClimaxPvp.tagPrize = prize;
        ClimaxPvp.isTagHosted = true;
        Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is hosting "
                + ChatColor.GOLD + "Tag" + ChatColor.GRAY + " for " + ChatColor.GREEN + "$" + prize + "!");
        Bukkit.broadcastMessage(ChatColor.GRAY + "Do " + ChatColor.AQUA + "" + ChatColor.BOLD + "/tag join" + ChatColor.GRAY + " to join!");
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.NOTE_PLING, 1, 1);
        }
        taskid = ClimaxPvp.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                final int taskidCancel = taskid;
                countdown--;
                if (countdown <= 0) {
                    if (ClimaxPvp.inTag.size() < 2) {
                        Bukkit.getServer().getScheduler().cancelTask(taskidCancel);
                        countdown = countdownOriginal;
                        endTag();
                        return;
                    } else {
                        Bukkit.getServer().getScheduler().cancelTask(taskidCancel);
                        countdown = countdownOriginal;
                        startTag();
                    }
                }
                if (countdown == 10) {
                    for (Player players : ClimaxPvp.inTag) {
                        players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.AQUA + "Lowest deaths wins!");
                    }
                }
                for (Player players : ClimaxPvp.inTag) {
                    players.setLevel(countdown);
                    updateLobbyScoreboards();
                    if (countdown <= 5 && countdown != 0) {
                        players.sendMessage(ChatColor.GRAY + "Starting in " + ChatColor.WHITE + countdown + ChatColor.GRAY + " seconds!");
                        players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                    }
                }
            }
        },0L, 20L);
    }

    public void startTag() {
        ClimaxPvp.isTagRunning = true;
        int point = 0;

        updateInGameScoreboards();

        int randomItPicker = (int) (Math.random() * ClimaxPvp.inTag.size() - 1);
        Player it = ClimaxPvp.inTag.get(randomItPicker);
        setIt(it);

        for (Player players : ClimaxPvp.inTag) {
            point++;
            if (ClimaxPvp.inTagLobby.contains(players)) {
                players.teleport(tagFiles.getArenaPoint(point));
                players.setFoodLevel(20);
                players.setFireTicks(0);
                players.setHealth(20);
                //ClimaxPvp.playerPoint.put(point, players);
                ClimaxPvp.inTagLobby.remove(players);
                players.getInventory().clear();
                players.getInventory().setBoots(new I(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_FALL, 1));
                players.getInventory().addItem(new I(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 2));
                players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Tag has started! You have 3 minutes!");
                players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.AQUA + it.getName() + " has been chosen as IT! Run for your lives!");
            }
        }
        matchCountdown = 90;
        taskid2 = ClimaxPvp.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                final int taskid3 = taskid2;
                matchCountdown--;
                if (matchCountdown <= 0) {
                    endTagWithWinner();
                    Bukkit.getServer().getScheduler().cancelTask(taskid3);
                }
                /*if (matchCountdown % 10 == 0) {
                    for (int i : ClimaxPvp.tagPlayerDeaths.values()) {
                        ClimaxPvp.tagDeathsSorted.add(i);
                    }
                    Collections.sort(ClimaxPvp.tagDeathsSorted);
                    for (int deaths : ClimaxPvp.tagDeathsSorted) {
                        for (Player player : ClimaxPvp.tagPlayerDeaths.keySet()) {
                            if (ClimaxPvp.tagPlayerDeaths.get(player) == deaths) {
                                deadPlayers.add(player);
                                deathCounts.add(deaths);
                            }
                        }
                    }
                    for (Player players : ClimaxPvp.inTag) {
                        for (Player magic : deadPlayers) {
                            players.sendMessage(ChatColor.GRAY + magic.getName());
                        }
                        for (Integer integer : deathCounts) {
                            players.sendMessage(ChatColor.AQUA + integer);
                        }
                    }
                    ClimaxPvp.tagDeathsSorted.clear();
                }*/
                if (matchCountdown == 60) {
                    for (Player players : ClimaxPvp.inTag) {
                        players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.AQUA + "1:00" + ChatColor.GRAY + " remaining!");
                    }
                }
                if (matchCountdown == 30 || matchCountdown == 10) {
                    for (Player players : ClimaxPvp.inTag) {
                        players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.AQUA + "0:" + matchCountdown + ChatColor.GRAY + " remaining!");
                    }
                }
                if (matchCountdown <= 5 && matchCountdown != 0) {
                    for (Player players : ClimaxPvp.inTag) {
                        players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.AQUA + "0:0" + matchCountdown + "" + ChatColor.GRAY + " remaining!");
                    }
                }
                for (Player players : ClimaxPvp.inTag) {
                    players.setLevel(matchCountdown);
                }
            }
        },0L, 20L);
    }

    public void endTag() {
        if (countdown == 0) {
            Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Tag has ended! Not enough players (Needs at least 4)");
        }
        ClimaxPvp.isTagRunning = false;
        ClimaxPvp.isTagHosted = false;
        ClimaxPvp.isIt = null;
        ClimaxPvp.tagDeathsSorted.clear();
        for (Player player : ClimaxPvp.inTag) {
            setSpawnScoreboard(player);
        }
        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTag.contains(players)) {
                        ClimaxPvp.inTag.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagSpectators.contains(players)) {
                        ClimaxPvp.tagSpectators.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagWinners.contains(players)) {
                        ClimaxPvp.tagWinners.remove(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTagLobby.contains(players)) {
                        ClimaxPvp.inTagLobby.remove(players);
                    }
                }
            }
        }, 20L * 3);
        countdown = countdownOriginal;
        host.sendMessage(ChatColor.GRAY + "You have been given your money back.");
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(host);
        playerData.depositBalance(ClimaxPvp.tagPrize);
    }
    public void endTagWithWinner() {
        Player minPlayer = null;
        int minValue = Integer.MAX_VALUE;
        for (Player player : ClimaxPvp.tagPlayerDeaths.keySet()) {
            int value = ClimaxPvp.tagPlayerDeaths.get(player);
            if (value < minValue) {
                minValue = value;
                minPlayer = player;
            }
        }
        Player winner = minPlayer;
        for (Player player : ClimaxPvp.inTag) {
            if (!ClimaxPvp.tagPlayerDeaths.containsKey(player)) {
                winner = player;
            }
        }
        Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Tag has " + ChatColor.RED
                + "ended!" + ChatColor.GRAY + " Winner: " + ChatColor.GOLD + "" + ChatColor.BOLD + winner.getName());
        ClimaxPvp.isTagRunning = false;
        ClimaxPvp.isTagHosted = false;
        ClimaxPvp.isIt = null;
        ClimaxPvp.tagDeathsSorted.clear();
        for (Player player : ClimaxPvp.inTag) {
            setSpawnScoreboard(player);
        }
        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTag.contains(players)) {
                        ClimaxPvp.inTag.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagSpectators.contains(players)) {
                        ClimaxPvp.tagSpectators.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagWinners.contains(players)) {
                        ClimaxPvp.tagWinners.remove(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTagLobby.contains(players)) {
                        ClimaxPvp.inTagLobby.remove(players);
                    }
                }
            }
        }, 20L * 5);
        countdown = countdownOriginal;
        winner.sendMessage(ChatColor.GRAY + "You have received " + ChatColor.GREEN + "$" + ClimaxPvp.tagPrize + ChatColor.GRAY + " for winning!");
        winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1, 1);
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(winner);
        playerData.depositBalance(ClimaxPvp.tagPrize);
    }
    public void cancelTag(Player player) {
        Bukkit.broadcastMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Tag has been cancelled by " + ChatColor.GOLD + player.getName());
        ClimaxPvp.isTagRunning = false;
        ClimaxPvp.isTagHosted = false;
        ClimaxPvp.isIt = null;
        ClimaxPvp.tagDeathsSorted.clear();
        for (Player players : ClimaxPvp.inTag) {
            setSpawnScoreboard(players);
        }
        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTag.contains(players)) {
                        ClimaxPvp.inTag.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagSpectators.contains(players)) {
                        ClimaxPvp.tagSpectators.remove(players);
                        ClimaxPvp.getInstance().respawn(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.tagWinners.contains(players)) {
                        ClimaxPvp.tagWinners.remove(players);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (ClimaxPvp.inTagLobby.contains(players)) {
                        ClimaxPvp.inTagLobby.remove(players);
                    }
                }
            }
        }, 20L * 3);
        countdown = countdownOriginal;
        host.sendMessage(ChatColor.GRAY + "You have been given your money back.");
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(host);
        playerData.depositBalance(ClimaxPvp.tagPrize);
    }
    public void joinTag(Player player) {
        if (ClimaxPvp.isTagRunning) {
            player.sendMessage(ChatColor.RED + "Tag is currently in progress, do /tag spec to join!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        }
        if (!ClimaxPvp.isTagHosted) {
            player.sendMessage(ChatColor.RED + "There is no tag game currently running! Host one with /tag host!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        } else {
            if (ClimaxPvp.inTag.size() < 10) {
                ClimaxPvp.inTag.add(player);
                ClimaxPvp.inTagLobby.add(player);
                player.teleport(tagFiles.getLobbyPoint());
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                for (Player players : ClimaxPvp.inTag) {
                    players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has joined the lobby ["
                            + ChatColor.AQUA + ClimaxPvp.inTag.size() + ChatColor.GRAY + "/" + ChatColor.AQUA + "10" + ChatColor.GRAY + "]");
                }
                setLobbyScoreboard(player);
            } else {
                player.sendMessage(ChatColor.RED + "Tag is full!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            }
        }
    }
    public void spectateTag(Player player) {
        if (!ClimaxPvp.isTagHosted) {
            player.sendMessage(ChatColor.RED + "Tag must be hosted to spectate!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return;
        } else {
            ClimaxPvp.tagSpectators.add(player);
            player.teleport(tagFiles.getLobbyPoint());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            for (Player players : ClimaxPvp.inTag) {
                players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is spectating the lobby");
            }
        }
    }
    public void setIt(Player player) {
        ClimaxPvp.isIt = player;
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 100));
        for (Player players : ClimaxPvp.inTag) {
            players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " is now it!");
            players.playSound(players.getLocation(), Sound.NOTE_PLING, 1, 1);
        }
    }
    public void updateInGameScoreboards() {
        Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("Tag");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("Tag", "dummy");
        }
        for (Player players : ClimaxPvp.inTag) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "  &f> &c&lDeaths &f<  "));
            if (ClimaxPvp.tagPlayerDeaths.get(players) != null) {
                objective.getScore(players.getName()).setScore(ClimaxPvp.tagPlayerDeaths.get(players));
                players.setScoreboard(scoreboard);
            } else {
                objective.getScore(players.getName()).setScore(0);
                players.setScoreboard(scoreboard);
            }
        }
    }
    public void setSpawnScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("Player Data");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("Player Data", "dummy");
        }
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("  \u00A7f> \u00A76\u00A7lClimax\u00A7c\u00A7lPvP \u00A7f<  ");
        if (playerData != null) {
            objective.getScore("\u00A7f-]\u00A77\u00A7m---------\u00A7f[-").setScore(13);
            objective.getScore("\u00A7f» \u00A7aBalance:").setScore(12);
            String balance = "$" + new Double(playerData.getBalance()).intValue();
            objective.getScore(balance).setScore(11);
            objective.getScore(" ").setScore(10);
            objective.getScore("\u00A7f» \u00A7cKills:").setScore(9);
            objective.getScore(Integer.toString(ClimaxPvp.getInstance().getPlayerData(player).getKills())).setScore(8);
            objective.getScore("  ").setScore(7);
            objective.getScore("\u00A7f» \u00A7cDeaths:").setScore(6);
            objective.getScore(Integer.toString(ClimaxPvp.getInstance().getPlayerData(player).getDeaths())).setScore(5);
            objective.getScore("   ").setScore(4);
        }
        objective.getScore("\u00A7f» \u00A7eWebsite:").setScore(3);
        objective.getScore("climaxmc.net").setScore(2);
        objective.getScore("\u00A7f-]\u00A7f\u00A77\u00A7m---------\u00A7f[-").setScore(1);
        player.setScoreboard(scoreboard);
        /*Objective healthObjective = board.registerNewObjective("showhealth", "health");
        healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObjective.setDisplayName(ChatColor.RED + "\u2764");
        player.setHealth(player.getHealth());*/
    }
    public void updateLobbyScoreboards() {
        for (Player player : ClimaxPvp.inTag) {
            Scoreboard scoreboard = player.getScoreboard();
            Objective objective = scoreboard.getObjective("lobby");
            if (objective == null) {
                objective = scoreboard.registerNewObjective("lobby", "dummy");
            }
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "  &f> &bTag &f<  "));
            int lastCountdown = countdown - 1;
            scoreboard.resetScores(ChatColor.YELLOW + "Time Left: " + lastCountdown);
            objective.getScore(ChatColor.YELLOW + "Time Left: " + countdown).setScore(3);
        }
    }
    public void setLobbyScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("lobby");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("lobby", "dummy");
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "  &f> &bTag &f<  "));
        objective.getScore(ChatColor.YELLOW + "Time Left: " + countdown).setScore(3);
        player.setScoreboard(scoreboard);
    }
}