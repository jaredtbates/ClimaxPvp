package net.climaxmc.OneVsOne;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegularKit {
	
	public static void wear(Player p){
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().addItem(sword);
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
		p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP, 1));
	}

}
