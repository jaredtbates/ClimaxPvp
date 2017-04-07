package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSpawnListener implements Listener {
    private ClimaxPvp plugin;

    public ItemSpawnListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getEntity().remove();
            }
        }.runTaskLater(plugin, 20 * 10);
    }
}
