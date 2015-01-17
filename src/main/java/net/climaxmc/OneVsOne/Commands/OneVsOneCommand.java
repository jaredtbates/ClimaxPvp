package net.climaxmc.OneVsOne.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.OneVsOne;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OneVsOneCommand implements CommandExecutor {
	private ClimaxPvp plugin;
	private OneVsOne instance;

	public OneVsOneCommand(ClimaxPvp plugin, OneVsOne instance) {
		this.plugin = plugin;
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase("1v1")) {
				if (args.length > 0) {
					if (player.hasPermission("ClimaxPvp.1v1.ArenaCommands")) {
						if (args[0].equalsIgnoreCase("setlobby")) {
							plugin.getConfig().set("lobbyspawn.x", player.getLocation().getBlockX());
							plugin.getConfig().set("lobbyspawn.y", player.getLocation().getBlockY() + 1);
							plugin.getConfig().set("lobbyspawn.z", player.getLocation().getBlockZ());
							plugin.saveConfig();
							player.sendMessage("§a1v1 Lobby Spawn Set!");
							return true;
						} else if (args[0].equalsIgnoreCase("setarena1spawn1")) {
							addArenaSpawn(player, 1, 1);
							return true;
						} else if (args[0].equalsIgnoreCase("setarena1spawn2")) {
							addArenaSpawn(player, 1, 2);
							return true;
						} else if (args[0].equalsIgnoreCase("setarena2spawn1")) {
							addArenaSpawn(player, 2, 1);
							return true;
						} else if (args[0].equalsIgnoreCase("setarena2spawn2")) {
							addArenaSpawn(player, 2, 2);
							return true;
						} else if (args[0].equalsIgnoreCase("setarena3spawn1")) {
							addArenaSpawn(player, 3, 1);
							return true;
						} else if (args[0].equalsIgnoreCase("setarena3spawn2")) {
							addArenaSpawn(player, 3, 2);
							return true;
						}
					}
				}
				int x = plugin.getConfig().getInt("lobbyspawn.x");
				int y = plugin.getConfig().getInt("lobbyspawn.y");
				int z = plugin.getConfig().getInt("lobbyspawn.z");
				player.teleport(new Location(player.getWorld(), x, y, z));
				ItemStack stick = new ItemStack(Material.STICK);
				ItemMeta stickMeta = stick.getItemMeta();
				stickMeta.setDisplayName("§6§lRegular 1v1");
				stick.setItemMeta(stickMeta);
				player.getInventory().clear();
				player.getInventory().addItem(stick);
				player.sendMessage(plugin.getPrefix() + " §7Teleported to the 1v1 Lobby!");
				return true;
			}
		}
		return false;
	}

	private void addArenaSpawn(Player player, int arena, int spawn) {
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockX());
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".y", player.getLocation().getBlockY() + 0.5);
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".z", player.getLocation().getBlockZ());
		plugin.saveConfig();
		player.sendMessage("§aArena " + arena + " spawn " + spawn + " set!");
	}
}
