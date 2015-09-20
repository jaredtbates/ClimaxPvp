package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RankCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public RankCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
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
                PlayerData playerData = plugin.getPlayerData(target);
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
