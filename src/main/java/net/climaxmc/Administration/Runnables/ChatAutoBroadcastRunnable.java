package net.climaxmc.Administration.Runnables;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;

public class ChatAutoBroadcastRunnable implements Runnable {
    private ClimaxPvp plugin;
    private int amount = 0;

    public ChatAutoBroadcastRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (amount >= plugin.getConfig().getStringList("ChatAutoBroadcast.Messages").size()) {
            amount = 0;
        }

        plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getStringList("ChatAutoBroadcast.Messages").get(amount++)));
    }
}
