package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    private ClimaxPvp plugin;

    public InventoryClickListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();
        if (inventory != null && event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
            if (inventory.getName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Kit Selector")) {
                for (Kit kit : KitManager.getKits()) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(kit.getItem().getItemMeta().getDisplayName())) {
                        kit.wearCheckLevel(player);
                        plugin.getServer().getScheduler().runTask(plugin, player::closeInventory);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
}