package net.climaxmc.Administration.Listeners;

import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
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
        PlayerData playerData = plugin.getPlayerData(player);
        String command = event.getMessage();

        if (StringUtils.startsWithIgnoreCase(event.getMessage(), "/me")
                || StringUtils.startsWithIgnoreCase(event.getMessage(), "/minecraft:")
                || StringUtils.startsWithIgnoreCase(event.getMessage(), "/bukkit:")
                || StringUtils.startsWithIgnoreCase(event.getMessage(), "/spigot:")) {
            event.setCancelled(true);
            return;
        }

        plugin.getServer().getOnlinePlayers().stream().filter(players ->
                plugin.getPlayerData(players).hasRank(Rank.HELPER) && ChatCommands.cmdspies.contains(players.getUniqueId()))
                .forEach(players -> players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + " [CmdSpy] " + ChatColor.DARK_GRAY + player.getName() + ": " + ChatColor.GRAY + command));
    }
}
