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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
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
        objective.setDisplayName("  §f> §6§lClimax§c§lPvP §f<  ");
        if (playerData != null) {
            objective.getScore("§f-]§7§m---------§f[-").setScore(13);
            objective.getScore("§f» §aBalance:").setScore(12);
            String balance = "$" + new Double(playerData.getBalance()).intValue();
            objective.getScore(balance).setScore(11);
            balances.put(player.getUniqueId(), new Double(playerData.getBalance()).intValue());
            objective.getScore(" ").setScore(10);
            objective.getScore("§f» §cKills:").setScore(9);
            objective.getScore(Integer.toString(plugin.getPlayerData(player).getKills())).setScore(8);
            kills.put(player.getUniqueId(), playerData.getKills());
            objective.getScore("  ").setScore(7);
            objective.getScore("§f» §cDeaths:").setScore(6);
            deaths.put(player.getUniqueId(), playerData.getDeaths());
            objective.getScore(Integer.toString(plugin.getPlayerData(player).getDeaths())).setScore(5);
            objective.getScore("   ").setScore(4);
        }
        objective.getScore("§f» §eWebsite:").setScore(3);
        objective.getScore("climaxmc.net").setScore(2);
        objective.getScore("§f-]§f§7§m---------§f[-").setScore(1);
        /*Objective healthObjective = board.registerNewObjective("showhealth", "health");
        healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObjective.setDisplayName(ChatColor.RED + "\u2764");
        player.setHealth(player.getHealth());*/
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
            objective.setDisplayName("  §f> §6§lClimax§c§lPvP §f<  ");
            objective.getScore("§f-]§7§m---------§f[-").setScore(13);
            objective.getScore("§f» §aBalance:").setScore(12);
            if (balances.containsKey(player.getUniqueId())) {
                board.resetScores("$" + balances.get(player.getUniqueId()));
                String balance = "$" + new Double(playerData.getBalance()).intValue();
                objective.getScore(balance).setScore(11);
            }
            balances.put(player.getUniqueId(), new Double(playerData.getBalance()).intValue());
            objective.getScore(" ").setScore(10);
            objective.getScore("§f» §cKills:").setScore(9);
            if (kills.containsKey(player.getUniqueId())) {
                board.resetScores(Integer.toString(kills.get(player.getUniqueId())));
                objective.getScore(Integer.toString(plugin.getPlayerData(player).getKills())).setScore(8);
            }
            kills.put(player.getUniqueId(), playerData.getKills());
            objective.getScore("  ").setScore(7);
            objective.getScore("§f» §cDeaths:").setScore(6);
            if (deaths.containsKey(player.getUniqueId())) {
                board.resetScores(Integer.toString(deaths.get(player.getUniqueId())));
                objective.getScore(Integer.toString(plugin.getPlayerData(player).getDeaths())).setScore(5);
            }
            deaths.put(player.getUniqueId(), playerData.getDeaths());
            objective.getScore("   ").setScore(4);
            objective.getScore("§f» §eWebsite:").setScore(3);
            objective.getScore("climaxmc.net").setScore(2);
            objective.getScore("§f-]§f§7§m---------§f[-").setScore(1);
        }
    }
}
