package net.climaxmc.KitPvp.Listeners;

import me.xericker.disguiseabilities.other.WorldGuard;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Duels.DuelUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
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

            if (WorldGuard.isWithinProtectedRegion(target.getLocation())) {
                event.setCancelled(true);
            }

            if (target.getGameMode().equals(GameMode.CREATIVE) && ClimaxPvp.deadPeoples.contains(target)) {
                event.setCancelled(true);
            }
            if (ClimaxPvp.isSpectating != null) {
                if (ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            }

            if (ClimaxPvp.deadPeoples != null) {
                if (ClimaxPvp.deadPeoples.contains(player)) {
                    event.setCancelled(true);
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

    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER) || !event.getEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        if (player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
            if (player.getItemInHand().getItemMeta().getDisplayName() != null && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Duel Axe " + ChatColor.AQUA + "(Punch a player!)")) {
                if (ClimaxPvp.hasRequest.containsKey(target) && ClimaxPvp.hasRequest.get(target).equals(player)) {
                    DuelUtils duelUtils = new DuelUtils(plugin);
                    duelUtils.acceptRequest(target);
                    event.setCancelled(true);
                    return;
                }
                if (ClimaxPvp.hasRequest.containsKey(player)) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have already sent a duel request to this player!");
                    event.setCancelled(true);
                    return;
                } else {
                    if (ClimaxPvp.inDuel.contains(target)) {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "That player is already in a duel!");
                        event.setCancelled(true);
                        return;
                    } else {
                        DuelUtils duelUtils = new DuelUtils(plugin);
                        duelUtils.openInventory(player);
                        event.setCancelled(true);

                        ClimaxPvp.initialRequest.put(player, target);
                        ClimaxPvp.duelRequestReverse.put(target, player);
                         Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                             @Override
                             public void run() {
                                 ClimaxPvp.initialRequest.remove(player);
                                 ClimaxPvp.duelRequestReverse.remove(target);
                                 ClimaxPvp.duelsKit.remove(player);
                             }
                         }, 20L * 15);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            if (WorldGuard.isWithinProtectedRegion(event.getEntity().getLocation())) {
                //event.setCancelled(true);
            }
            if (player.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50) {
                event.setCancelled(true);
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
