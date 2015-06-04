package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Donations.Enums.Trail;
import org.bukkit.entity.Player;
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
        Player player = (Player) event.getWhoClicked();
        if (inventory.getName().equals("§a§lTrail Settings")) {
            event.setCancelled(true);
            player.closeInventory();
            Trail trail = null;

            for (Trail possibleTrail : Trail.values()) {
                if (event.getCurrentItem().getType().equals(possibleTrail.getMaterial())) {
                    trail = possibleTrail;
                }
            }

            if (trail != null) {
                if (instance.getTrailsEnabled().containsKey(player.getUniqueId()) && instance.getTrailsEnabled().get(player.getUniqueId()).equals(trail)) {
                    instance.getTrailsEnabled().remove(player.getUniqueId());
                    player.sendMessage("§aYou have removed your trail!");
                } else {
                    instance.getTrailsEnabled().put(player.getUniqueId(), trail);
                    player.sendMessage("§aYou have applied the " + trail.getName() + " trail!");
                }
            }
        }
    }
}
