package net.climaxmc.Administration.Listeners;

import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.database.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    private ClimaxPvp plugin;

    public AsyncPlayerChatListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (VanishCommand.getVanished().contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot chat while vanished!");
            return;
        }

        PlayerData playerData = plugin.getPlayerData(player);
        int kills = playerData.getKills();
        Rank rank = playerData.getRank();

        String level = playerData.getLevelColor() + Integer.toString(kills);

        if (playerData.hasRank(Rank.NINJA)) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
            ChatColor color = rank.getColor();
            event.setFormat(level + ChatColor.DARK_GRAY + " " + ChatColor.BOLD + "{" + (color == null ? "" : color) + "" + ChatColor.BOLD + "" + rank.getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "}" + playerData.getLevelColor() + " %s" + ChatColor.RESET + ": %s");
        } else {
            event.setFormat(level + playerData.getLevelColor() + " %s" + ChatColor.RESET + ": %s");
        }
    }
}
