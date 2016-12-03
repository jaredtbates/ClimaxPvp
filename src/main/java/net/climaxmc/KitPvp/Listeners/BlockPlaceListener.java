package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private ClimaxPvp plugin;

    public BlockPlaceListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
        if (ClimaxPvp.deadPeoples.contains(player)) {
            event.setCancelled(true);
        }
        if (ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}