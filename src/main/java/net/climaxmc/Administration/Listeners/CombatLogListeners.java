package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CombatLogListeners implements Listener {
    private ClimaxPvp plugin;

    public CombatLogListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private Map<UUID, Integer> tagged = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if (!(tagged.containsKey(damager.getUniqueId()) && tagged.containsKey(damaged.getUniqueId()))) {
                tagged.put(damager.getUniqueId(), 10);
                tagged.put(damaged.getUniqueId(), 10);
                damager.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damaged.getName() + ChatColor.GRAY + ".");
                damaged.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damager.getName() + ChatColor.GRAY + ".");
                new BukkitRunnable() {
                    public void run() {
                        tagged.put(damager.getUniqueId(), tagged.get(damager.getUniqueId()) - 1);
                        if (tagged.get(damager.getUniqueId()) == 0) {
                            tagged.remove(damager.getUniqueId());
                            damager.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                        }

                        tagged.put(damaged.getUniqueId(), tagged.get(damaged.getUniqueId()) - 1);
                        if (tagged.get(damager.getUniqueId()) == 0) {
                            tagged.remove(damaged.getUniqueId());
                            damaged.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                        }
                    }
                }.runTaskTimer(plugin, 20, 20);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            player.damage(Integer.MIN_VALUE);
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + ChatColor.RED + " has logged out while in combat!");
        }
    }
}
