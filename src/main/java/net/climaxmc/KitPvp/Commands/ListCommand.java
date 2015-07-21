package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ListCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String players = "Online players (%d): ";

        int amount = 0;

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            CachedPlayerData playerData = plugin.getPlayerData(player);

            if (VanishCommand.getVanished().contains(player.getUniqueId())) {
                continue;
            }

            amount++;

            if (playerData.getRank() == Rank.DEFAULT) {
                players += (playerData.getLevelColor() + player.getDisplayName() + ChatColor.RESET + ", ");
            } else {
                players += (ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "{" + playerData.getRank().getColor() + "" + ChatColor.BOLD + "" + playerData.getRank().getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "} " + playerData.getLevelColor() + player.getDisplayName() + ChatColor.RESET + ", ");
            }
        }

        sender.sendMessage(String.format(players, amount));

        return true;
    }
}
