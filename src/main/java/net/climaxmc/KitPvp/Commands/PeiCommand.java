package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PeiCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public PeiCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        player.getInventory().addItem(new I(Material.PUMPKIN_PIE).name(ChatColor.GOLD + "Pei"));
        player.sendMessage(ChatColor.GREEN + "You have received a Pei!");

        return true;
    }
}
