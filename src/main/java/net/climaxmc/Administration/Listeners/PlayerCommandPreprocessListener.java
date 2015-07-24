package net.climaxmc.Administration.Listeners;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.permissions.PermissionAttachment;

public class PlayerCommandPreprocessListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerCommandPreprocessListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        CachedPlayerData playerData = plugin.getPlayerData(player);
        String command = event.getMessage();

        if (StringUtils.startsWithIgnoreCase(event.getMessage(), "/me") || StringUtils.startsWithIgnoreCase(event.getMessage(), "/minecraft:me")) {
            event.setCancelled(true);
            return;
        }

        if (VanishCommand.getVanished().contains(player.getUniqueId()) || CheckCommand.getChecking().contains(player.getUniqueId())) {
            if (playerData.hasRank(Rank.MODERATOR)) {
                PermissionAttachment attachment = player.addAttachment(plugin);
                attachment.setPermission("bukkit.command.teleport", true);
                attachment.setPermission("minecraft.command.tp", true);

                plugin.getServer().getScheduler().runTaskLater(plugin, attachment::remove, 1);
            }
        }

        plugin.getServer().getOnlinePlayers().stream().filter(players -> plugin.getPlayerData(players).hasRank(Rank.HELPER)).forEach(players -> players.sendMessage(ChatColor.DARK_GRAY + player.getName() + ": " + ChatColor.GRAY + command));
    }
}
