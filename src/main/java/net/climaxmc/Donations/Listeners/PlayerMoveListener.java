package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerMoveListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        for (ParticleEffect effect : ParticleEffect.values()) {
            if (player.hasPermission("ClimaxPvp.Particles." + effect.getName())) {
                Location location = player.getLocation();
                location.setY(location.getY() + 0.5);
                if (effect.equals(ParticleEffect.WATER_BUBBLE)) {
                    effect.display(0, 0, 0, 1, 1, location, 10);
                }
            }
        }
    }
}
