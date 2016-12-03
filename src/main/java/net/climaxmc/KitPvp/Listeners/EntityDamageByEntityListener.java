package net.climaxmc.KitPvp.Listeners;

import me.xericker.disguiseabilities.other.WorldGuard;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.GameMode;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static org.json.XMLTokener.entity;

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

            if (target.getGameMode().equals(GameMode.CREATIVE) && ClimaxPvp.deadPeoples.contains(target)) {
                event.setCancelled(true);
            }
            if (ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }

            if (player != null && ((KitPvp.currentTeams.containsKey(player.getName())
                    && KitPvp.currentTeams.get(player.getName()).equals(target.getName()))
                    || (KitPvp.currentTeams.containsKey(target.getName())
                    && KitPvp.currentTeams.get(target.getName()).equals(player.getName())))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            if (WorldGuard.isWithinProtectedRegion(event.getEntity().getLocation())) {
                //event.setCancelled(true);
            }
        }
    }

    /*@EventHandler (priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Player target = (Player) event.getEntity();
                if (WorldGuard.isWithinProtectedRegion(target.getLocation()) || WorldGuard.isWithinProtectedRegion(target.getLocation())) {
                    return;
                }
                event.setCancelled(true);
                target.damage(event.getDamage());
                double unitVector = target.getLocation().toVector().subtract(event.getDamager().getLocation().toVector()).length() * (0.3);

                velocity(target, target.getLocation().toVector().subtract(event.getDamager().getLocation().toVector()), unitVector, true, 0.5D, 0.0D, 1.0D, 0.4D, true);
            }
        }
    }

    private void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, double xMax, boolean groundBoost) {
        if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
            return;
        }

        if (ySet) {
            vec.setY(yBase);
        }

        vec.normalize();
        vec.multiply(str);

        vec.setY(vec.getY() + yAdd);

        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }

        if (vec.getX() > xMax) {
            vec.setX(xMax);
        }

        if (groundBoost) {
            vec.setY(vec.getY() + 0.2D);
        }

        ent.setFallDistance(0.0F);
        ent.setVelocity(vec);
    }*/
}
