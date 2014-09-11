package net.climaxmc.KitPvp;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a kit
 * 
 * @author Jared
 */
public abstract class Kit implements Listener, CommandExecutor {
	/**
	 * Name of the kit
	 */
	private String kitName = "";
	/**
	 * Item representing the kit
	 */
	private ItemStack kitItem = new ItemStack(Material.AIR);
	/**
	 * Lore of the kit
	 */
	private String kitLore = "";
	
	/**
	 * Defines a kit
	 * 
	 * @param kitName Name of the kit
	 * @param kitItem Item representing the kit
	 */
	public Kit(String kitName, ItemStack kitItem) {
		this.kitName = kitName;
		this.kitItem = kitItem;
		System.out.println("Kit Manager> Enabled kit " + kitName);
	}
	
	/**
	 * Defines a kit
	 * 
	 * @param kitName Name of the kit
	 * @param kitItem Item representing the kit
	 * @param kitLore Lore of the kit
	 */
	public Kit(String kitName, ItemStack kitItem, String kitLore) {
		this.kitName = kitName;
		this.kitItem = kitItem;
		this.kitLore = kitLore;
		System.out.println("Kit Manager> Enabled kit " + kitName);
	}
	
	/**
	 * Get the name of the kit
	 * 
	 * @return Name of the kit
	 */
	public String getName() {
		return kitName;
	}
	
	/**
	 * Get the item that represents the kit
	 * 
	 * @return Item representing the kit
	 */
	public ItemStack getItem() {
		return kitItem;
	}
	
	/**
	 * Get the lore of the kit
	 * 
	 * @return Lore of the kit
	 */
	public String setLore() {
		return kitLore;
	}
	
	/**
	 * Set the name of the kit
	 * 
	 * @param newKitName New name of the kit
	 */
	public void setName(String newKitName) {
		kitName = newKitName;
	}
	
	/**
	 * Sets the item that represents the kit
	 * 
	 * @param newKitItem New item representing the kit
	 */
	public void setItem(ItemStack newKitItem) {
		kitItem = newKitItem;
	}
	
	/**
	 * Sets the lore of the kit
	 * 
	 * @param newKitLore New lore of the kit
	 */
	public void setLore(String newKitLore) {
		kitLore = newKitLore;
	}
	
	/**
	 * Wear the kit
	 * 
	 * @param player Player to wear kit
	 */
	public abstract void wear(Player player);
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase(getName())) {
				wear(player);
			}
		}
		return false;
	}

	/**
	 * Gives a player specified amount of soups
	 * 
	 * @param inventory Inventory to give soups
	 * @param startslot Inventory slot to start giving soups
	 * @param finalslot Inventory slot to end giving soups
	 */
	public static void addSoup(Inventory inventory, int startslot, int finalslot) {
		for (int x = startslot; x <= finalslot; x++) {
			inventory.setItem(x, new ItemStack(Material.MUSHROOM_SOUP));
		}
	}
}
