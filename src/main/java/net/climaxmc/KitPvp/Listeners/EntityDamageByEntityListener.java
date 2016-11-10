package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;
import java.util.Map;

public class EntityDamageByEntityListener implements Listener {
    private ClimaxPvp plugin;

    public EntityDamageByEntityListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player target = (Player) event.getEntity();

            Player player = null;

            if (event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
                player = (Player) event.getDamager();
            } else if (event.getDamager().getType().equals(EntityType.ARROW) || event.getDamager().getType().equals(EntityType.FISHING_HOOK)) {
                Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getShooter() instanceof Player) {
                    player = (Player) projectile.getShooter();
                }
            }

            if (player != null && ((KitPvp.currentTeams.containsKey(player.getName())
                    && KitPvp.currentTeams.get(player.getName()).equals(target.getName()))
                    || (KitPvp.currentTeams.containsKey(target.getName())
                    && KitPvp.currentTeams.get(target.getName()).equals(player.getName())))) {
                event.setCancelled(true);
            }
        }
    }
}
