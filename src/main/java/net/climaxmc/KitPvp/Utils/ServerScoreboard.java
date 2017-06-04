package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class ServerScoreboard {

    private Scoreboard board;
    private Objective objective;
    private Player player;

    public ServerScoreboard(Player player) {

        /**
         * Setting object variables per player
         */
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
        objective = board.registerNewObjective("Player Data", "dummy");
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f> &c&lClimax&f&lPvP &f<"));
        this.player = player;
        registerTeams();
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @SuppressWarnings("deprecation")
    private void registerTeams() {

        /**
         * Teams are being registered to use prefixes/suffixes which allow lines longer than the player name limit of 16.
         * Registering in this case simply refers to the adding of OfflinePlayers as constants on the board to update
         * with prefixes/suffixes later.
         */
        killstreak = board.registerNewTeam("killstreak");
        killstreak.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&6Killstrea")));

        kills = board.registerNewTeam("kills");
        kills.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&6Kil")));

        deaths = board.registerNewTeam("deaths");
        deaths.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&6Deat")));

        nextTier = board.registerNewTeam("nextTier");
        nextTier.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&eNex")));

        progress = board.registerNewTeam("progress");
        progress.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&eProgre")));

        updateTeams();

        /**
         * Top and bottom have slightly different lengths so as to not interfear with each other
         */
        Team top = board.registerNewTeam("top");
        top.setPrefix(ChatUtils.color("&7&m------"));
        top.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&7&m---------")));

        Team bottom = board.registerNewTeam("bottom");
        bottom.setPrefix(ChatUtils.color("&7&m-----"));
        bottom.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("&7&m----------")));

        Team website = board.registerNewTeam("website");
        website.setPrefix(ChatUtils.color("&7www.cli"));
        website.addPlayer(Bukkit.getOfflinePlayer(ChatUtils.color("maxmc.net")));
    }

    private Team killstreak, kills, deaths, nextTier, progress;

    private void updateTeams() {

        /**
         * Even registering a team prefix with more than 16 characters will kick you from the server with
         * an error that, to be honest, doesn't really help and makes you sit for like 20 minutes trying to figure out
         * what's wrong until you finally figure it out and feel dumb but also mad at the way this crap was coded.
         */

        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);

        String nextTierString = ChatUtils.color("&9Blue");

        if (playerData.getKills() >= 100) {
            nextTierString = ChatUtils.color("&aGreen");
        }
        if (playerData.getKills() >= 300) {
            nextTierString = ChatUtils.color("&cRed");
        }
        if (playerData.getKills() >= 500) {
            nextTierString = ChatUtils.color("&6Gold");
        }
        if (playerData.getKills() >= 700) {
            nextTierString = ChatUtils.color("&5Purple");
        }
        if (playerData.getKills() >= 1000) {
            nextTierString = ChatUtils.color("&4Max");
        }

        String killsRequired = Integer.toString(100);
        if (playerData.getKills() >= 100) {
            killsRequired = Integer.toString(300);
        }
        if (playerData.getKills() >= 300) {
            killsRequired = Integer.toString(500);
        }
        if (playerData.getKills() >= 500) {
            killsRequired = Integer.toString(700);
        }
        if (playerData.getKills() >= 700) {
            killsRequired = Integer.toString(1000);
        }
        if (playerData.getKills() >= 1000) {
            killsRequired = "Max";
        }

        if (!KitPvp.killStreak.containsKey(player.getUniqueId())) {
            KitPvp.killStreak.put(player.getUniqueId(), 0);
        }
        int killstreakInt = KitPvp.killStreak.get(player.getUniqueId());

        killstreak.setSuffix(ChatUtils.color("k: &f" + killstreakInt));
        kills.setSuffix(ChatUtils.color("ls: &f" + playerData.getKills()));
        deaths.setSuffix(ChatUtils.color("hs: &f" + playerData.getDeaths()));
        nextTier.setSuffix(ChatUtils.color("t: " + nextTierString));
        progress.setSuffix(ChatUtils.color("ss: &f" + playerData.getKills() + "/" + killsRequired));
    }

    public void updateScoreboard() {

        /**
         * Sets scores for buffer, then swaps out for the buffer objective, then updates the new buffer scores.
         */
        updateTeams();
        setScores();
    }

    private void setScores() {

        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);

        String tier = ChatUtils.color("&7Gray");

        if (playerData.getKills() >= 100) {
            tier = ChatUtils.color("&9Blue");
        }
        if (playerData.getKills() >= 300) {
            tier = ChatUtils.color("&aGreen");
        }
        if (playerData.getKills() >= 500) {
            tier = ChatUtils.color("&cRed");
        }
        if (playerData.getKills() >= 700) {
            tier = ChatUtils.color("&6Gold");
        }
        if (playerData.getKills() >= 1000) {
            tier = ChatUtils.color("&5Purple");
        }

        List<String> scoreboardLines = new ArrayList<>();

        scoreboardLines.add(ChatUtils.color("&7&m---------"));
        scoreboardLines.add(ChatUtils.color("&6Kil"));
        scoreboardLines.add(ChatUtils.color("&6Deat"));
        scoreboardLines.add(ChatUtils.color("&6Killstrea"));
        scoreboardLines.add(" ");
        scoreboardLines.add(ChatUtils.color("&eTier: " + tier));
        scoreboardLines.add(ChatUtils.color("&eNex"));
        scoreboardLines.add(ChatUtils.color("&eProgre"));
        scoreboardLines.add("  ");
        scoreboardLines.add(ChatUtils.color("maxmc.net"));
        scoreboardLines.add(ChatUtils.color("&7&m----------"));

        int line = scoreboardLines.size();
        for (String entry : scoreboardLines) {
            setScore(entry, line);
            line--;
        }
        for (String s : board.getEntries()) {
            if (!scoreboardLines.contains(s))
                board.resetScores(s);
        }
    }

    private void setScore(String name, int score) {
        objective.getScore(name).setScore(score);
    }

    /**
     * No flicker yay (actually does flicker cus prefixes like once every minute so idc)
     */
    /*private void swapBuffer() {
        buffer.setDisplaySlot(DisplaySlot.SIDEBAR);
        buffer.setDisplayName(objective.getDisplayName());
        t = objective;
        objective = buffer;
        buffer = t;
    }*/
}
