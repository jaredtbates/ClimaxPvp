package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ListCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String players = ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Online players (%d): ";

        int amount = 0;

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            PlayerData playerData = plugin.getPlayerData(player);

            if (KitPvp.getVanished().contains(player.getUniqueId())) {
                continue;
            }

            amount++;

            if (playerData.getRank() == Rank.DEFAULT) {
                players += (playerData.getLevelColor() + player.getDisplayName() + ChatColor.RESET + ", ");
            } else {
                players += (playerData.getRank().getColor()
                        + "" + playerData.getRank().getPrefix() + " " + playerData.getLevelColor()
                        + player.getDisplayName() + ChatColor.GRAY + ", " + ChatColor.RESET);
            }
        }

        sender.sendMessage(String.format(players, amount));

        return true;
    }
}
