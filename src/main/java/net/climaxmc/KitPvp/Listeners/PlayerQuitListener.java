package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerQuitListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (KitPvp.inKit.contains(player.getUniqueId())) {
            KitPvp.inKit.remove(player.getUniqueId());
        }

        if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
            KitPvp.killStreak.remove(player.getUniqueId());
        }

        event.setQuitMessage(ChatColor.RED + "Quit " + ChatColor.DARK_GRAY + "\u00bb " + player.getName());
    }
}
