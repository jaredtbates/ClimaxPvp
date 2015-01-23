package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;

public class InventoryClickListener implements Listener {
	ClimaxPvp plugin;

	public InventoryClickListener(ClimaxPvp plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		final Player player = (Player) event.getWhoClicked();
		if (inventory != null) {
			if (inventory.getName().equals(KitPvp.kitSelector.getName())) {
				for (Kit kit : KitManager.kits) {
					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(kit.getItem().getItemMeta().getDisplayName())) {
						if (player.hasPermission("ClimaxPvp.Kit." + kit.getName().replaceAll("\\s+", ""))) {
							if (!KitPvp.inKit.contains(player.getUniqueId())) {
								KitPvp.inKit.add(player.getUniqueId());
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
						plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
							public void run() {
								player.closeInventory();
							}
						});
					}
					event.setCancelled(true);
				}
			}
		}
	}
}