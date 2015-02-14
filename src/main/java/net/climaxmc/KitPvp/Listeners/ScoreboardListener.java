package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.Events.UpdateEvent;
import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

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
            Objective obje = board.registerNewObjective("§lPlayer Data", "dummy");
            obje.setDisplaySlot(DisplaySlot.SIDEBAR);
            board.registerNewTeam("Team");
            Objective obj = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
            obj.setDisplayName("§f§lClimaxPvp");
            int line = 7;
            obj.getScore("§a§lBalance").setScore(line--);
            String balance = "$" + new Double(plugin.getEconomy().getBalance(player)).intValue();
            //player.getScoreboard().resetScores(balance);
            plugin.getServer().getScoreboardManager().getMainScoreboard();
            obj.getScore(balance).setScore(line--);
            obj.getScore(" ").setScore(line--);
            //obj.getScore(String.valueOf(C.cGray) + C.Bold + "Stacker").setScore(line--);
            //player.getScoreboard().resetScores(this.Get(player).BestPig);
            //this.Get(player).BestPig = this._pigStacker;
            obj.getScore("§c§l Kills").setScore(line--);
            obj.getScore("0").setScore(line--);
            //player.getScoreboard().resetScores(this.Get(player).GetUltraText(false));
            obj.getScore("    ").setScore(line--);
            obj.getScore("§e§lWebsite").setScore(line--);
            obj.getScore("climaxmc.net").setScore(line--);
        }
    }
}
