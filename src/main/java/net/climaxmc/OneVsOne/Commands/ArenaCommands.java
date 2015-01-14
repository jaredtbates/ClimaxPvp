package net.climaxmc.OneVsOne.Commands;

import net.climaxmc.ClimaxPvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ArenaCommands implements CommandExecutor {
	ClimaxPvp plugin;
	
	public ArenaCommands(ClimaxPvp plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args[0].equalsIgnoreCase("setlobby")) {
				plugin.getConfig().set("lobbyspawn.x", player.getLocation().getBlockX() + 0.5);
				plugin.getConfig().set("lobbyspawn.y", player.getLocation().getBlockY());
				plugin.getConfig().set("lobbyspawn.z", player.getLocation().getBlockZ() + 0.5);
				plugin.saveConfig();
				player.sendMessage(ChatColor.GREEN + "1v1 Lobby Spawn Set!");
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
		return false;
	}
	
	public void addArenaSpawn(Player player, int arena, int spawn) {
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockX() + 0.5);
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockY());
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockZ() + 0.5);
		plugin.saveConfig();
		player.sendMessage("§aArena " + arena + " Spawn " + spawn + " Set!");
	}
}
