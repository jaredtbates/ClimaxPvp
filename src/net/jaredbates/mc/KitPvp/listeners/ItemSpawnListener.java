package net.jaredbates.mc.KitPvp.listeners;

import net.jaredbates.mc.KitPvp.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {
	Main plugin;
	
	public ItemSpawnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		event.getEntity().remove();
	}
}