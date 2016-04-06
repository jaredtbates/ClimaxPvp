package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Teams.TeamMessages;
import net.climaxmc.KitPvp.Utils.Teams.TeamUtils;
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
        TeamMessages teamMessages = new TeamMessages(plugin);
        if (args.length > 1 || args.length == 0) {
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            player.sendMessage(ChatColor.RED + "Incorrect Usage! Try this: /team <[player]/accept/deny/leave>");
        } else {
            if (args[0].equalsIgnoreCase("accept")) {
                if (plugin.getServer().getOnlinePlayers().size() >= 7) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        teamMessages.sendAcceptMessage(player, requester);
                        TeamUtils.createTeam(player, requester);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    player.sendMessage(ChatColor.RED + "Since there are less than 7 players online, you can't team!");
                }
            } else if (args[0].equalsIgnoreCase("deny")) {
                if (plugin.getServer().getOnlinePlayers().size() >= 7) {
                    if (teamUtils.hasPendingRequest(player)) {
                        Player requester = teamUtils.getRequester(player);
                        teamMessages.sendDeclineMessage(player, requester);
                        TeamUtils.removePendingRequest(player);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "You do not have a pending team request!");
                        return false;
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    player.sendMessage(ChatColor.RED + "Since there are less than 7 players online, you can't team!");
                }
            } else if (args[0].equalsIgnoreCase("leave")) {
                if (!teamUtils.isTeaming(player)) {
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    player.sendMessage(ChatColor.RED + "You are not in a team!");
                } else {
                    if (KitPvp.currentTeams.containsKey(player.getName()) || KitPvp.currentTeams.containsValue(player.getName())) {
                        Player teammate = null;
                        for (String possibleTeammate : KitPvp.currentTeams.keySet()) {
                            if (KitPvp.currentTeams.get(possibleTeammate).equals(player.getName())) {
                                teammate = plugin.getServer().getPlayer(possibleTeammate);
                            }
                        }
                        if (teammate != null) {
                            teammate.playSound(teammate.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
                            teammate.sendMessage(ChatColor.RED + " " + player.getName() + "has left the team. Therefore, the team has been disbanded!");
                            KitPvp.currentTeams.remove(teammate.getName());
                            KitPvp.currentTeams.remove(player.getName());
                        }
                        player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
                        player.sendMessage(ChatColor.RED + "You have left the team. Therefore, the team has been disbanded!");
                        KitPvp.currentTeams.remove(teammate.getName());
                        KitPvp.currentTeams.remove(player.getName());
                    } else if (KitPvp.currentTeams.values().contains(player.getName())) {
                        KitPvp.currentTeams.keySet().stream().filter(key -> KitPvp.currentTeams.get(key).equalsIgnoreCase(player.getName())).forEach(key -> {
                            Player teammate = Bukkit.getServer().getPlayer(KitPvp.currentTeams.get(key));
                            teammate.playSound(teammate.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
                            player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
                            teammate.sendMessage(ChatColor.RED + " " + player.getName() + "has left the team. Therefore, the team has been disbanded!");
                            player.sendMessage(ChatColor.RED + "You have left the team. Therefore, the team has been disbanded!");
                            KitPvp.currentTeams.remove(KitPvp.currentTeams.get(key));
                        });
                    }
                }
            } else {
                if (plugin.getServer().getOnlinePlayers().size() >= 7) {
                    Player target = Bukkit.getServer().getPlayerExact(args[0]);
                    if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.GOLD + " \"" + ChatColor.YELLOW + args[0] + ChatColor.GOLD + "\"" + ChatColor.RED + "is not online or doesn't exist!");
                        return false;
                    } else if (target.getUniqueId().equals(player.getUniqueId())) {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + "Team with yourself? How would that even work...");
                    } else {
                        if (teamUtils.hasPendingRequest(target)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + "That player already has a pending request to team!");
                            return false;
                        } else if (teamUtils.hasPendingRequest(player)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + "You already have a pending request to team with another player!");
                            return false;
                        } else if (teamUtils.isTeaming(target)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + "That player is already on a team!");
                            return false;
                        } else {
                            teamMessages.sendRequestMessage(player, target);
                            TeamUtils.createPendingRequest(target, player);
                            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (teamUtils.hasPendingRequest(target)) {
                                        player.sendMessage(ChatColor.RED + "Your request to team with "
                                                + ChatColor.GOLD + target.getName() + ChatColor.RED + " has expired!");
                                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 2, 0.5F);

                                        target.sendMessage(ChatColor.GOLD + player.getName() + "'s" + ChatColor.RED + " request to team with you has expired!");
                                        target.playSound(target.getLocation(), Sound.NOTE_BASS_GUITAR, 2, 0.5F);

                                        TeamUtils.removePendingRequest(target);
                                    }
                                }
                            }, 600L);
                        }
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    player.sendMessage(ChatColor.RED + "Since there are less than 7 players online, you can't team!");
                }
            }
        }
        return false;
    }
}
