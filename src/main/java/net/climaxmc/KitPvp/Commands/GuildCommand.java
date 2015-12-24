package net.climaxmc.KitPvp.Commands;// AUTHOR: gamer_000 (12/13/2015)

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuildCommand implements CommandExecutor {

    private ClimaxPvp plugin;

    public GuildCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("guild")) {
            player.sendMessage(ChatColor.RED + "Hmm...");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
        return false;
    }
}
