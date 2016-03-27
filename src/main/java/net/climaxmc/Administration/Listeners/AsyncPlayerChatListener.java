package net.climaxmc.Administration.Listeners;

import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.Administration.Commands.StaffChatCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    private ClimaxPvp plugin;

    public AsyncPlayerChatListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (StaffChatCommand.getToggled().contains(player.getUniqueId())) {
            event.setCancelled(true);
            plugin.getServer().getOnlinePlayers().stream().filter(players ->
                    plugin.getPlayerData(players).hasRank(Rank.HELPER))
                    .forEach(players -> players.sendMessage(ChatColor.YELLOW + " " + ChatColor.BOLD + "[STAFF] "
                            + ChatColor.RED + player.getName() + ": " + event.getMessage()));
            return;
        }

        if (VanishCommand.getVanished().contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + " You cannot chat while vanished!");
            return;
        }

        PlayerData playerData = plugin.getPlayerData(player);

        playerData.getPunishments().stream().filter(punishment -> punishment.getType().equals(Punishment.PunishType.MUTE)).forEach(punishment -> {
            PlayerData punisherData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(punishment.getPunisherUUID()));
            if (System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + " You were temporarily muted by " + plugin.getServer().getOfflinePlayer(punisherData.getUuid()).getName()
                        + " for " + punishment.getReason() + ".\n"
                        + " You have " + Time.toString(punishment.getTime() + punishment.getExpiration() - System.currentTimeMillis()) + " left in your mute.\n"
                        + " Appeal on climaxmc.net/forum if you believe that this is an error!");
            } else if (punishment.getExpiration() == -1) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + " You were permanently muted by " + plugin.getServer().getOfflinePlayer(punisherData.getUuid()).getName()
                        + " for " + punishment.getReason() + ".\n"
                        + " Appeal on climaxmc.net/forum if you believe that this is an error!");
            }
        });

        Rank rank = playerData.getRank();

        if (playerData.hasRank(Rank.NINJA)) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
            ChatColor color = rank.getColor();
            event.setFormat((color == null ? "" : color) + " " + ChatColor.BOLD + "[" + rank.getPrefix() + "]" + playerData.getLevelColor() + " %s" + ChatColor.RESET + " \u00BB " + ChatColor.WHITE + "%s");
            //event.setFormat(level + ChatColor.DARK_GRAY + " " + ChatColor.BOLD + "{" + (color == null ? "" : color) + "" + ChatColor.BOLD + "" + rank.getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "}" + playerData.getLevelColor() + " %s" + ChatColor.RESET + ": %s");
        } else {
            event.setFormat(playerData.getLevelColor() + " %s" + ChatColor.RESET + " \u00BB " + ChatColor.GRAY + "%s");
        }

        /*if (StringUtils.containsIgnoreCase(event.getMessage(), "apply") && StringUtils.containsIgnoreCase(event.getMessage(), "staff")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "We are not currently accepting staff applications.");
        }*/

        if (ChatCommands.chatSilenced && !playerData.hasRank(Rank.HELPER)) {
            event.setCancelled(true);
        }
    }
}
