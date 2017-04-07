package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerPickupItemListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {

        if (ClimaxPvp.getInstance().isSpectating.contains(event.getPlayer().getUniqueId())
                || ClimaxPvp.getInstance().deadPeoples.contains(event.getPlayer())
                || KitPvp.getVanished().contains(event.getPlayer().getUniqueId())
                || KitPvp.getChecking().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }

        if (event.getItem().getItemStack().getType().equals(Material.MUSHROOM_SOUP)) {
            return;
        }
        event.setCancelled(true);
    }
}
