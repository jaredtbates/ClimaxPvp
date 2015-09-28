package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;

public class PlayerJoinListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerJoinListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerJoin(AsyncPlayerPreLoginEvent event) {
        plugin.getMySQL().createPlayerData(event.getUniqueId());
        PlayerData playerData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(event.getUniqueId()));

        if (playerData != null) {
            playerData.getPunishments().stream().filter(punishment -> punishment.getType().equals(Punishment.PunishType.BAN)).forEach(punishment -> {
                if (System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration())) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You were temporarily banned by " + plugin.getServer().getOfflinePlayer(punishment.getPunisherUUID()).getName()
                            + " for " + punishment.getReason() + ".\n"
                            + "You have " + Time.toString(punishment.getTime() + punishment.getExpiration() - System.currentTimeMillis()) + " left in your ban.\n"
                            + "Appeal on forum.climaxmc.net if you believe that this is in error!");
                } else if (punishment.getExpiration() == -1) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You were permanently banned by " + plugin.getServer().getOfflinePlayer(punishment.getPunisherUUID()).getName()
                            + " for " + punishment.getReason() + ".\n"
                            + "Appeal on forum.climaxmc.net if you believe that this is in error!");
                }
            });
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);

        event.setJoinMessage((player.hasPlayedBefore() ? ChatColor.DARK_AQUA : ChatColor.GOLD) + "Join" + ChatColor.DARK_GRAY + "\u00bb " + player.getName());

        plugin.respawn(player);

        plugin.getMySQL().getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>());

        if (playerData != null) {
            player.setDisplayName(playerData.getNickname());
            player.setPlayerListName(playerData.getLevelColor() + player.getName());

            if (playerData.hasRank(Rank.OWNER)) {
                if (!player.isOp()) {
                    player.setOp(true);
                    player.sendMessage(ChatColor.BOLD + "You were opped because you had been previously deopped.");
                }
            }
        }

        int playersOnline = plugin.getServer().getOnlinePlayers().size();

        if (playersOnline > plugin.getConfig().getInt("HighestPlayerCount")) {
            plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "We have reached a new high player count of " + playersOnline + "!");
            plugin.getConfig().set("HighestPlayerCount", playersOnline);
            plugin.saveConfig();
        }

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        player.setScoreboard(board);

        PermissionAttachment attachment = player.addAttachment(plugin);

        if (playerData != null && playerData.hasRank(Rank.MODERATOR)) {
            attachment.setPermission("minecraft.command.tp", true);
            attachment.setPermission("bukkit.command.teleport", true);
        }

        attachment.setPermission("bukkit.command.tps", true);
    }
}
