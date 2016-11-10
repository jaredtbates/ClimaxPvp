package net.climaxmc.Administration.Runnables;

import net.climaxmc.Administration.Commands.ChatCommands;
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

        if (ChatCommands.chatSilenced == false) {
            plugin.getServer().broadcastMessage(" §f§l[§7=§8------------------§7=§f§l[*]§7=§8------------------§7=§f§l]");
            plugin.getServer().broadcastMessage(" ");
            plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getStringList("ChatAutoBroadcast.Messages").get(amount++)));
            plugin.getServer().broadcastMessage(" ");
            plugin.getServer().broadcastMessage(" §f§l[§7=§8-------------------§7==§8-------------------§7=§f§l]");
        }
    }
}
