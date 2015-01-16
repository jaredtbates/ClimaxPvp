package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Utils.ParticleEffect;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
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
        for (ParticleEffect.ParticleType type : ParticleEffect.ParticleType.values()) {
            ParticleEffect effect = new ParticleEffect(type, 1, 1, 10);
            if (player.hasPermission("ClimaxPvp.Particles." + type.getName())) {
                effect.sendToLocation(player.getLocation());
            }
        }
    }
}
