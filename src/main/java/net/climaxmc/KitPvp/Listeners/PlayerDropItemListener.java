package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Material;
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
        if (item != Material.BOWL && item != Material.GLASS_BOTTLE && item != Material.POTION && item != Material.SPLASH_POTION && item != Material.MUSHROOM_SOUP) {
            event.setCancelled(true);
        }
    }
}