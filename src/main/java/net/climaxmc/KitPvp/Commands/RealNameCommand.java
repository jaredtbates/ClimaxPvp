package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class RealNameCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public RealNameCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/realname [nickname]");
            return true;
        }

        plugin.getServer().getOnlinePlayers().stream().filter(players -> StringUtils.containsIgnoreCase(players.getDisplayName(), args[0]))
                .forEach(players -> player.sendMessage(players.getDisplayName() + ChatColor.RESET + "'s real name is " + players.getName() + "."));

        return true;
    }
}
