package net.climaxmc.Administration.Listeners;

import lombok.Getter;
import me.xericker.disguiseabilities.other.WorldGuard;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CombatLogListeners implements Listener {
    private ClimaxPvp plugin;
    @Getter
    private static HashMap<UUID, Integer> tagged = new HashMap<>();
    private HashMap<UUID, Long> logged = new HashMap<>();

    public CombatLogListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (WorldGuard.isWithinProtectedRegion(damaged.getLocation()) || WorldGuard.isWithinProtectedRegion(damager.getLocation())) {
                return;
            }
            if (ClimaxPvp.deadPeoples.contains(damager) || ClimaxPvp.isSpectating.contains(damager.getUniqueId())) {
                return;
            }
            if (damager.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50 || (damaged.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50)) {
                return;
            }

           if (KitPvp.currentTeams.containsKey(damaged.getName()) || KitPvp.currentTeams.containsKey(damager.getName())) {
                if (KitPvp.currentTeams.get(damaged.getName()) == damager.getName()
                        || KitPvp.currentTeams.get(damager.getName()) == damaged.getName()) {
                    if (plugin.getServer().getOnlinePlayers().size() >= 7) {
                        damaged.sendMessage(ChatColor.GREEN + "You were not damaged by " + ChatColor.AQUA + damager.getName()
                                + ChatColor.GREEN + "because you are teamed!");
                        damager.sendMessage(ChatColor.GREEN + "You did not damage " + ChatColor.AQUA + damaged.getName()
                                + ChatColor.GREEN + "because you are teamed!");
                        event.setCancelled(true);
                    } else {
                        event.setCancelled(false);
                    }
                } else {
                    event.setCancelled(false);
                }
            }

            if (!KitPvp.currentTeams.containsKey(damaged.getName()) || !KitPvp.currentTeams.containsKey(damager.getName())) {
                if (KitPvp.currentTeams.get(damaged.getName()) != damager.getName()
                        || KitPvp.currentTeams.get(damager.getName()) != damaged.getName()) {
                    if (!tagged.containsKey(damaged.getUniqueId())) {
                        damaged.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damager.getName() + ChatColor.GRAY + ".");
                        tagged.put(damaged.getUniqueId(),
                                new BukkitRunnable() {
                                    public void run() {
                                        tagged.remove(damaged.getUniqueId());
                                        damaged.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                                    }
                                }.runTaskLater(plugin, 240).getTaskId());
                    } else {
                        plugin.getServer().getScheduler().cancelTask(tagged.get(damaged.getUniqueId()));
                        tagged.put(damaged.getUniqueId(),
                                new BukkitRunnable() {
                                    public void run() {
                                        tagged.remove(damaged.getUniqueId());
                                        damaged.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                                    }
                                }.runTaskLater(plugin, 240).getTaskId());
                    }

                    if (!tagged.containsKey(damager.getUniqueId())) {
                        damager.sendMessage(ChatColor.GRAY + "You are now in combat with " + ChatColor.GOLD + damaged.getName() + ChatColor.GRAY + ".");
                        tagged.put(damager.getUniqueId(),
                                new BukkitRunnable() {
                                    public void run() {
                                        tagged.remove(damager.getUniqueId());
                                        damager.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                                    }
                                }.runTaskLater(plugin, 240).getTaskId());
                    } else {
                        plugin.getServer().getScheduler().cancelTask(tagged.get(damager.getUniqueId()));
                        tagged.put(damager.getUniqueId(),
                                new BukkitRunnable() {
                                    public void run() {
                                        tagged.remove(damager.getUniqueId());
                                        damager.sendMessage(ChatColor.GRAY + "You are no longer in combat.");
                                    }
                                }.runTaskLater(plugin, 240).getTaskId());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            tagged.remove(player.getUniqueId());
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + ChatColor.RED + " has logged out while in combat!");
            logged.put(player.getUniqueId(), System.currentTimeMillis() + 120000);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId()) && (event.getMessage().toLowerCase().startsWith("/spawn")
                || event.getMessage().toLowerCase().startsWith("/warp")
                || event.getMessage().toLowerCase().startsWith("/spec")
                || event.getMessage().toLowerCase().startsWith("/climaxpvp:")
                || event.getMessage().toLowerCase().startsWith("/tourney")
                || event.getMessage().toLowerCase().startsWith("/tournament")
                || event.getMessage().toLowerCase().startsWith("/repair")
                || event.getMessage().toLowerCase().startsWith("/soup"))) {
            player.sendMessage(ChatColor.RED + "You cannot run that command during combat!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (tagged.containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().cancelTask(tagged.get(player.getUniqueId()));
            tagged.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        if (logged.containsKey(uuid)) {
            if (System.currentTimeMillis() < logged.get(uuid)) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "\nYou are banned for combat logging.\nYour ban will expire 2 minutes from when you were banned.");
            } else {
                logged.remove(uuid);
            }
        }
    }
}
