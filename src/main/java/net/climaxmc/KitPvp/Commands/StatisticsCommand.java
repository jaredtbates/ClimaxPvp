package net.climaxmc.KitPvp.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class StatisticsCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public StatisticsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        PlayerData data;

        if (args.length == 0) {
            data = plugin.getPlayerData(player);
        } else if (args.length == 1) {
            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            data = plugin.getPlayerData(target);
        } else {
            player.sendMessage(ChatColor.RED + "/" + label + " [player]");
            return true;
        }

        player.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "=======" + ChatColor.AQUA + " " + data.getPlayer().getName() + "'s statistics " + ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "=======");
        player.sendMessage(ChatColor.GREEN + "Rank: " + ChatColor.RED + WordUtils.capitalizeFully(data.getRank().toString()));
        player.sendMessage(ChatColor.GREEN + "Balance: " + ChatColor.RED + "$" + data.getBalance());
        player.sendMessage(ChatColor.GREEN + "Kills: " + ChatColor.RED + data.getKills());
        player.sendMessage(ChatColor.GREEN + "Deaths: " + ChatColor.RED + data.getDeaths());

        return true;
    }
}
