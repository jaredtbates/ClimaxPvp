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
        for (ParticleEffect.ParticleType type : ParticleEffect.ParticleType.values()) {
            ParticleEffect effect = new ParticleEffect(type, 1, 1, 10);
            if (player.hasPermission("ClimaxPvp.Particles." + type.getName())) {
                //effect.sendToLocation(player.getLocation());
            }
        }
        Location location = player.getLocation();
        location.setY(location.getY() + 0.5);
        ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0, 0, 0);
        //effect.sendToLocation(location);
    }
}
