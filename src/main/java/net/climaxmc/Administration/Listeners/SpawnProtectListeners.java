package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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

        if (damagerLocation.distance(damager.getWorld().getSpawnLocation()) <= 12 ||
                damagedLocation.distance(damaged.getWorld().getSpawnLocation()) <= 12) {
            event.setCancelled(true);
        }
    }
}
