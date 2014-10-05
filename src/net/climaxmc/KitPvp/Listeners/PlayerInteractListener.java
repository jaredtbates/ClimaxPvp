package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Kit.KitType;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {
	Main plugin;

	public PlayerInteractListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 20.0D) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				player.getInventory().getItemInHand().setType(Material.BOWL);
				player.setHealth(player.getHealth() >= 13.0D ? 20.0D : player.getHealth() + 7.0D);
			}
		}
		if (item != null) {
			if (item.getType().equals(Material.NETHER_STAR)) {
				ItemStack defaultKits = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
				ItemMeta defaultKitsMeta = defaultKits.getItemMeta();
				defaultKitsMeta.setDisplayName("§eDefault Kits");
				defaultKits.setItemMeta(defaultKitsMeta);
				plugin.kitSelector.setItem(0, defaultKits);
				ItemStack amateurKits = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
				ItemMeta amateurKitsMeta = amateurKits.getItemMeta();
				amateurKitsMeta.setDisplayName("§3Amateur Kits");
				amateurKits.setItemMeta(amateurKitsMeta);
				plugin.kitSelector.setItem(9, amateurKits);
				ItemStack experiencedKits = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
				ItemMeta experiencedKitsMeta = experiencedKits.getItemMeta();
				experiencedKitsMeta.setDisplayName("§aExperienced Kits");
				experiencedKits.setItemMeta(experiencedKitsMeta);
				plugin.kitSelector.setItem(18, experiencedKits);
				ItemStack advancedKits = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
				ItemMeta advancedKitsMeta = advancedKits.getItemMeta();
				advancedKitsMeta.setDisplayName("§cAdvanced Kits");
				advancedKits.setItemMeta(advancedKitsMeta);
				plugin.kitSelector.setItem(27, advancedKits);
				ItemStack veteranKits = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
				ItemMeta veteranKitsMeta = veteranKits.getItemMeta();
				veteranKitsMeta.setDisplayName("§6Veteran Kits");
				veteranKits.setItemMeta(veteranKitsMeta);
				plugin.kitSelector.setItem(36, veteranKits);
				int defaultSlot = 0, amateurSlot = 0, experiencedSlot = 0, advancedSlot = 0, veteranSlot = 0;
				for (Kit kit : KitManager.kits) {
					if (kit.getType().equals(KitType.DEFAULT)) {
						plugin.kitSelector.setItem(++defaultSlot, kit.getItem());
					} else if (kit.getType().equals(KitType.AMATEUR)) {
						plugin.kitSelector.setItem(++amateurSlot + 9, kit.getItem());
					} else if (kit.getType().equals(KitType.EXPERIENCED)) {
						plugin.kitSelector.setItem(++experiencedSlot + 18, kit.getItem());
					} else if (kit.getType().equals(KitType.ADVANCED)) {
						plugin.kitSelector.setItem(++advancedSlot + 27, kit.getItem());
					} else if (kit.getType().equals(KitType.VETERAN)) {
						plugin.kitSelector.setItem(++veteranSlot + 36, kit.getItem());
					}
				}
				player.openInventory(plugin.kitSelector);
			}
		}
	}
}