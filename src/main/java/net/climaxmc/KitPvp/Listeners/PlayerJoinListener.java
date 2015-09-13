package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoinListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerJoinListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getMySQL().createPlayerData(player.getUniqueId());
        PlayerData playerData = plugin.getPlayerData(player);

        event.setJoinMessage((player.hasPlayedBefore() ? ChatColor.DARK_AQUA : ChatColor.GOLD) + "Join" + ChatColor.DARK_GRAY + "\u00bb " + player.getName());

        plugin.respawn(player);

        plugin.getMySQL().getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>());

        player.setDisplayName(playerData.getNickname());
        player.setPlayerListName(playerData.getLevelColor() + player.getName());
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Be a part of the community!");
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Vote for which feature you'd like to see on Climax! " + ChatColor.RED + "http://www.strawpoll.me/5131832/");

        if (playerData.hasRank(Rank.OWNER)) {
            if (!player.isOp()) {
                player.setOp(true);
                player.sendMessage(ChatColor.BOLD + "You were opped because you had been previously deopped.");
            }
        }

        int playersOnline = plugin.getServer().getOnlinePlayers().size();

        if (playersOnline > plugin.getConfig().getInt("HighestPlayerCount")) {
            plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "We have reached a new high player count of " + playersOnline + "!");
            plugin.getConfig().set("HighestPlayerCount", playersOnline);
            plugin.saveConfig();
        }
    }
}
