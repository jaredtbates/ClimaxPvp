package net.climaxmc.KitPvp.Commands.Economy;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public BalanceCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        Player target;

        if (args.length == 0) {
            PlayerData playerData = plugin.getPlayerData(player);
            player.sendMessage(ChatColor.WHITE + "\u00BB" + ChatColor.GRAY + "Balance: " + ChatColor.GREEN + "$" + playerData.getBalance());
        } else if (args.length == 1) {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            } else {
                PlayerData targetData = plugin.getPlayerData(target);
                player.sendMessage(ChatColor.WHITE + "\u00BB" + ChatColor.GRAY + "Balance of "
                        + ChatColor.GOLD + target.getName() + ChatColor.GREEN + "$" + targetData.getBalance());
            }
        } else {
            player.sendMessage(ChatColor.RED + "/bal [player]");
            return true;
        }

        return true;
    }
}
