package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
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
        int kills = playerData.getKills();
        Rank rank = playerData.getRank();

        String level = playerData.getLevelColor() + Integer.toString(kills);

        if (playerData.hasPerk(() -> "Chat_Color")) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }

        if (playerData.hasRank(Rank.TRUSTED)) {
            event.setFormat(level + ChatColor.DARK_GRAY + " " + ChatColor.BOLD + "{" + rank.getColor() + "" + ChatColor.BOLD + "" + rank.getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "}" + playerData.getLevelColor() + " %s" + ChatColor.RESET + ": %s");
        } else {
            event.setFormat(level + playerData.getLevelColor() + " %s" + ChatColor.RESET + ": %s");
        }
    }
}
