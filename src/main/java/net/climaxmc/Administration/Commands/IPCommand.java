package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IPCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public IPCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.ADMINISTRATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/ip <player>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (plugin.getPlayerData(target).hasRank(Rank.OWNER)) {
            player.sendMessage(ChatColor.RED + "You may not check an owner's IP!");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + target.getName() + "'s IP is " + target.getAddress().getAddress().getHostAddress());

        return true;
    }
}
