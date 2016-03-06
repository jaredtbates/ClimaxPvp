package net.climaxmc.KitPvp.Commands;
/* Created by GamerBah on 3/6/2016 */

import net.climaxmc.ClimaxPvp;
import net.gpedro.integrations.slack.SlackMessage;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffReqCommand implements CommandExecutor {

    private ClimaxPvp plugin;

    public StaffReqCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/" + label + " <message>");
            player.sendMessage(ChatColor.RED + "Sends a message to offline Staff Members if you need help. Please don't spam!");
            return true;
        }

        String message = StringUtils.join(args, ' ', 1, args.length);

        plugin.getSlackStaffHelp().call(new SlackMessage(">>>*" + player.getName() + ":* _" + message + "_"));

        player.sendMessage(ChatColor.GREEN + "Your message has been sent! A staff member has been notified of your request and should be able to help you shortly!");
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);

        return false;
    }
}
