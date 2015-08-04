package net.climaxmc.Administration.Commands;// AUTHOR: gamer_000 (8/3/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommands implements CommandExecutor {

    private ClimaxPvp plugin;

    public ChatCommands(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        CachedPlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.MODERATOR) || player.isOp() || playerData.hasRank(Rank.ADMINISTRATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("clearchat")) {
            if(args.length > 0) {
                player.sendMessage(ChatColor.RED + "Too many arguments. Just use /clearchat");
            } else {
                for(int i = 0; i <= 100; i++) {
                    Bukkit.getServer().broadcastMessage(" ");
                }
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The chat has been cleared.");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                }
            }
        }

        return false;
    }

}
