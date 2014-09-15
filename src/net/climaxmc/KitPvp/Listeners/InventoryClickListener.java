package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

public class InventoryClickListener implements Listener {
	Main plugin;

	public InventoryClickListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		final Player player = (Player) event.getWhoClicked();
		if (inventory != null) {
			if (inventory.getName() == plugin.kitSelector.getName()) {
				for (Kit kit : KitManager.kits) {
					if (event.getSlot() == kit.getSlot()) {
						if (player.hasPermission("ClimaxKits.Kit." + kit.getName().replaceAll("\\s+", ""))) {
							if (!Main.inKit.contains(player.getUniqueId())) {
								Main.inKit.add(player.getUniqueId());
								for (PotionEffect effect : player.getActivePotionEffects()) {
									player.removePotionEffect(effect.getType());
								}
								player.getInventory().clear();
								kit.wear(player);
								player.sendMessage("§6You have chosen §a" + kit.getName());
							} else {
								player.sendMessage("§cYou have not died yet!");
							}
						} else {
							player.sendMessage("§cYou do not have permission for kit " + kit.getName() + "§c!");
						}
						event.setCancelled(true);
						plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
							public void run() {
								player.closeInventory();
							}
						});
					}
				}
			}
		}
	}
}