package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnProtectListeners implements Listener {
    private ClimaxPvp plugin;

    public SpawnProtectListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        Location damagerLocation = damager.getLocation();
        Location damagedLocation = damaged.getLocation();

        if ((damagerLocation.distance(damager.getWorld().getSpawnLocation()) <= 16 || (damagedLocation.distance(damaged.getWorld().getSpawnLocation()) <= 16))
                || (damagerLocation.distance(plugin.getWarpLocation("Soup")) <= 12 || (damagedLocation.distance(plugin.getWarpLocation("Soup")) <= 12))
                || (damagerLocation.distance(plugin.getWarpLocation("Fair")) <= 4 || (damagedLocation.distance(plugin.getWarpLocation("Fair")) <= 4))
                || (damagerLocation.distance(plugin.getWarpLocation("Fps")) <= 3 || (damagedLocation.distance(plugin.getWarpLocation("Fps"))) <= 3)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Location location = player.getLocation();

        if (location.distance(location.getWorld().getSpawnLocation()) <= 16
                || location.distance(plugin.getWarpLocation("Soup")) <= 12
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        if (location.distance(location.getWorld().getSpawnLocation()) <= 16
                || location.distance(plugin.getWarpLocation("Soup")) <= 12
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        if (location.distance(location.getWorld().getSpawnLocation()) <= 16
                || location.distance(plugin.getWarpLocation("Soup")) <= 12
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
            entity.remove();
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        if (location.distance(location.getWorld().getSpawnLocation()) <= 16
                || location.distance(plugin.getWarpLocation("Soup")) <= 12
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();

        if ((location.distance(location.getWorld().getSpawnLocation()) <= 16
                || location.distance(plugin.getWarpLocation("Soup")) <= 12
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3)
                && !player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}
