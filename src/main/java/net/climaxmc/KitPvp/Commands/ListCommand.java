package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.CachedPlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ListCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String players = "Online players (" + plugin.getServer().getOnlinePlayers().size() + "): ";

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            CachedPlayerData playerData = plugin.getPlayerData(player);
            players += (playerData.getLevelColor() + player.getDisplayName() + ChatColor.RESET + ", ");
        }

        sender.sendMessage(players);

        return true;
    }
}
