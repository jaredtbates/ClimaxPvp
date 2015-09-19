package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BomberKit extends Kit {
    public BomberKit() {
        super("Bomber", new ItemStack(Material.TNT), "Throw your bombs at people to explode them!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    	player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }
    
    /** For the ability - Players should receive 1 TNT every 5 seconds and be able to throw the tnt by right/left clicking on the tnt.
     *  Maximum of 3 tnt at a time, and could you add all the little particle effects like before? make them red.
     *  and of course throwing the bomb should use up 1 in your inventory. **/
}