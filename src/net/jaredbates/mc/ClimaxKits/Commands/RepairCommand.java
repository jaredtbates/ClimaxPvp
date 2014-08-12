package net.jaredbates.mc.ClimaxKits.Commands;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

public class RepairCommand implements Listener {
	Main plugin;

	public RepairCommand(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String[] args = event.getMessage().substring(1).split(" ");
		Player player = event.getPlayer();
		if (args[0].equals("repair")) {
			event.setCancelled(true);
			if (player.hasPermission("ClimaxKits.Repair")) {
				for (ItemStack item : player.getInventory().getContents()) {
					item.setDurability((short) 0);
				}
				for (ItemStack item : player.getInventory().getArmorContents()) {
					item.setDurability((short) 0);
				}
				player.sendMessage("§aYour inventory has been repaired!");
			} else {
				player.sendMessage("§cYou do not have permission for that command!");
			}
		}
	}
}
