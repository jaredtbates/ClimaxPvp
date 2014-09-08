package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.KitPvp.Main;

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