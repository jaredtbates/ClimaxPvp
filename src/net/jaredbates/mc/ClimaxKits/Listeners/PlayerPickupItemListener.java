package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {
	Main plugin;
	
	public PlayerPickupItemListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
}