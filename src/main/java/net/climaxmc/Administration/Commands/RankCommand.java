package net.climaxmc.Administration.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.API.Rank;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
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
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.OWNER) && !player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "/rank <player> <rank>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        PlayerData targetData = plugin.getPlayerData(target);

        for (Rank rank : Rank.values()) {
            if (rank.toString().equalsIgnoreCase(args[1])) {
                targetData.setRank(rank);
                sender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s rank to " + WordUtils.capitalizeFully(rank.toString()) + ".");
                target.sendMessage(ChatColor.GREEN + "Your rank has been set to " + WordUtils.capitalizeFully(rank.toString()) + " by " + sender.getName() + ".");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Incorrect rank! Please use one of the following: " + WordUtils.capitalizeFully(Arrays.toString(Rank.values()).replace("[", "").replace("]", "")));

        return true;
    }
}
