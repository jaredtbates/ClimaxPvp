package net.climaxmc.Listeners;

import net.climaxmc.Database;
import net.climaxmc.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	Main plugin;
	
	public PlayerJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isOp()) {
			if (!Database.containsPlayer(plugin, plugin.connection, player)) {
				Database.addIP(plugin, plugin.connection, player, player.getAddress());
				player.setOp(false);
			} else {
				
			}
		}
	}
}
