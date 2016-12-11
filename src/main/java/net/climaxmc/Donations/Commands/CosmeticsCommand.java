package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import net.climaxmc.KitPvp.Menus.CosmeticsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public CosmeticsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CosmeticsMenu cosmeticsMenu = new CosmeticsMenu(plugin);
            cosmeticsMenu.openInventory(player);
        }
        return false;
    }
}