package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CombatLogListeners implements Listener {
    private ClimaxPvp plugin;

    public CombatLogListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private Map<UUID, Long> tagged = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (!tagged.containsKey(damager.getUniqueId())) {
                damager.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damaged.getName() + ChatColor.GRAY + ".");
            }

            if (!tagged.containsKey(damaged.getUniqueId())) {
                damaged.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damager.getName() + ChatColor.GRAY + ".");
            }

            tagged.put(damager.getUniqueId(), System.currentTimeMillis() + 20000);
            tagged.put(damaged.getUniqueId(), System.currentTimeMillis() + 20000);

            new BukkitRunnable() {
                public void run() {
                    if (!tagged.containsKey(damager.getUniqueId())) {
                        cancel();
                        return;
                    }

                    if (System.currentTimeMillis() >= tagged.get(damager.getUniqueId())) {
                        tagged.remove(damager.getUniqueId());
                        damager.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                    }
                }
            }.runTaskTimer(plugin, 20, 20);

            new BukkitRunnable() {
                public void run() {
                    if (!tagged.containsKey(damager.getUniqueId())) {
                        cancel();
                        return;
                    }

                    if (tagged.containsKey(damaged.getUniqueId())) {
                        if (System.currentTimeMillis() >= tagged.get(damaged.getUniqueId())) {
                            tagged.remove(damaged.getUniqueId());
                            damaged.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                        }
                    }
                }
            }.runTaskTimer(plugin, 20, 20);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            tagged.remove(player.getUniqueId());
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + ChatColor.RED + " has logged out while in combat!");
        }

        if (player.getLastDamageCause() != null) {
            if (player.getLastDamageCause().getEntity() instanceof Player) {
                Player other = (Player) player.getLastDamageCause().getEntity();
                if (tagged.containsKey(other.getUniqueId())) {
                    tagged.remove(other.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId()) && !event.getMessage().toLowerCase().startsWith("/repair")) {
            player.sendMessage(ChatColor.RED + "You cannot run commands during combat!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            tagged.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {
            if (tagged.containsKey(killer.getUniqueId())) {
                tagged.remove(killer.getUniqueId());
            }
        }
    }
}
