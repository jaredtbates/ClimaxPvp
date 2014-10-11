package net.climaxmc.Listeners;

import net.climaxmc.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
	Main plugin;
	
	public PlayerQuitListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (plugin.inPassword.contains(player.getUniqueId())) {
			plugin.inPassword.remove(player.getUniqueId());
		}
	}
}
