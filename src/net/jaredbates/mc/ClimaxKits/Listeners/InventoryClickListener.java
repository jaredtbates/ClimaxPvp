package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Kits.ArcherKit;
import net.jaredbates.mc.ClimaxKits.Kits.FishermanKit;
import net.jaredbates.mc.ClimaxKits.Kits.HeavyKit;
import net.jaredbates.mc.ClimaxKits.Kits.IronGolemKit;
import net.jaredbates.mc.ClimaxKits.Kits.PvpKit;
import net.jaredbates.mc.ClimaxKits.Kits.SoldierKit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
	Main plugin;

	public InventoryClickListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		ItemStack clicked = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		if (inventory != null) {
			if (inventory.getName() == plugin.kitSelector.getName()) {
				if (clicked.getItemMeta() != null) {
					if (clicked.getItemMeta().getDisplayName().equals("§eKit Pvp")) {
						PvpKit.wear(player);
					} else if (clicked.getItemMeta().getDisplayName().equals("§eKit Heavy")) {
						HeavyKit.wear(player);
					} else if (clicked.getItemMeta().getDisplayName().equals("§eKit Archer")) {
						ArcherKit.wear(player);
					} else if (clicked.getItemMeta().getDisplayName().equals("§eKit Soldier")) {
						SoldierKit.wear(player);
						plugin.soldierKit.add(player.getName());
					} else if (clicked.getItemMeta().getDisplayName().equals("§eKit Fisherman")) {
						FishermanKit.wear(player);
						plugin.fishermanKit.add(player.getName());
					} else if (clicked.getItemMeta().getDisplayName().equals("§eKit IronGolem")) {
						IronGolemKit.wear(player);
						plugin.irongolemKit.add(player.getName());
					}
					event.setCancelled(true);
					player.closeInventory();
				}
			}
		}
	}
}