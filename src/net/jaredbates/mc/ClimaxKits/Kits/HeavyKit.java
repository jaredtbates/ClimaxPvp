package net.jaredbates.mc.ClimaxKits.Kits;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Utils.Soup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeavyKit {
	Main plugin;
	
	public static void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		sword.addEnchantment(Enchantment.DURABILITY, 2);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		player.getInventory().addItem(sword);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1));
		Soup.add(player.getInventory(), 1, 35);
		player.sendMessage("§6You have chosen §aHeavy");
	}
}