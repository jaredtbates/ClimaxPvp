package net.climaxmc.KitPvp.Commands.Economy;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public PayCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            sender.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "/pay <player> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        int amount;

        try {
            amount = Math.abs(Integer.parseInt(args[1]));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "/pay <player> <amount>");
            return true;
        }

        PlayerData playerData = plugin.getPlayerData(player);

        if (amount > playerData.getBalance()) {
            player.sendMessage(ChatColor.RED + "You do not have that much money!");
            return true;
        }

        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "What's the point in giving someone $0?");
            return true;
        }

        playerData.withdrawBalance(amount);
        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have sent " + ChatColor.GREEN + target.getName() + " $" + amount + ".");

        PlayerData targetData = plugin.getPlayerData(target);
        targetData.depositBalance(amount);
        target.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have received " + ChatColor.GREEN + "$" + amount + ChatColor.GRAY + " from " + ChatColor.GREEN + player.getName() + ".");

        return true;
    }
}
