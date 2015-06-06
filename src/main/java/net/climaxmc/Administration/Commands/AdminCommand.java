package net.climaxmc.Administration.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.API.Rank;
import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
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

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/admin <on/off>");
            return true;
        }

        switch (args[0]) {
            case "on":
                playerData.addData("Admin Mode", true);
                player.sendMessage(ChatColor.GREEN + "Admin access to all kits has been enabled!");
                break;
            case "off":
                playerData.removeData("Admin Mode");
                player.sendMessage(ChatColor.GREEN + "Admin access to all kits has been " + ChatColor.RED + "disabled" + ChatColor.GREEN + "!");
                break;
            default:
                player.sendMessage(ChatColor.RED + "/admin <on/off>");
                break;
        }

        return true;
    }
}
