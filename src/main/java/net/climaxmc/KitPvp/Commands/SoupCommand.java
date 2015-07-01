package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.common.database.CachedPlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SoupCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SoupCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        CachedPlayerData playerData = plugin.getPlayerData(player);

        if (player.getLocation().distance(plugin.getWarpLocation("NoSoup")) <= 100) {
            player.sendMessage(ChatColor.RED + "You are at NoSoup!");
            return true;
        }

        if (playerData.getBalance() < 10) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to get more soup!");
            return true;
        }

        playerData.withdrawBalance(10);

        Inventory soupInventory = Bukkit.createInventory(null, 54, org.bukkit.ChatColor.BOLD + "Free Soup!");
        Kit.addSoup(soupInventory, 0, 53);
        player.openInventory(soupInventory);

        player.sendMessage(ChatColor.GREEN + "You opened a soup inventory for $10!");

        return true;
    }
}
