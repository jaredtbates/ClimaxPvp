package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerDropItemListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Material item = event.getItemDrop().getItemStack().getType();

        Player player = event.getPlayer();

        String itemName = event.getItemDrop().getItemStack().getItemMeta().getDisplayName();

        player.updateInventory();

        if (itemName != null) {
            if (itemName.equals(ChatColor.GRAY + "Mode: " + ChatColor.YELLOW + "Soup") || itemName.equals(ChatColor.GRAY + "Mode: " + ChatColor.YELLOW + "Rod/Regen")) {
                event.setCancelled(true);
                return;
            }
        }

        if (item.equals(Material.BOWL) || item.equals(Material.GLASS_BOTTLE) || item.equals(Material.POTION)) {
            return;
        }

        event.setCancelled(true);
    }
}