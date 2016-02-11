package net.climaxmc.KitPvp.Commands;
/* Created by GamerBah on 2/10/2016 */


import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OptionsCommand implements CommandExecutor {

    private ClimaxPvp plugin;

    public OptionsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(ChatColor.RED + "/options <player>");
        }

        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "/options <player>");
        }

        player.sendMessage(ChatColor.GREEN + "Opening options for " + args[0]);

        return true;
    }
}
