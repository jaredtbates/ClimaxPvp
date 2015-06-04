package net.climaxmc.Donations.Listeners;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Donations.Enums.Trail;
import net.md_5.bungee.api.ChatColor;
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

            for (Trail trail : Trail.values()) {
                if (event.getCurrentItem().getType().equals(trail.getMaterial())) {
                    PlayerData playerData = plugin.getPlayerData(player);

                    if (!playerData.hasPerk(trail)) {
                        player.sendMessage(ChatColor.RED + "Please donate at https://donate.climaxmc.net for access to this trail!");
                        return;
                    }

                    if (instance.getTrailsEnabled().containsKey(player.getUniqueId()) && instance.getTrailsEnabled().get(player.getUniqueId()).equals(trail)) {
                        instance.getTrailsEnabled().remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "You have removed your trail!");
                    } else {
                        instance.getTrailsEnabled().put(player.getUniqueId(), trail);
                        player.sendMessage(ChatColor.GREEN + "You have applied the " + trail.getName() + " trail!");
                    }
                }
            }
        }
    }
}
