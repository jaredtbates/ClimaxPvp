package net.climaxmc.KitPvp.Commands.Messaging;// AUTHOR: gamer_000 (1/16/2016)

import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {
    private ClimaxPvp plugin;

    public StaffChat(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (command.getName().equalsIgnoreCase("staff")) {
            if (!playerData.hasRank(Rank.HELPER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
            } else {
                if (args.length == 0) {
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    player.sendMessage(ChatColor.RED + "Try this: /" + label + " <message>");
                }
                if (args.length > 0) {
                    String message = StringUtils.join(args, ' ', 0, args.length);

                    plugin.getServer().getOnlinePlayers().stream().filter(players ->
                            plugin.getPlayerData(players).hasRank(Rank.HELPER) && ChatCommands.staffChat.contains(players.getUniqueId()))
                            .forEach(players -> players.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[STAFF] "
                                    + ChatColor.RED + player.getName() + ": " + message));
                }
            }
        }

        return false;
    }

}
