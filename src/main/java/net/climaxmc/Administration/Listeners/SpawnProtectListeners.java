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

        if (!(((damagerLocation.getBlockY() <= 20) || (damagerLocation.getBlockY() >= 30))
                && ((damagerLocation.getBlockX() <= -672) || (damagerLocation.getBlockX() <= -694))
                && ((damagerLocation.getBlockZ() <=  550) || (damagerLocation.getBlockZ() <= 573))
                || ((damagedLocation.getBlockY() <= 20) || (damagedLocation.getBlockY() >= 30)
                && (damagedLocation.getBlockX() <= -672) || (damagedLocation.getBlockX() <= -694)
                && (damagedLocation.getBlockZ() <=  550) || (damagedLocation.getBlockZ() <= 573)))) {
            event.setCancelled(true);
        }
    }
}
