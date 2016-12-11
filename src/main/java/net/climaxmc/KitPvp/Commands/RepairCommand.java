package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        if (playerData.getBalance() >= 20) {
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
            /*if (!KitManager.isPlayerInKit(player)) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Why would you need to repair? You're not in a kit!");
                return true;
            }*/
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You repaired your inventory for" + ChatColor.GREEN + "$20!");
            playerData.withdrawBalance(20);
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You do not have enough money to repair your inventory!");
        }

        return true;
    }
}
