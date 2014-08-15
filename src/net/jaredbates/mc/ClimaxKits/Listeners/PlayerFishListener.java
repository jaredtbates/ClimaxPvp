package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {
	Main plugin;
	
	public PlayerFishListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		if (event.getCaught() instanceof Player) {
			if (plugin.fishermanKit.contains(player.getName())) {
				event.getCaught().teleport(player.getLocation());
			}
		}
	}
}
