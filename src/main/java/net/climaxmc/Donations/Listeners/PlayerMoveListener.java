package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {
    private ClimaxPvp plugin;
    private Donations instance;

    public PlayerMoveListener(ClimaxPvp plugin, Donations instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!(event.getTo().getBlockX() == player.getLocation().getBlockX())) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                public void run() {
                    if (instance.getParticlesEnabled().containsKey(player.getUniqueId())) {
                        for (ParticleEffect.ParticleType type : ParticleEffect.ParticleType.values()) {
                            if (instance.getParticlesEnabled().get(player.getUniqueId()).getType().equals(type)) {
                                Location location = player.getLocation();
                                location.setY(location.getY() + 0.5);
                                new ParticleEffect(instance.getParticlesEnabled().get(player.getUniqueId())).sendToLocation(location);
                            }
                        }
                    }
                }
            }, 20);
        }
    }
}
