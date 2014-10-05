package net.climaxmc.Listeners;

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
			if (player.getAddress() != null)
				player.getAddress();
			player.setOp(false);
			
		}
	}
}
