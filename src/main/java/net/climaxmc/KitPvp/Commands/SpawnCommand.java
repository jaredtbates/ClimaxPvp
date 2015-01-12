package net.climaxmc.KitPvp.Commands;

import java.util.ArrayList;
import java.util.List;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class SpawnCommand implements CommandExecutor {
	ClimaxPvp plugin;

	public SpawnCommand(ClimaxPvp plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (command.getName().equalsIgnoreCase("spawn")) {
				final Block block = player.getLocation().getBlock();
				
				player.sendMessage("§aPlease wait §c3 §aseconds to be teleported to spawn.");

				plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
					public void run() {
						if (player.getLocation().getBlock().equals(block)) {
							if (KitPvp.inKit.contains(player.getUniqueId())) {
								KitPvp.inKit.remove(player.getUniqueId());
							}
							player.teleport(player.getWorld().getSpawnLocation());
							player.getInventory().clear();
							player.getInventory().setArmorContents(null);
							player.setHealth(20L);
							player.setMaxHealth(20L);
							for (PotionEffect effect : player.getActivePotionEffects()) {
								player.removePotionEffect(effect.getType());
							}
							ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
							ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
							kitSelectorMeta.setDisplayName("§a§lKit Selector");
							List<String> kitSelectorLores = new ArrayList<String>();
							kitSelectorLores.add("§5§o(Right Click) to select a kit!");
							kitSelectorMeta.setLore(kitSelectorLores);
							kitSelector.setItemMeta(kitSelectorMeta);
							player.getInventory().setItem(0, kitSelector);
							player.sendMessage("§aYou have been teleported to spawn!");
						} else {
							player.sendMessage("§cTeleportation canceled.");
						}
					}
				}, 60);
			}
		}
		return false;
	}
}
