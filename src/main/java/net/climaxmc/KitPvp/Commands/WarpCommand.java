package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class WarpCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public WarpCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.GREEN + "Available warps: " + ChatColor.AQUA + plugin.getWarpsConfig().getKeys(false).stream().collect(Collectors.joining(", ")));
        } else if (args.length >= 1) {

        }

        return true;
    }
}
