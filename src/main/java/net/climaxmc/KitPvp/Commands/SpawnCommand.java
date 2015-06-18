package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpawnCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        plugin.respawn(player);
        player.sendMessage(ChatColor.GREEN + "You have been teleported to spawn!");

        return true;
    }
}
