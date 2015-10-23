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

public class ChatCommands implements CommandExecutor {

    private ClimaxPvp plugin;

    public ChatCommands(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public HashSet<Player> cmdspies = new HashSet<>();
    public boolean chatSilenced = false;

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
                if (args.length > 0) {
                    player.sendMessage(ChatColor.RED + "Too many arguments. Just use /clearchat");
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
        }

        if (cmd.getName().equalsIgnoreCase("lockchat")) {
            if (!playerData.hasRank(Rank.MODERATOR) || !playerData.hasRank(Rank.JR_DEVELOPER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            } else {
                if (args.length > 0) {
                    player.sendMessage(ChatColor.RED + "Too many arguments. Just use /lockchat");
                } else {
                    if(chatSilenced == false) {
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
        }

        if (cmd.getName().equalsIgnoreCase("cmdspy")) {
            if (!playerData.hasRank(Rank.HELPER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            } else {
                if (args.length > 0) {
                    player.sendMessage(ChatColor.RED + "Too many arguments. Just use /cmdspy");
                } else {
                    if(!cmdspies.contains(player)) {
                        cmdspies.add(player);
                    } else {
                        cmdspies.remove(player);
                    }
                }
            }
        }

        return false;
    }

}
