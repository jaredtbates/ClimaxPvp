package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class YoutubeCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public YoutubeCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.getRank().equals(Rank.YOUTUBE) || playerData.getRank().equals(Rank.TWITCH) || playerData.hasRank(Rank.ADMINISTRATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "/" + label + " <on/off>");
            return true;
        }

        switch (args[0]) {
            case "on":
                playerData.addData("Admin Mode", true);
                player.sendMessage(ChatColor.GREEN + "YouTube/Twitch access to all kits has been enabled!");
                break;
            case "off":
                playerData.removeData("Admin Mode");
                player.sendMessage(ChatColor.GREEN + "YouTube/Twitch access to all kits has been " + ChatColor.RED + "disabled" + ChatColor.GREEN + "!");
                break;
            default:
                player.sendMessage(ChatColor.RED + "/" + label + " <on/off>");
                break;
        }

        return true;
    }
}
