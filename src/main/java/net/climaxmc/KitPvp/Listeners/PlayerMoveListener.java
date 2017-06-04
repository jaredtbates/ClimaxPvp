package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Administration.Listeners.CombatLogListeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.antinub.AntiNub;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
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

        if (CombatLogListeners.getTagged().containsKey(player.getUniqueId())) {
            if (plugin.isWithinProtectedRegion(event.getTo()) && !plugin.isWithinProtectedRegion(event.getFrom())) {
                player.setVelocity(event.getFrom().getBlock().getLocation().toVector().subtract(event.getTo().getBlock().getLocation().toVector()).multiply(2).setY(0.5));
                player.sendMessage(ChatUtils.color("&f\u00BB &cYou cannot enter spawn while in combat!"));
                player.playSound(player.getLocation(), Sound.NOTE_BASS, 1F, 0.5F);
                AntiNub.alertsEnabled.put(player.getUniqueId(), false);
                ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        AntiNub.alertsEnabled.put(player.getUniqueId(), true);
                    }
                }, 20L * 2);
            }
        }

        if (player.getLocation().getBlockY() <= 0) {
            player.damage(1000);
        }
    }
}
