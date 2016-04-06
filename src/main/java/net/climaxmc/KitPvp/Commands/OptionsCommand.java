package net.climaxmc.KitPvp.Commands;
/* Created by GamerBah on 4/3/2016 */

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.OptionsMenu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
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

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Try this: /options <player>");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);

        if (target == null) {
            player.sendMessage(org.bukkit.ChatColor.RED + "That player is not online!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;
        }

        OptionsMenu optionsMenu = new OptionsMenu(plugin);
        optionsMenu.openInventory(player, target);

        return false;
    }

}