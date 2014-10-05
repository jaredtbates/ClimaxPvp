package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {
	Main plugin;
	
	public ItemSpawnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onItemSpawn(final ItemSpawnEvent event) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            @Override
            public void run() {
                event.getEntity().remove();
            }
 
        }, 20L);
	}
}
