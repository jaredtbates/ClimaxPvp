package net.climaxmc.KitPvp.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public BalanceCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            player.sendMessage("§bBalance: §a$" + playerData.getBalance());
        }
        return true;
    }
}
