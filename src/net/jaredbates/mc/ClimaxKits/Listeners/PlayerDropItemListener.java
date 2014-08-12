package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
	Main plugin;
	
	public PlayerDropItemListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getType() != Material.BOWL) {
			event.setCancelled(true);
		}
	}
}