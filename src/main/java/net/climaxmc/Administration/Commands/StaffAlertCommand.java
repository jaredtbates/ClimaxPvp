package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffAlertCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public StaffAlertCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/staffalert <msg>");
            return true;
        }

        String message = StringUtils.join(args, ' ', 0, args.length);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = plugin.getPlayerData(player);
                if (playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                    player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "AntiNub" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + message);
                }
            }
        });

        return true;
    }
}
