package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerCommandPreprocessListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        for (Player players : plugin.getServer().getOnlinePlayers()) {
            if (players.hasPermission("ClimaxPvp.CommandSpy")) {
                players.sendMessage("§8" + player.getName() + ": §7" + command);
            }
        }
    }
}
