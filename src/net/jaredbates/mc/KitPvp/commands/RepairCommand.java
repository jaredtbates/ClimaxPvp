package net.jaredbates.mc.KitPvp.commands;

import net.jaredbates.mc.KitPvp.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class RepairCommand implements Listener {
	Main plugin;
	
	public RepairCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().substring(1).split(" ");
		if (args[0].equals("/1v1")) {
			event.getPlayer().sendMessage("Test");
		}
	}
}
