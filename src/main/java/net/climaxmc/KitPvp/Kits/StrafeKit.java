package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrafeKit extends Kit {
    public StrafeKit() {
        super("Strafe", new ItemStack(Material.IRON_SWORD), "It's in the name! Speed II Ftw!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().clear();
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.getInventory().clear();
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ItemStack leggings = new ItemStack(Material.GOLD_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    }
}