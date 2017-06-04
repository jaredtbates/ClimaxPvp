package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AFKTimer implements Listener {

    private ClimaxPvp plugin;
    public AFKTimer(ClimaxPvp plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (locations.containsKey(players.getUniqueId())) {
                        if (locations.get(players.getUniqueId()).equals(players.getLocation())) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kick " + players.getName() + " AFK -s");
                        } else {
                            locations.put(players.getUniqueId(), players.getLocation());
                        }
                    } else {
                        locations.put(players.getUniqueId(), players.getLocation());
                    }
                }
            }
        }, 0L, (20L * 60) * 4);
    }

    private Map<UUID, Location> locations = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        locations.remove(event.getPlayer().getUniqueId());
    }
}
