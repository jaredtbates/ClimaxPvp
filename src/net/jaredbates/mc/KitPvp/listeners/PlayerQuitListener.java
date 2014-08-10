package net.jaredbates.mc.KitPvp.listeners;

import net.jaredbates.mc.KitPvp.Main;

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
	}
}
