package net.climaxmc.Administration.Runnables;

import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;

public class AutoBroadcastRunnable implements Runnable {
    private ClimaxPvp plugin;
    private int amount = 0;

    public AutoBroadcastRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            plugin.sendActionBar(player, plugin.getConfig().getStringList("AutoBroadcast.Messages").get(amount++));
            //if (amount > plugin.getConfig().getStringList("AutoBroadcast"))
        }
    }
}
