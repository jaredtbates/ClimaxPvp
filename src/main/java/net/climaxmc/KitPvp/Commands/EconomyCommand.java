package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class EconomyCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public EconomyCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.ADMINISTRATOR) && !player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length <= 1 || args.length >= 4) {
            sender.sendMessage(ChatColor.RED + "/" + label + " <give|take|set|reset> <player> <amount>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        PlayerData targetData = plugin.getPlayerData(target);

        int amount = 0;

        if (args[0].equals("reset")) {
            targetData.setBalance(amount);
            sender.sendMessage(ChatColor.GREEN + "You set " + target.getName() + "'s balance to $" + targetData.getBalance() + ".");
            target.sendMessage(ChatColor.GREEN + "Your balance was set to $" + targetData.getBalance() + ".");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "/" + label + " <give|take|set|reset> <player> <amount>");
            return true;
        }

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "/" + label + " <give|take|set|reset> <player> <amount>");
            return true;
        }

        switch (args[0]) {
            case "give":
                targetData.depositBalance(amount);
                sender.sendMessage(ChatColor.GREEN + "$" + amount + " added to " + target.getName() + "'s account. New balance: $" + targetData.getBalance() + ".");
                target.sendMessage(ChatColor.GREEN + "$" + amount + " has been added to your account.");
                break;
            case "take":
                targetData.withdrawBalance(amount);
                sender.sendMessage(ChatColor.GREEN + "$" + amount + " taken from " + target.getName() + "'s account. New balance: $" + targetData.getBalance() + ".");
                target.sendMessage(ChatColor.GREEN + "$" + amount + " has been taken from your account.");
                break;
            case "set":
                targetData.setBalance(amount);
                sender.sendMessage(ChatColor.GREEN + "You set " + target.getName() + "'s balance to $" + targetData.getBalance() + ".");
                target.sendMessage(ChatColor.GREEN + "Your balance was set to $" + targetData.getBalance() + ".");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "/" + label + " <give|take|set|reset> <player> <amount>");
                break;
        }

        return true;
    }
}
