package net.climaxmc.OneVsOne;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegularKit {
	
	public static void wear(Player player) {
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		player.getInventory().addItem(sword);
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
	}

}
