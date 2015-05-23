package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TrailsCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public TrailsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            new TrailsInventory(player);
        }
        return false;
    }
}
