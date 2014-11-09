package net.climaxmc.OneVsOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.climaxmc.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OneVsOne implements CommandExecutor {
	Main plugin;
	
	static ArrayList<UUID> in1v1 = new ArrayList<UUID>();
	static HashMap<UUID, UUID> challenged = new HashMap<UUID, UUID>();
	
	public OneVsOne(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(new Events(plugin), plugin);
		plugin.getCommand("1v1").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().startsWith("1v1") && args.length == 0) {
				if (in1v1.contains(player.getUniqueId())){
					player.sendMessage("§aTeleported to Spawn!");
					player.teleport(new Location(player.getWorld(), -675.5, 8, 1068.5));
					in1v1.remove(player.getUniqueId());
					challenged.remove(player.getUniqueId());
				} else {
					in1v1.add(player.getUniqueId());
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
					player.sendMessage(plugin.climax + " §7Teleported to the 1v1 Lobby!");
				}
				return true;
			}
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
	
	private void addArenaSpawn(Player player, int arena, int spawn) {
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockX() + 0.5);
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockY());
		plugin.getConfig().set("arena" + arena + ".spawn" + spawn + ".x", player.getLocation().getBlockZ() + 0.5);
		plugin.saveConfig();
		player.sendMessage("§aArena " + arena + " Spawn " + spawn + " Set!");
	}
}
