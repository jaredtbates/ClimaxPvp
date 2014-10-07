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
			if (!Database.containsPlayerIP(plugin, plugin.connection, player)) {
				Database.addIP(plugin, plugin.connection, player, player.getAddress());
				player.sendMessage("§cYour IP has been added to the database! If you login with a new IP you will be prompted for a password! Make sure to set it with §a/setpassword [Password]§c!");
			} else {
				
			}
		}
	}
}
