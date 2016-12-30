package net.climaxmc.KitPvp.Utils.Tag;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Tag.TagFiles;
import net.climaxmc.KitPvp.Utils.Tag.TagUtils;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommands implements CommandExecutor {
    private ClimaxPvp plugin;

    public TagCommands(ClimaxPvp plugin) { this.plugin = plugin; }

    public Player player;
    public Player target;

    TagFiles tagFiles = new TagFiles();
    TagUtils tagUtils = new TagUtils(plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tag Commands:");
            player.sendMessage(ChatColor.GRAY + "/tag host <prize amt ($)> // Hosts tag with your money");
            player.sendMessage(ChatColor.GRAY + "/tag join // Joins the current tag game");
            player.sendMessage(ChatColor.GRAY + "/tag leave // Leaves the current tag game");
            player.sendMessage(ChatColor.GRAY + "/tag spec // Spectates a running tag game");
            return true;
        }

        target = plugin.getServer().getPlayerExact(args[0]);

        PlayerData playerData = plugin.getPlayerData(player);



        if (args[0].contains("host")) {
            if (!playerData.hasRank(Rank.BETA)) {
                player.sendMessage(ChatColor.RED + "Donate for a rank to host a tag game @ donate.climaxmc.net!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                return true;
            }
            if (args.length == 2) {
                if (isInteger(args[1]) && Integer.parseInt(args[1]) >= 100) {
                    tagUtils.createTag(player, Integer.parseInt(args[1]));
                } else {
                    player.sendMessage(ChatColor.RED + "You must enter a prize amount of $100 or more!");
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tag Commands:");
                player.sendMessage(ChatColor.GRAY + "/tag host <prize amt ($)> // Hosts tag with your money");
                player.sendMessage(ChatColor.GRAY + "/tag join // Joins the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag leave // Leaves the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag spec // Spectates a running tag game");
            }
        }
        if (args[0].contains("join")) {
            if (args.length == 1) {
                if (!ClimaxPvp.inTag.contains(player)) {
                    tagUtils.joinTag(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're already in the tag!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tag Commands:");
                player.sendMessage(ChatColor.GRAY + "/tag host <prize amt ($)> // Hosts tag with your money");
                player.sendMessage(ChatColor.GRAY + "/tag join // Joins the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag leave // Leaves the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag spec // Spectates a running tag game");
            }
        }
        if (args[0].contains("leave")) {
            if (args.length == 1) {
                if (ClimaxPvp.inTag.contains(player)) {
                    ClimaxPvp.inTag.remove(player);
                    tagUtils.setSpawnScoreboard(player);
                    plugin.respawn(player);
                } else if (ClimaxPvp.tagSpectators.contains(player)) {
                    ClimaxPvp.tagSpectators.remove(player);
                    tagUtils.setSpawnScoreboard(player);
                    plugin.respawn(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're not in the tag!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tag Commands:");
                player.sendMessage(ChatColor.GRAY + "/tag host <prize amt ($)> // Hosts tag with your money");
                player.sendMessage(ChatColor.GRAY + "/tag join // Joins the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag leave // Leaves the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag spec // Spectates a running tag game");
            }
        }
        if (args[0].contains("spec")) {
            if (args.length == 1) {
                if (!ClimaxPvp.tagSpectators.contains(player)) {
                    tagUtils.spectateTag(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You're already spectating the tag!");
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Tag Commands:");
                player.sendMessage(ChatColor.GRAY + "/tag host <prize amt ($)> // Hosts tag with your money");
                player.sendMessage(ChatColor.GRAY + "/tag join // Joins the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag leave // Leaves the current tag game");
                player.sendMessage(ChatColor.GRAY + "/tag spec // Spectates a running tag game");
            }
        }

        if (playerData.hasRank(Rank.MODERATOR)) {
            if (args[0].contains("admin")) {
                if (args.length == 2) {
                    if (args[1].contains("cancel")) {
                        tagUtils.cancelTag(player);
                    }
                    if (args[1].contains("setwinpoint")) {
                        tagFiles.setWinPoint(player);
                        player.sendMessage("Successfully set the win point!");
                    }
                    if (args[1].contains("setlosepoint")) {
                        tagFiles.setLosePoint(player);
                        player.sendMessage("Successfully set the death point!");
                    }
                    if (args[1].contains("setlobby")) {
                        tagFiles.setLobbyPoint(player);
                        player.sendMessage("Successfully set the lobby point!");
                    }
                } else if (args.length == 3) {
                    if (args[1].contains("setpoint")) {
                        if (isInteger(args[2]) && Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 10) {
                            tagFiles.setArenaPoint(player, args[2]);
                            player.sendMessage("Successfully set the arena point " + args[2] + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "That is not a number between 1 and 10!");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/tag admin cancel // Cancels the current tag game");
                    player.sendMessage(ChatColor.RED + "/tag admin setpoint <#> // Set an arena point for the tag game");
                    player.sendMessage(ChatColor.RED + "/tag admin setwinpoint // Set point for winner place");
                    player.sendMessage(ChatColor.RED + "/tag admin setlosepoint // Set point for the losers");
                    player.sendMessage(ChatColor.RED + "/tag admin setlobby // Set lobby location");
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