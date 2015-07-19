package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ReportCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/report <player> <reason>");
            return true;
        }

        Player reported = plugin.getServer().getPlayer(args[0]);

        if (reported == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
        }

        return true;
    }
}
