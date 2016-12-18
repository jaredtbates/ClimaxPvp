package net.climaxmc.KitPvp.Utils.Tournaments;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TournamentEvents implements Listener {
    private ClimaxPvp plugin;

    public TournamentEvents(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    TournamentFiles tournamentFiles = new TournamentFiles();
    TournamentUtils tournamentUtils = new TournamentUtils(plugin);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (ClimaxPvp.inTourney.contains(player)) {

            ClimaxPvp.inTourney.remove(player);
            player.teleport(tournamentFiles.getDeathPoint());

            if (ClimaxPvp.tourneyWinners.size() < 2) {
                tournamentUtils.endTourney(killer);
                return;
            }

            ClimaxPvp.tourneyWinners.add(killer);
            killer.teleport(tournamentFiles.getWinPoint());

            Bukkit.broadcastMessage(Integer.toString(ClimaxPvp.tourneyWinners.size()));

            tournamentUtils.startNextMatchTimer();
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (int i = 1; i <= 10; i++) {
            if (ClimaxPvp.playerPoint.get(i) != null && ClimaxPvp.playerPoint.get(i).equals(player)) {
                Player player1 = ClimaxPvp.playerPoint.get(i);
                ClimaxPvp.playerPoint.remove(i);
                ClimaxPvp.inTourney.remove(player1);
            }
        }
    }
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        /*if (event.getMessage().contains("tourney")) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Tournaments are currently disabled!");
            event.setCancelled(true);
        }*/
        if (ClimaxPvp.inTourney.contains(player)) {
            if (!event.getMessage().contains("tourney")) {
                player.sendMessage(ChatColor.RED + "You can only do /tourney while in a tournament! Do /tourney leave to leave!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                event.setCancelled(true);
            }
        }
    }
}