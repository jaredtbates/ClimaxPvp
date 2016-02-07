package net.climaxmc.KitPvp.Listeners;
/* Created by GamerBah on 1/24/2016 */


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Commands.ReportCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    private ClimaxPvp plugin;

    public InventoryCloseListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals("Climax Reports")) {
            if (ReportCommand.reportBuilders.containsKey(player.getUniqueId())) {
                ReportCommand.reportBuilders.remove(player.getUniqueId());
            } else {
                return;
            }
            if (ReportCommand.reportArray.containsKey(player.getUniqueId())) {
                ReportCommand.reportArray.remove(player.getUniqueId());
            } else {
                return;
            }
        }
    }
}
