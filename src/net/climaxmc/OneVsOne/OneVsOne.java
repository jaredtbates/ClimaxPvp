package net.climaxmc.OneVsOne;

import java.util.ArrayList;
import java.util.HashMap;

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
	public OneVsOne(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(new Events(plugin), plugin);
		plugin.getCommand("1v1").setExecutor(this);
	}
	
	static ArrayList<String> in1v1 = new ArrayList<String>();
	static HashMap<String, String> challenged = new HashMap<String, String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,	String label, String[] args){
		Player p = (Player) sender;
		if(cmd.getName().startsWith("1v1") && args.length == 0){
			if(in1v1.contains(p.getName())){
				p.sendMessage("§aTeleported to Spawn!");
				p.teleport(new Location(p.getWorld(), -675.5, 8, 1068.5));
				in1v1.remove(p.getName());
				challenged.remove(p);
				return true;
			}
			in1v1.add(p.getName());
			int x = plugin.getConfig().getInt("lobbyspawn.x");
			int y = plugin.getConfig().getInt("lobbyspawn.y");
			int z = plugin.getConfig().getInt("lobbyspawn.z");
			p.teleport(new Location(p.getWorld(), x, y, z));
			ItemStack stick = new ItemStack(Material.STICK);
			ItemMeta stickmeta = stick.getItemMeta();
			stickmeta.setDisplayName("§6§lRegular 1v1");
			stick.setItemMeta(stickmeta);
			p.getInventory().clear();
			p.getInventory().addItem(stick);
			p.sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 Lobby!");
			return true;
		}
		if(args[0].equalsIgnoreCase("setlobby")){
			plugin.getConfig().set("lobbyspawn.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("lobbyspawn.y", p.getLocation().getBlockY());
			plugin.getConfig().set("lobbyspawn.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "1v1 Lobby Spawn Set!");
		}
		if(args[0].equalsIgnoreCase("setarena1spawn1")){
			plugin.getConfig().set("arena1.spawn1.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena1.spawn1.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena1.spawn1.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 1 Spawn 1 Set!");
		}
		if(args[0].equalsIgnoreCase("setarena1spawn2")){
			plugin.getConfig().set("arena1.spawn2.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena1.spawn2.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena1.spawn2.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 1 Spawn 2 Set!");
		}
		if(args[0].equalsIgnoreCase("setarena2spawn1")){
			plugin.getConfig().set("arena2.spawn1.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena2.spawn1.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena2.spawn1.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 2 Spawn 1 Set!");
		}
		if(args[0].equalsIgnoreCase("setarena2spawn2")){
			plugin.getConfig().set("arena2.spawn2.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena2.spawn2.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena2.spawn2.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 2 Spawn 2 Set!");
		}
		if(args[0].equalsIgnoreCase("setarena3spawn1")){
			plugin.getConfig().set("arena3.spawn1.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena3.spawn1.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena3.spawn1.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 3 Spawn 1 Set!");
		}
		if(args[0].equalsIgnoreCase("setarena3spawn2")){
			plugin.getConfig().set("arena3.spawn2.x", p.getLocation().getBlockX() + 0.5);
			plugin.getConfig().set("arena3.spawn2.y", p.getLocation().getBlockY());
			plugin.getConfig().set("arena3.spawn2.z", p.getLocation().getBlockZ() + 0.5);
			plugin.saveConfig();
			p.sendMessage(ChatColor.GREEN + "Arena 3 Spawn 2 Set!");
		}
		return false;
	}
}
