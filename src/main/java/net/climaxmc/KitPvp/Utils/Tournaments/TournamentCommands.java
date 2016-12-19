package net.climaxmc.KitPvp.Utils.Tournaments;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TournamentCommands implements CommandExecutor {
    private ClimaxPvp plugin;

    public TournamentCommands(ClimaxPvp plugin) { this.plugin = plugin; }

    public Player player;
    public Player target;

    TournamentFiles tournamentFiles = new TournamentFiles();
    TournamentUtils tournamentUtils = new TournamentUtils(plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tourney Commands:");
            player.sendMessage(ChatColor.GRAY + "/tourney host <prize amt ($)> // Hosts a tournament with your money");
            player.sendMessage(ChatColor.GRAY + "/tourney join // Joins the current tournament");
            player.sendMessage(ChatColor.GRAY + "/tourney leave // Leaves the current tournament");
            player.sendMessage(ChatColor.GRAY + "/tourney spec // Spectates a running tournament");
            return true;
        }

        target = plugin.getServer().getPlayerExact(args[0]);

        if (args[0].contains("host")) {
            if (args.length == 2) {
                if (isInteger(args[1]) && Integer.parseInt(args[1]) >= 300) {
                    tournamentUtils.createTourney(player, Integer.parseInt(args[1]));
                } else {
                    player.sendMessage(ChatColor.RED + "You must enter a prize amount of $300 or more!");
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tourney Commands:");
                player.sendMessage(ChatColor.GRAY + "/tourney host <prize amt ($)> // Hosts a tournament with your money");
                player.sendMessage(ChatColor.GRAY + "/tourney join // Joins the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney leave // Leaves the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney spec // Spectates a running tournament");
            }
        }
        if (args[0].contains("join")) {
            if (args.length == 1) {
                if (!ClimaxPvp.inTourney.contains(player)) {
                    tournamentUtils.joinTourney(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're already in the tourney!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tourney Commands:");
                player.sendMessage(ChatColor.GRAY + "/tourney host <prize amt ($)> // Hosts a tournament with your money");
                player.sendMessage(ChatColor.GRAY + "/tourney join // Joins the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney leave // Leaves the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney spec // Spectates a running tournament");
            }
        }
        if (args[0].contains("leave")) {
            if (args.length == 1) {
                if (ClimaxPvp.inTourney.contains(player)) {
                    ClimaxPvp.inTourney.remove(player);
                    plugin.respawn(player);
                } else if (ClimaxPvp.tourneySpectators.contains(player)) {
                    ClimaxPvp.tourneySpectators.remove(player);
                    plugin.respawn(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're not in the tourney!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tourney Commands:");
                player.sendMessage(ChatColor.GRAY + "/tourney host <prize amt ($)> // Hosts a tournament with your money");
                player.sendMessage(ChatColor.GRAY + "/tourney join // Joins the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney leave // Leaves the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney spec // Spectates a running tournament");
            }
        }
        if (args[0].contains("spec")) {
            if (args.length == 1) {
                if (!ClimaxPvp.tourneySpectators.contains(player)) {
                    tournamentUtils.spectateTourney(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're already spectating the tourney!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tourney Commands:");
                player.sendMessage(ChatColor.GRAY + "/tourney host <prize amt ($)> // Hosts a tournament with your money");
                player.sendMessage(ChatColor.GRAY + "/tourney join // Joins the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney leave // Leaves the current tournament");
                player.sendMessage(ChatColor.GRAY + "/tourney spec // Spectates a running tournament");
            }
        }

        PlayerData playerData = plugin.getPlayerData(player);
        if (playerData.hasRank(Rank.MODERATOR)) {
            if (args[0].contains("admin")) {
                if (args.length == 2) {
                    if (args[1].contains("cancel")) {
                        tournamentUtils.cancelTourney(player);
                    }
                    if (args[1].contains("setwinpoint")) {
                        tournamentFiles.setWinPoint(player);
                    }
                    if (args[1].contains("setdeathpoint")) {
                        tournamentFiles.setDeathPoint(player);
                    }
                    if (args[1].contains("setlobby")) {
                        tournamentFiles.setLobbyPoint(player);
                    }
                } else if (args.length == 3) {
                    if (args[1].contains("setpoint")) {
                        if (isInteger(args[2]) && Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 10) {
                            tournamentFiles.setArenaPoint(player, args[2]);
                        } else {
                            player.sendMessage(ChatColor.RED + "That is not a number between 1 and 10!");
                        }
                    }
                    if (args[1].contains("setduelpoint")) {
                        if (isInteger(args[2]) && Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 2) {
                            tournamentFiles.setDuelPoint(player, args[2]);
                        } else {
                            player.sendMessage(ChatColor.RED + "Number must be between 1 and 2!");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/tourney admin cancel // Cancels the current tourney");
                    player.sendMessage(ChatColor.RED + "/tourney admin setpoint <#> // Set a point in the tourney");
                    player.sendMessage(ChatColor.RED + "/tourney admin setwinpoint // Set point for winner place");
                    player.sendMessage(ChatColor.RED + "/tourney admin setdeathpoint // Set point for the losers");
                    player.sendMessage(ChatColor.RED + "/tourney admin setlobby // Set lobby location");
                    player.sendMessage(ChatColor.RED + "/tourney admin setduelpoint 1 // Set the first point for the match");
                    player.sendMessage(ChatColor.RED + "/tourney admin setduelpoint 2 // Set the second point for the match");
                }
            }
        }
        return false;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}