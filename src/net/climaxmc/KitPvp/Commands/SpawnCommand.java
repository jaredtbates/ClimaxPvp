package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Main;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
	Main plugin;

	public SpawnCommand(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase("spawn")) {
				final Location location = player.getLocation();
				
				player.sendMessage("§aPlease wait §c3 §aseconds to be teleported to spawn.");

				plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
					public void run() {
						if (player.getLocation().equals(location)) {
							player.teleport(player.getWorld().getSpawnLocation());
						} else {
							player.sendMessage("§cYou cannot move while teleporting!");
						}
					}
				}, 60);
				player.teleport(player.getWorld().getSpawnLocation());
			}
		}
		return false;
	}
}
