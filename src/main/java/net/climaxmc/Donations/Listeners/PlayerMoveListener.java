package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Utils.ParticleEffect;
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
        if (instance.getParticlesEnabled().containsKey(player.getUniqueId())) {
            for (ParticleEffect.ParticleType type : ParticleEffect.ParticleType.values()) {
                if (instance.getParticlesEnabled().get(player.getUniqueId()).equals(type)) {
                    Location location = player.getLocation();
                    location.setY(location.getY() + 0.5);
                    new ParticleEffect(type, 0, 0, 0).sendToLocation(location);
                }
            }
        }
    }
}
