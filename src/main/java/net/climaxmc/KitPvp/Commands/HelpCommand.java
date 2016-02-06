package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public HelpCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (args.length == 1 && args[0].equals("staff") && playerData.hasRank(Rank.HELPER)) {
            player.sendMessage(ChatColor.RED + " /punish" + ChatColor.GRAY + " - Punish a player with warn/kick/tempmute" + (playerData.hasRank(Rank.MODERATOR) ? "/mute/tempban/ban" : ""));
            player.sendMessage(ChatColor.RED + " /inventory" + ChatColor.GRAY + " - Check if a player is autosouping");
            player.sendMessage(ChatColor.RED + " /check" + ChatColor.GRAY + " - Check if a player is hacking");

            if (playerData.hasRank(Rank.MODERATOR)) {
                player.sendMessage(ChatColor.RED + " /vanish" + ChatColor.GRAY + " - Vanish from the game (removes you from the player list and fake logs you out)");
                player.sendMessage(ChatColor.RED + " /clearchat" + ChatColor.GRAY + " - Clear chat so that players cannot see messages sent");
            }

            if (playerData.hasRank(Rank.ADMINISTRATOR)) {
                player.sendMessage(ChatColor.RED + " /admin" + ChatColor.GRAY + " - Enable/disable all kits (used mainly for testing)");
                player.sendMessage(ChatColor.RED + " /economy" + ChatColor.GRAY + " - Access economy settings");
            }

            return true;
        }

        player.sendMessage(plugin.getHelp().toArray(new String[plugin.getHelp().size()]));

        if (playerData.hasRank(Rank.HELPER)) {
            player.sendMessage(ChatColor.RED + " Execute " + ChatColor.GREEN + "/help staff" + ChatColor.RED + " for information on staff commands.");
        }

        return true;
    }
}
