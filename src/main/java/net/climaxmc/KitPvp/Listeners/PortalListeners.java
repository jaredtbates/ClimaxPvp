package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PortalListeners implements Listener {
    private ClimaxPvp plugin;

    public PortalListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (event.getBlock().getType().equals(Material.PORTAL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void rotationFix(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.PORTAL)) {
            float yaw = event.getPlayer().getLocation().getYaw();

            if (yaw < 0) {
                yaw += 360;
            }

            yaw %= 360;
            int direction = (int) ((yaw + 8) / 22.5);

            if (direction == 0 || direction == 1 || direction == 15 || direction == 9 || direction == 8 || direction == 7 || direction == 16 || direction == 14) {
                event.getBlockPlaced().setData((byte) 1);
            } else if (direction == 4 || direction == 6 || direction == 3 || direction == 13 || direction == 12 || direction == 11 || direction == 2 || direction == 5 || direction == 10) {
                event.getBlockPlaced().setData((byte) 0);
            }
        }
    }
}
