package net.climaxmc.KitPvp.KitPvp.Listeners;

import net.climaxmc.KitPvp.Main;

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
		event.setQuitMessage("§cQuit§8» " + player.getName());
		/* if (plugin.tag.contains(player.getName())) {
			Bukkit.broadcastMessage("§c" + player.getName().toUpperCase() + " HAS COMBAT LOGGED!");
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "tempban " + player.getName() + " 2m");
		} */
	}
}
