package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    private ClimaxPvp plugin;
    private Donations instance;

    public InventoryClickListener(ClimaxPvp plugin, Donations instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals("§a§lParticle Settings")) {
            int slot = event.getSlot();
            switch (slot) {
                case 53:

                    return;
                default:
                    return;
            }
        }
    }
}
