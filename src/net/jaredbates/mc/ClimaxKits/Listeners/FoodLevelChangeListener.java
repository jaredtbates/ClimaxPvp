package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
	Main plugin;
	
	public FoodLevelChangeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHungerChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
}