package net.climaxmc.Administration.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.API.Rank;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

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
            if (!playerData.hasRank(Rank.OWNER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length < 2 || args.length > 3) {
            sender.sendMessage(ChatColor.RED + "Not enough arguments! Try this: /rank <user> <new-rank>");
        } else {
            for (Rank rank : Rank.values()) {
                if (rank.toString().equalsIgnoreCase(args[1])) {
                }
            }
        }
        return true;
    }
}
