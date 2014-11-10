package net.climaxmc.OneVsOne.Listeners;

import net.climaxmc.Main;
import net.climaxmc.OneVsOne.OneVsOne;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerDeathListener implements Listener {
	Main plugin;
	
	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		final Player player = event.getEntity();
		if (player instanceof Player) {
			plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
				public void run() {
					if (OneVsOne.in1v1.contains(player.getUniqueId())) {
						OneVsOne.in1v1.remove(player.getUniqueId());
						int x = plugin.getConfig().getInt("lobbyspawn.x");
						int y = plugin.getConfig().getInt("lobbyspawn.y");
						int z = plugin.getConfig().getInt("lobbyspawn.z");
						player.teleport(new Location(player.getWorld(), x, y, z));
					} else if (OneVsOne.challenged.containsKey(player.getUniqueId())) {
						int x = plugin.getConfig().getInt("lobbyspawn.x");
						int y = plugin.getConfig().getInt("lobbyspawn.y");
						int z = plugin.getConfig().getInt("lobbyspawn.z");
						player.teleport(new Location(player.getWorld(), x, y, z));
						ItemStack stick = new ItemStack(Material.STICK);
						ItemMeta stickmeta = stick.getItemMeta();
						stickmeta.setDisplayName("§6§lRegular 1v1");
						stick.setItemMeta(stickmeta);
						OneVsOne.in1v1.add(player.getUniqueId());
						player.getInventory().clear();
						player.getInventory().addItem(stick);
						player.sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 Lobby!");
						OneVsOne.in1v1.add(player.getKiller().getUniqueId());
						player.getKiller().getInventory().clear();
						player.getKiller().getInventory().addItem(stick);
						player.getKiller().sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 Lobby!");
						player.getKiller().teleport(new Location(player.getWorld(), x, y, z));
					}
				}
			});
		}
	}
}
