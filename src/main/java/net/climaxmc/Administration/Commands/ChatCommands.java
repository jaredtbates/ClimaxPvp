package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class ChatCommands implements CommandExecutor {

    private ClimaxPvp plugin;

    public ChatCommands(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public static HashSet<UUID> cmdspies = new HashSet<>();

    public static boolean chatSilenced = false;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (cmd.getName().equalsIgnoreCase("clearchat")) {
            if (!playerData.hasRank(Rank.MODERATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            } else {
                for (int i = 0; i <= 100; i++) {
                    Bukkit.getServer().broadcastMessage(" ");
                }
                Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The chat has been cleared by " + player.getName() + "!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("lockchat")) {
            if (!playerData.hasRank(Rank.MODERATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            } else {
                if (!chatSilenced) {
                    chatSilenced = true;
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "The chat has been locked by " + player.getName() + "!");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                    }
                } else {
                    chatSilenced = false;
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "All chat has been re-enabled!");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                    }
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("cmdspy")) {
            if (!playerData.hasRank(Rank.HELPER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            } else {
                if (!cmdspies.contains(player.getUniqueId())) {
                    cmdspies.add(player.getUniqueId());
                    player.sendMessage(ChatColor.YELLOW + "Command Spy " + ChatColor.GREEN + " ENABLED");
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                } else {
                    cmdspies.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.YELLOW + "Command Spy " + ChatColor.RED + " DISABLED");
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                }
            }
        }

        return false;
    }

}
