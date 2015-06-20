package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.common.donations.trails.ParticleEffect;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private ClimaxPvp plugin;
    private Donations instance;

    public PlayerMoveListener(ClimaxPvp plugin, Donations instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (instance.getTrailsEnabled().containsKey(player.getUniqueId())) {
            for (Trail trail : Trail.values()) {
                if (trail.equals(instance.getTrailsEnabled().get(player.getUniqueId()))) {
                    Location location = player.getLocation();
                    location.setY(location.getY() + trail.getYOffset());
                    new ParticleEffect(instance.getTrailsEnabled().get(player.getUniqueId()).getData()).sendToLocation(location);
                }
            }
        }

        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            if (player.getLocation().distance(player.getWorld().getSpawnLocation()) <= 150 ||
                    player.getLocation().distance(plugin.getWarpLocation("NoSoup")) <= 100) {
                plugin.respawn(player);
                player.setGameMode(GameMode.SPECTATOR);
                player.setFlySpeed(0.15F);
            }
        }
    }
}
