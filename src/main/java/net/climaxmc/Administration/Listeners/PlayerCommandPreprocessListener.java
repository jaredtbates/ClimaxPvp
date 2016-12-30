package net.climaxmc.Administration.Listeners;

import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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

        if (ClimaxPvp.inDuel.contains(player)) {
            if (StringUtils.startsWithIgnoreCase(event.getMessage(), "/kill") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/ping") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/msg") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/r")) {
                return;
            } else {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You cannot do commands in a duel!");
                event.setCancelled(true);
            }
        }

        if (KitPvp.getVanished().contains(player.getUniqueId())) {
            if (StringUtils.startsWithIgnoreCase(event.getMessage(), "/spawn")
                    || StringUtils.startsWithIgnoreCase(event.getMessage(), "/warp")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You may not go to spawn or warp while vanished! (/vanish)");
                return;
            }
        }
        if (ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
            if (!(StringUtils.startsWithIgnoreCase(event.getMessage(), "/spec") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/spectate")) ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/ping") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/msg") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/r")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You may only to /spec while spectating!");
                return;
            }
        }
        if (ClimaxPvp.deadPeoples.contains(player)) {
            if (!(StringUtils.startsWithIgnoreCase(event.getMessage(), "/spawn") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/warp")) ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/ping") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/msg") ||
                    StringUtils.startsWithIgnoreCase(event.getMessage(), "/r")) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You may only do /spawn or /warp while dead!");
                return;
            }
        }

        plugin.getServer().getOnlinePlayers().stream().filter(players ->
                plugin.getPlayerData(players).hasRank(Rank.TRIAL_MODERATOR) && ChatCommands.cmdspies.contains(players.getUniqueId()))
                .forEach(players -> players.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.DARK_GRAY + player.getName() + ": " + ChatColor.GRAY + command));
    }
}
