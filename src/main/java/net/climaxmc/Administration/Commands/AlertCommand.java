package net.climaxmc.Administration.Commands;
/* Created by GamerBah on 2/15/2016 */


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

public class AlertCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public AlertCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);

            if (!playerData.hasRank(Rank.ADMINISTRATOR)) {
                player.sendMessage(ChatColor.RED + " You do not have permission to execute that command!");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + " /alert <message>");
                return true;
            }

            String message = StringUtils.join(args, ' ', 0, args.length);

            for (Player players : plugin.getServer().getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.NOTE_PIANO, 2, 1.5F);
            }

            plugin.getServer().broadcastMessage(" ");
            plugin.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + " [ALERT] " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message));
            plugin.getServer().broadcastMessage(" ");

        }
        return false;
    }
}
