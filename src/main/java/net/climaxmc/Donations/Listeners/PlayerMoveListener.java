package net.climaxmc.Donations.Listeners;

import net.climaxmc.API.ParticleEffect;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Donations.Enums.Trail;
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
            if ((player.getLocation().getBlockY() <= -5) || (player.getLocation().getBlockY() >= 100)
                    || (player.getLocation().getBlockX() >= -590 || player.getLocation().getBlockX() <= -760)
                    || (player.getLocation().getBlockZ() >= 1160 || player.getLocation().getBlockZ() <= 980)) {
                plugin.respawn(player);
                player.setGameMode(GameMode.SPECTATOR);
                player.setFlySpeed(0.15F);
            }
        }
    }
}
