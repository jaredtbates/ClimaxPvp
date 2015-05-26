package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    ClimaxPvp plugin;

    public InventoryClickListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();
        if (inventory != null && event.getCurrentItem() != null) {
            if (inventory.getName().equals(KitPvp.kitSelector.getName())) {
                for (Kit kit : KitManager.kits) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(kit.getItem().getItemMeta().getDisplayName())) {
                        kit.wearCheckPerms(player);
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