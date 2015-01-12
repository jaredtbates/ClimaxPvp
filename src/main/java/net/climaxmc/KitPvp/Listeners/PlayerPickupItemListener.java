package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {
	ClimaxPvp plugin;
	
	public PlayerPickupItemListener(ClimaxPvp plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
}
