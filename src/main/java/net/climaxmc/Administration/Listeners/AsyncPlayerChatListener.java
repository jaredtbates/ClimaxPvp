package net.climaxmc.Administration.Listeners;

import net.climaxmc.API.PlayerData;
import net.climaxmc.API.Rank;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    private ClimaxPvp plugin;

    public AsyncPlayerChatListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        Rank rank = playerData.getRank();

        if (playerData.hasRank(Rank.TRUSTED)) {
            event.setFormat(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "{" + rank.getColor() + "" + ChatColor.BOLD + "" + rank.getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "}" + ChatColor.RESET + " %s" + ChatColor.RESET + ": %s");
        } else {
            event.setFormat(ChatColor.RESET + "%s" + ChatColor.RESET + ": %s");
        }
    }
}
