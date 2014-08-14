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
				if (plugin.economy.getBalance(player) >= 2) {
					plugin.economy.withdrawPlayer(player, 2);
					for (ItemStack item : player.getInventory().getContents()) {
						if (item != null) {
							item.setDurability((short) -100);
						}
					}
					for (ItemStack item : player.getInventory().getArmorContents()) {
						if (item != null) {
							item.setDurability((short) -100);
						}
					}
					player.sendMessage("§aYou repaired your inventory for $2!");
				} else {
					player.sendMessage("§cYou do not have enough money to repair your inventory!");
				}
			} else {
				player.sendMessage("§cYou do not have permission for that command!");
			}
		}
	}
}
