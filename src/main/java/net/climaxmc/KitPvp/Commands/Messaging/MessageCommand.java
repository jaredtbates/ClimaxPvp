package net.climaxmc.KitPvp.Commands.Messaging;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public MessageCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length <= 1) {
            player.sendMessage(ChatColor.RED + "/" + label + " <player> <message>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        plugin.getMessagers().put(player.getUniqueId(), target.getUniqueId());
        plugin.getMessagers().put(target.getUniqueId(), player.getUniqueId());

        String message = StringUtils.join(args, ' ', 1, args.length);

        player.sendMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.BOLD + " -> " + ChatColor.AQUA + "" + ChatColor.BOLD + target.getName() + ChatColor.AQUA + ": " + message.trim());
        target.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " -> " + ChatColor.DARK_AQUA + target.getName() + ChatColor.AQUA + ": " + message);

        return true;
    }
}
