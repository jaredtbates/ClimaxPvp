package net.climaxmc.KitPvp.KitPvp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Kit implements Listener {
	private String kitName = "";
	private ItemStack kitItem = new ItemStack(Material.AIR);
	private ItemMeta kitMeta = kitItem.getItemMeta();
	
	public Kit(String kitName, ItemStack kitItem, String kitMeta) {
		this.kitName = kitName;
		this.kitItem = kitItem;
		this.kitMeta = kitMeta;
		System.out.println("Kit Manager> Enabled kit " + kitName);
	}
	
	public String getName() {
		return kitName;
	}
	
	public ItemStack getItem() {
		return kitItem;
	}
	
	public void setName(String newKitName) {
		kitName = newKitName;
	}
	
	public void setItem(ItemStack newKitItem) {
		kitItem = newKitItem;
	}
	
	public abstract void wear(Player player);
}
