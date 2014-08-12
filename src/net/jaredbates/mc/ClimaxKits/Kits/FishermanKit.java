package net.jaredbates.mc.ClimaxKits.Kits;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Utils.Soup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class FishermanKit {
	Main plugin;
	
	public FishermanKit(Main plugin) {
		this.plugin = plugin;
	}
	
	public static void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		player.getInventory().addItem(sword);
		player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
		player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		player.getInventory().setBoots(boots);
		Soup.add(player.getInventory(), 2, 35);
		player.sendMessage("§6You have chosen §aFisherman");
	}
}