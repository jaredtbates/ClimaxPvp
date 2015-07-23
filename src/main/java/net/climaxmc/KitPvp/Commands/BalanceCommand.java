package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
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
            target = player;
        } else if (args.length == 1) {
            target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "/bal [player]");
            return true;
        }


        CachedPlayerData playerData = plugin.getPlayerData(target);
        player.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.RED + "$" + playerData.getBalance());
        player.sendMessage(ChatColor.GREEN + "Levels: " + ChatColor.RED + playerData.getKills());

        return true;
    }
}
