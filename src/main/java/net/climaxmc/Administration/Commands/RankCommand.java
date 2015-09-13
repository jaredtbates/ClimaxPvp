package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RankCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public RankCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CachedPlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.OWNER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "/rank <player> <rank>");
            return true;
        }

        OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player does not exist!");
            return true;
        }

        for (Rank rank : Rank.values()) {
            if (rank.toString().equalsIgnoreCase(args[1])) {
                CachedPlayerData playerData = plugin.getPlayerData(target);
                if (playerData != null) {
                    playerData.setRank(rank);
                    sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s rank to " + WordUtils.capitalizeFully(rank.toString()) + ".");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "That player does not exist!");
                    return true;
                }
            }
        }

        sender.sendMessage(ChatColor.RED + "Incorrect rank! Please use one of the following: " + WordUtils.capitalizeFully(Arrays.toString(Rank.values()).replace("[", "").replace("]", "")));
        return true;
    }
}
