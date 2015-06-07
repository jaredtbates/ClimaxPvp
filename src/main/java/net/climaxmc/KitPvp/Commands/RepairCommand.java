package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public RepairCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);
        if (playerData.getBalance() >= 2) {
            playerData.withdrawBalance(2);
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null) {
                    item.setDurability((short) -100);
                }
            }
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item != null) {
                    item.setDurability((short) -100);
                }
            }
            player.sendMessage(ChatColor.GREEN + "You repaired your inventory for $2!");
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough money to repair your inventory!");
        }

        return true;
    }
}
