package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.Events.UpdateEvent;
import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

public class ScoreboardListener implements Listener {
    private ClimaxPvp plugin;

    public ScoreboardListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

    @EventHandler
    public void onUpdate(UpdateEvent event) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(board);
            Objective objective = board.registerNewObjective("§lPlayer Data", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            board.registerNewTeam("Team");
            Objective obj = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§f§lClimaxPvp");
            int line = 11;
            obj.getScore("§a§lBalance").setScore(line--);
            String balance = "$" + new Double(plugin.getEconomy().getBalance(player)).intValue();
            obj.getScore(balance).setScore(line--);
            obj.getScore(" ").setScore(line--);
            obj.getScore("§c§lKills").setScore(line--);
            obj.getScore(Integer.toString(plugin.getStatistics(player).getKills())).setScore(line--);
            obj.getScore(" ").setScore(line--);
            obj.getScore("§c§lDeaths").setScore(line--);
            obj.getScore(Integer.toString(plugin.getStatistics(player).getDeaths())).setScore(line--);
            obj.getScore("    ").setScore(line--);
            obj.getScore("§e§lWebsite").setScore(line--);
            obj.getScore("climaxmc.net").setScore(line);
        }
    }
}
