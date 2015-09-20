package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.events.PlayerBalanceChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {
    private ClimaxPvp plugin;
    private Map<UUID, Integer> balances = new HashMap<>();
    private Map<UUID, Integer> kills = new HashMap<>();
    private Map<UUID, Integer> deaths = new HashMap<>();

    public ScoreboardListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(board);
        Objective objective = board.registerNewObjective("Player Data", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        board.registerNewTeam("Team");
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Climax" + ChatColor.RED + "" + ChatColor.BOLD + "Pvp");
        if (playerData != null) {
            objective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Balance").setScore(11);
            String balance = "$" + new Double(playerData.getBalance()).intValue();
            objective.getScore(balance).setScore(10);
            balances.put(player.getUniqueId(), new Double(playerData.getBalance()).intValue());
            objective.getScore(" ").setScore(9);
            objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills").setScore(8);
            objective.getScore(Integer.toString(plugin.getPlayerData(player).getKills())).setScore(7);
            kills.put(player.getUniqueId(), playerData.getKills());
            objective.getScore("  ").setScore(6);
            objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Deaths").setScore(5);
            deaths.put(player.getUniqueId(), playerData.getDeaths());
            objective.getScore(Integer.toString(plugin.getPlayerData(player).getDeaths())).setScore(4);
            objective.getScore("   ").setScore(3);
        }
        objective.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Website").setScore(2);
        objective.getScore("climaxmc.net").setScore(1);
        Objective healthObjective = board.registerNewObjective("showhealth", "health");
        healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObjective.setDisplayName(ChatColor.RED + "\u2764");
        player.setHealth(player.getHealth());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        updateScoreboards();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBalanceChange(PlayerBalanceChangeEvent event) {
        updateScoreboards();
    }

    public void updateScoreboards() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            PlayerData playerData = plugin.getPlayerData(player);
            Scoreboard board = player.getScoreboard();
            Objective objective = board.getObjective("Player Data");
            objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "ClimaxPvp");
            objective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Balance").setScore(11);
            if (balances.containsKey(player.getUniqueId())) {
                board.resetScores("$" + balances.get(player.getUniqueId()));
                String balance = "$" + new Double(playerData.getBalance()).intValue();
                objective.getScore(balance).setScore(10);
            }
            balances.put(player.getUniqueId(), new Double(playerData.getBalance()).intValue());
            objective.getScore(" ").setScore(9);
            objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills").setScore(8);
            if (kills.containsKey(player.getUniqueId())) {
                board.resetScores(Integer.toString(kills.get(player.getUniqueId())));
                objective.getScore(Integer.toString(plugin.getPlayerData(player).getKills())).setScore(7);
            }
            kills.put(player.getUniqueId(), playerData.getKills());
            objective.getScore("  ").setScore(6);
            objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Deaths").setScore(5);
            if (deaths.containsKey(player.getUniqueId())) {
                board.resetScores(Integer.toString(deaths.get(player.getUniqueId())));
                objective.getScore(Integer.toString(plugin.getPlayerData(player).getDeaths())).setScore(4);
            }
            deaths.put(player.getUniqueId(), playerData.getDeaths());
            objective.getScore("   ").setScore(3);
            objective.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Website").setScore(2);
            objective.getScore("climaxmc.net").setScore(1);
        }
    }
}
