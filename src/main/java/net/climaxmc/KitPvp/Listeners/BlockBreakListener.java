package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.SpectateCommand;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private ClimaxPvp plugin;

    public BlockBreakListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
        if (!player.isOp()) {
            event.setCancelled(true);
        }
        if (ClimaxPvp.deadPeoples.contains(player)) {
            event.setCancelled(true);
        }
        if (ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
        if (KitPvp.getVanished().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}