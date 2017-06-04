package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.common.database.MySQL;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ResetStatsCommand implements CommandExecutor {

    private ClimaxPvp plugin;
    public ResetStatsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            PlayerData playerData = plugin.getPlayerData(player);

            if (playerData.hasRank(Rank.ADMINISTRATOR)) {
                if (args.length == 1) {

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                    if (target != null) {
                        MySQL mySQL = plugin.getMySQL();

                        mySQL.executeUpdate("UPDATE climax_playerdata SET balance='0', kills='0', deaths='0', kdr='0', topks='0' WHERE uuid='" + target.getUniqueId() + "';");

                        player.sendMessage(ChatUtils.color("&cDeleted PlayerData for " + target.getName() + "!"));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/resetstats (player)");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                if (target != null) {
                    MySQL mySQL = plugin.getMySQL();

                    mySQL.executeUpdate("UPDATE climax_playerdata SET balance='0', kills='0', deaths='0', kdr='0', topks='0' WHERE uuid='" + target.getUniqueId() + "';");

                    sender.sendMessage(ChatUtils.color("&cDeleted PlayerData for " + target.getName() + "!"));
                }
            } else {
                sender.sendMessage(ChatColor.RED + "/resetstats (player)");
            }
        }

        return false;
    }
}
