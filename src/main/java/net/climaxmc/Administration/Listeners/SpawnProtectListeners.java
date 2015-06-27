package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpawnProtectListeners implements Listener {
    private ClimaxPvp plugin;

    public SpawnProtectListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        Location damagerLocation = damager.getLocation();
        Location damagedLocation = damaged.getLocation();

        if ((damagerLocation.distance(damager.getWorld().getSpawnLocation()) <= 12 || (damagedLocation.distance(damaged.getWorld().getSpawnLocation()) <= 12))
                || (damagerLocation.distance(plugin.getWarpLocation("NoSoup")) <= 7 || (damagedLocation.distance(plugin.getWarpLocation("NoSoup")) <= 7))
                || (damagerLocation.distance(plugin.getWarpLocation("Fair")) <= 4 || (damagedLocation.distance(plugin.getWarpLocation("Fair")) <= 4))
                || (damagerLocation.distance(plugin.getWarpLocation("Fps")) <= 3 || (damagedLocation.distance(plugin.getWarpLocation("Fps"))) <= 3)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        Location location = player.getLocation();

        if (location.distance(location.getWorld().getSpawnLocation()) <= 12
                || location.distance(plugin.getWarpLocation("NoSoup")) <= 7
                || location.distance(plugin.getWarpLocation("Fair")) <= 4
                || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
            event.setCancelled(true);
        }
    }
}
