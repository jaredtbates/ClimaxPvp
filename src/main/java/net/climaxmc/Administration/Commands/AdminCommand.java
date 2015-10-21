package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

        if (!playerData.hasRank(Rank.ADMINISTRATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/admin <on/off" + (playerData.hasRank(Rank.OWNER) ? "/allon/alloff" : "") + ">");
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
            case "allon":
                if (playerData.hasRank(Rank.OWNER)) {
                    plugin.getConfig().set("AllKitsEnabled", true);
                    plugin.saveConfig();
                    KitManager.setAllKitsEnabled(true);
                    player.sendMessage(ChatColor.GREEN + "All kits have been enabled for all players!");
                    break;
                }
            case "alloff":
                if (playerData.hasRank(Rank.OWNER)) {
                    plugin.getConfig().set("AllKitsEnabled", false);
                    plugin.saveConfig();
                    KitManager.setAllKitsEnabled(false);
                    player.sendMessage(ChatColor.GREEN + "All kits have been " + ChatColor.RED + "disabled" + ChatColor.GREEN + " for all players!");
                    break;
                }
            default:
                player.sendMessage(ChatColor.RED + "/admin <on/off" + (playerData.hasRank(Rank.OWNER) ? "/allon/alloff" : "") + ">");
                break;
        }

        return true;
    }
}
