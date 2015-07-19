package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerCommandPreprocessListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (StringUtils.startsWithIgnoreCase(event.getMessage(), "/me") || StringUtils.startsWithIgnoreCase(event.getMessage(), "/minecraft:me")) {
            event.setCancelled(true);
            return;
        }

        plugin.getServer().getOnlinePlayers().stream().filter(players -> plugin.getPlayerData(players).hasRank(Rank.MODERATOR)).forEach(players -> players.sendMessage(ChatColor.DARK_GRAY + player.getName() + ": " + ChatColor.GRAY + command));
    }
}
