package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Particles.TNTParticle;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.antinub.AntiNub;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class SnowmanKit extends Kit {

    public SnowmanKit() {
        super("Snowman", new ItemStack(Material.SNOW_BALL), "Throw snowballs to knock people back!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        regenResistance(player);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.WHITE);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.WHITE);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);

        ItemStack ability = new ItemStack(Material.SNOW_BALL, 6);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Snowball");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
        player.updateInventory();
    }

    @EventHandler
    public void onPlayerLaunchProjectile(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity().getType().equals(EntityType.SNOWBALL)) {
                Player player = (Player) event.getEntity().getShooter();
                if (!KitManager.isPlayerInKit(player, this)) {
                    return;
                }
                Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (KitManager.isPlayerInKit(player, SnowmanKit.this)) {
                            ItemStack ability = new ItemStack(Material.SNOW_BALL);
                            ItemMeta abilitymeta = ability.getItemMeta();
                            abilitymeta.setDisplayName(ChatColor.AQUA + "Snowball");
                            ability.setItemMeta(abilitymeta);
                            player.getInventory().addItem(ability);
                            player.updateInventory();
                        }
                    }
                }, 20L * 6);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.SNOWBALL)) {
            if (event.getEntity().getType().equals(EntityType.PLAYER)) {
                Snowball snowball = (Snowball) event.getDamager();
                Player player = (Player) event.getEntity();
                Player shooter = (Player) snowball.getShooter();

                if (!KitManager.isPlayerInKit(shooter)) {
                    return;
                }

                if (ClimaxPvp.getInstance().isWithinProtectedRegion(shooter.getLocation()) && ClimaxPvp.getInstance().isWithinProtectedRegion(player.getLocation())) {
                    return;
                }

                knockback(shooter, player);
                player.playSound(player.getLocation(), Sound.CLICK, 1F, 2F);

                shooter.playSound(shooter.getLocation(), Sound.CLICK, 1F, 2F);

                event.setCancelled(true);

                AntiNub.alertsEnabled.put(player.getUniqueId(), false);
                ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        AntiNub.alertsEnabled.put(player.getUniqueId(), true);
                    }
                }, 20L * 2);
            }
        }
    }

    private void knockback(Player player, Player target)
    {
        Location l = target.getLocation().subtract(player.getLocation());
        double distance = target.getLocation().distance(player.getLocation());
        Vector v = l.toVector().multiply(1.1/distance).setY(0.5);
        target.setVelocity(v);
    }
}