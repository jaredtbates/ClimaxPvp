package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
	Main plugin;
	
	public WeatherChangeListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
	}
}