package net.climaxmc.KitPvp.Commands;// AUTHOR: gamer_000 (11/22/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Teams.TeamUtils;
import net.climaxmc.KitPvp.Utils.Teams.TeamMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public TeamCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        TeamUtils teamUtils = new TeamUtils(plugin);
        if (command.getName().equalsIgnoreCase("team")) {
            if (args.length > 1 || args.length == 0) {
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                player.sendMessage(ChatColor.RED + "Incorrect Usage! Try this: /team <accept/deny/[player]>");
            } else {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        TeamMessages.sendAcceptMessage(player, requester);
                        TeamUtils.createTeam(player, requester);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        TeamMessages.sendDeclineMessage(player, requester);
                        TeamUtils.removePendingRequest(player);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (!teamUtils.isTeaming(player)) {
                        player.sendMessage(ChatColor.RED + "You are not in a team!");
                    } else {
                        //TODO: Leave the team!
                    }
                } else {
                    Player target = Bukkit.getServer().getPlayerExact(args[0]);
                    if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                        player.sendMessage(ChatColor.GOLD + "\"" + ChatColor.YELLOW + args[0] + ChatColor.GOLD + "\"" + ChatColor.RED + " is not online or doesn't exist!");
                        return false;
                    } else if (target == player) {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "Team with yourself? How would that even work...");
                    } else {
                        if (teamUtils.hasPendingRequest(target)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + "The player already has a pending request to team!");
                            return false;
                        } else if (teamUtils.hasPendingRequest(player)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + "You already have a pending request to team with another player!");
                            return false;
                        } else {
                            TeamMessages.sendRequestMessage(player, target);
                            TeamUtils.createPendingRequest(target, player);
                        }
                    }
                }
            }
        }
        return false;
    }
}
