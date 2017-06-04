package net.climaxmc.KitPvp.events.tournament;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.events.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class TournamentEvents implements Listener {
    private ClimaxPvp plugin;

    public TournamentEvents(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        EventManager eventManager = plugin.eventManager;
        TPManager tpManager = plugin.tpManager;
        TournamentFiles tournamentFiles = new TournamentFiles();
        TournamentManager tournamentManager = plugin.tournamentManager;

        if (eventManager.isInEvent(player.getUniqueId())) {

            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                public void run() {
                    ClimaxPvp.getInstance().respawn(player, tournamentFiles.getDeathPoint());
                    player.getInventory().clear();
                }
            }, 5L);

            if (tournamentManager.tourneyWinners.size() < 1 && tournamentManager.areSlotsEmpty()) {
                tournamentManager.end(killer);
                return;
            }

            tournamentManager.tourneyWinners.add(killer);
            killer.teleport(tournamentFiles.getWinPoint());
            killer.setHealth(20);
            killer.setFireTicks(0);
            for (PotionEffect effect : killer.getActivePotionEffects()) {
                killer.removePotionEffect(effect.getType());
            }
            killer.getInventory().clear();
            killer.getInventory().setArmorContents(null);

            tournamentManager.nextMatchTimer();
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        TournamentManager tournamentManager = plugin.tournamentManager;
        EventManager eventManager = plugin.eventManager;

        for (int i = 1; i <= 10; i++) {
            if (tournamentManager.playerPoint.get(i) != null && tournamentManager.playerPoint.get(i).equals(player)) {
                Player quitter = tournamentManager.playerPoint.get(i);
                tournamentManager.playerPoint.remove(i);
                eventManager.removePlayer(quitter.getUniqueId());
            }
        }
    }
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        /*if (event.getMessage().toLowerCase().startsWith("/tourney")) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "tournament are currently disabled!");
            event.setCancelled(true);
        }*/
        EventManager eventManager = plugin.eventManager;
        if (eventManager.isInEvent(player.getUniqueId())) {
            if (!event.getMessage().toLowerCase().startsWith("/event")) {
                player.sendMessage(ChatColor.RED + "You can only do event commands while in a tournament! Do /event leave to leave!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                event.setCancelled(true);
            }
        }
    }
}