package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.KitPvp.Main;

import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {
	Main plugin;

	public InventoryOpenListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) {
		Player player = (Player) event.getPlayer();
		if (event.getInventory().getHolder() instanceof Chest || event.getInventory().getHolder() instanceof DoubleChest){
			event.setCancelled(true);
			Soup.add(plugin.soup, 0, 53);
			player.openInventory(plugin.soup);
		}
	}
}
