package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.common.database.CachedPlayerData;
import net.climaxmc.common.donations.trails.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerDeathListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerDeathListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        Player killer = player.getKiller();

        final Location location = player.getLocation();

        BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> new ParticleEffect(new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 1, 2, 1)).sendToLocation(location), 1, 1);
        plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);

        plugin.getServer().getScheduler().runTask(plugin, () -> {
            plugin.respawn(player);
            if (plugin.getCurrentWarps().containsKey(player.getUniqueId())) {
                player.teleport(plugin.getCurrentWarps().get(player.getUniqueId()));
                if (player.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {
                    new PvpKit().wearCheckLevel(player);
                }
            }
        });

        CachedPlayerData playerData = plugin.getPlayerData(player);
        playerData.addDeaths(1);

        if (killer == null) {
            if (plugin.getServer().getOnlinePlayers().size() >= 15) {
                event.setDeathMessage(null);
            } else {
                event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " died");
            }
            return;
        }

        if (plugin.getServer().getOnlinePlayers().size() >= 15) {
            event.setDeathMessage(null);
        } else {
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName());
        }

        if (killer.getHealth() % 2 == 0) {
            player.sendMessage(ChatColor.RED + killer.getName() + ChatColor.YELLOW + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + " hearts" + ChatColor.YELLOW + " left");
        } else {
            player.sendMessage(ChatColor.RED + killer.getName() + ChatColor.YELLOW + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + " 1/2 hearts" + ChatColor.YELLOW + " left");
        }

        if (killer.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        CachedPlayerData killerData = plugin.getPlayerData(killer);
        killerData.addKills(1);

        if (killer.getLocation().distance(plugin.getWarpLocation("NoSoup")) <= 100) {
            killer.setHealth(20);
        }

        if (KitPvp.killStreak.containsKey(killer.getUniqueId())) {
            KitPvp.killStreak.put(killer.getUniqueId(), KitPvp.killStreak.get(killer.getUniqueId()) + 1);
            int killerAmount = KitPvp.killStreak.get(killer.getUniqueId());
            if (killerAmount % 5 == 0) {
                plugin.getServer().broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " has reached a KillStreak of " + ChatColor.RED + killerAmount + ChatColor.GRAY + "!");
                killerAmount = killerAmount * 2 + 10;
                killerData.depositBalance(killerAmount);
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$" + killerAmount + ChatColor.GREEN + "!");
            } else {
                killerData.depositBalance(10);
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$10" + ChatColor.GREEN + "!");
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have reached a KillStreak of " + ChatColor.GOLD + killerAmount + ChatColor.GREEN + "!");
            }
        } else {
            KitPvp.killStreak.put(killer.getUniqueId(), 1);
            killerData.depositBalance(10);
            killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$10" + ChatColor.GREEN + "!");
            killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have reached a KillStreak of " + ChatColor.GOLD + KitPvp.killStreak.get(killer.getUniqueId()) + ChatColor.GREEN + "!");
        }

        if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
            if (KitPvp.killStreak.get(player.getUniqueId()) >= 10) {
                plugin.getServer().broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " destroyed " + ChatColor.RED + player.getName() + "'s " + ChatColor.GOLD + "KillStreak of " + ChatColor.GREEN + KitPvp.killStreak.get(player.getUniqueId()) + "!");
            }
            KitPvp.killStreak.remove(player.getUniqueId());
        }
    }
}
