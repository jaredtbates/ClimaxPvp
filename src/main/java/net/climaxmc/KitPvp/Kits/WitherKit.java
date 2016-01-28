package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class WitherKit extends Kit {

    private ClimaxPvp plugin;

    public WitherKit(ClimaxPvp plugin) {
        super("Wither", new ItemStack(Material.SKULL_ITEM, 1, (byte) 1), "Shoot your WitherBow to Launch your Wither Head!", ChatColor.RED);
        this.plugin = plugin;
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.BOW));
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setHelmet(helmet);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 3);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 34);
        player.getInventory().setItem(17, new ItemStack(Material.ARROW, 64));
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.BOW));
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setHelmet(helmet);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 3);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(17, new ItemStack(Material.ARROW, 64));
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (KitManager.isPlayerInKit(player, this)) {
                event.setCancelled(true);
                player.launchProjectile(WitherSkull.class).setVelocity(event.getProjectile().getVelocity());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            if (event.getDamager() instanceof WitherSkull) {
                event.setCancelled(true);
                if (!VanishCommand.getVanished().contains(target.getUniqueId())
                        && !CheckCommand.getChecking().contains(target.getUniqueId())
                        && (KitPvp.currentTeams.get(event.getDamager().getName()) != target.getName()
                        && KitPvp.currentTeams.get(target.getName()) != event.getDamager().getName())) {
                    target.damage(5);
                    Vector vector = target.getEyeLocation().getDirection();
                    vector.multiply(-0.5F);
                    vector.setY(-0.2);
                    target.setVelocity(vector);
                }
                Location location = target.getLocation();
                if (location.distance(location.getWorld().getSpawnLocation()) <= 16
                        || location.distance(plugin.getWarpLocation("Soup")) <= 12
                        || location.distance(plugin.getWarpLocation("Fair")) <= 4
                        || location.distance(plugin.getWarpLocation("Fps")) <= 3) {
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPvp(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();
                if (KitManager.isPlayerInKit(player, this)) {
                    if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                        if (!VanishCommand.getVanished().contains(damaged.getUniqueId()) && !CheckCommand.getChecking().contains(damaged.getUniqueId())) {
                            damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1));
                        }
                    }
                }
            }
        }
    }
}
