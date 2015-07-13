package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
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
        String players = "Online players (" + plugin.getServer().getOnlinePlayers().size() + "): ";

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            players += (plugin.getPlayerData(player).getLevelColor() + player.getDisplayName() + ChatColor.RESET + ", ");
        }

        sender.sendMessage(players);

        return true;
    }
}
