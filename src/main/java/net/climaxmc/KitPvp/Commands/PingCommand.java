package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.PingUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public PingCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        Player target = player;

        if (args.length == 1) {
            target = plugin.getServer().getPlayer(args[0]);

            if (target == null /*|| KitPvp.getVanished().contains(target.getUniqueId())*/) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }
        } else if (args.length > 2) {
            player.sendMessage(ChatColor.RED + " /ping [player]");
        }

        int ping = PingUtils.getPing(target);

        if (player == target) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Your ping is " + ChatColor.GOLD + ping + "ms");
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + target.getName() + "'s" + ChatColor.GRAY + " ping is " + ChatColor.GOLD + ping + "ms");
        }

        return true;
    }
}
