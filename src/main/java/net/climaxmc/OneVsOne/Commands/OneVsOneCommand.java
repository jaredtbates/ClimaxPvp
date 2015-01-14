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
	ClimaxPvp plugin;
	
	public OneVsOneCommand(ClimaxPvp plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().startsWith("1v1") && args.length == 0) {
				if (OneVsOne.in1v1.contains(player.getUniqueId())){
					player.sendMessage("§aTeleported to Spawn!");
					player.teleport(new Location(player.getWorld(), -675.5, 8, 1068.5));
					OneVsOne.in1v1.remove(player.getUniqueId());
					OneVsOne.challenged.remove(player.getUniqueId());
				} else {
					OneVsOne.in1v1.add(player.getUniqueId());
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
		}
		return false;
	}
}
