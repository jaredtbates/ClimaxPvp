package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetIPCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public GetIPCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.ADMINISTRATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "/getip <player>");
            return true;
        }

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
            PlayerData targetData = plugin.getPlayerData(target);

            if (targetData == null) {
                sender.sendMessage(ChatColor.RED + "That player does not exist!");
                return;
            }

            sender.sendMessage(ChatColor.GRAY + target.getName() + "'s IP is " + ChatColor.RED + targetData.getIp());
        });

        return true;
    }
}
