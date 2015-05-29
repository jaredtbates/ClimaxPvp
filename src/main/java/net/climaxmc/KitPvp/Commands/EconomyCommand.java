package net.climaxmc.KitPvp.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class EconomyCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public EconomyCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1 || args.length >= 4) {
            return false;
        }

        Player player = plugin.getServer().getPlayer(args[2]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        PlayerData playerData = plugin.getPlayerData(player);

        if (args[0].equals("reset")) {
            playerData.setBalance(0);
        }

        int amount;

        try {
            amount = Integer.valueOf(args[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (args[0].equals("give")) {
            playerData.depositBalance(amount);
        } else if (args[0].equals("take")) {
            playerData.withdrawBalance(amount);
        } else if (args[0].equals("set")) {
            playerData.setBalance(amount);
        } else {
            return false;
        }

        return true;
    }
}
