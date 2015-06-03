package net.climaxmc.Administration.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.API.Rank;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public AdminCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.ADMINISTRATOR) || player.isOp())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "/admin <on/off>");
            return true;
        }

        if (args[0].equals("on")) {
            playerData.addData("Admin Mode", true);
            player.sendMessage(ChatColor.GREEN + "Admin access to all kits has been enabled!");
        } else if (args[0].equals("off")) {
            playerData.removeData("Admin Mode");
            player.sendMessage(ChatColor.GREEN + "Admin access to all kits has been " + ChatColor.RED + "disabled " + ChatColor.GREEN + "!");
        } else {
            player.sendMessage(ChatColor.RED + "/admin <on/off>");
        }

        return true;
    }
}
