package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.BossBar;
import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    ClimaxPvp plugin;

    public PlayerJoinListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.setJoinMessage("§3Join§8» " + player.getName());
        plugin.sendToSpawn(player);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                try {
                    BossBar.getInstance().setStatus(player, "§a§lWelcome to Climax!", 50, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 200);

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                try {
                    BossBar.getInstance().setStatus(player, "§b§lDonate at donate.climaxmc.net!", 100, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 200);
    }
}
