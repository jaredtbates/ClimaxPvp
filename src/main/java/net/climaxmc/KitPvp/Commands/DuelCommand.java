package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Duels.DuelUtils;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Duels.DuelFiles;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class DuelCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public DuelCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public Player player;
    public Player target;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        player = (Player) sender;

        if (args.length == 0 || args.length > 3) {
            player.sendMessage(ChatColor.RED + "/duel <player>");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;
        }

        target = plugin.getServer().getPlayerExact(args[0]);

        if (target == null) {
            if (args[0].contains("accept")) {
                /*DuelUtils duelUtils = new DuelUtils(plugin);
                duelUtils.acceptRequest(player);
                return true;*/
                if (args.length == 1) {
                    player.sendMessage(ChatColor.RED + "/duel accept <player>");
                    return true;
                }
                if (args.length == 2) {
                    target = plugin.getServer().getPlayerExact(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have not received a request from that player!");
                        return true;
                    } else {
                        DuelUtils duelUtils = new DuelUtils(plugin);
                        duelUtils.acceptRequest(target);
                        return true;
                    }
                }
            }
            PlayerData playerData = plugin.getPlayerData(player);
            if (playerData.hasRank(Rank.ADMINISTRATOR)) {
                if (args[0].contains("set")) {
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "/duel set <arena number> <1 or 2> (Must both be numbers)");
                        return true;
                    } else if (args.length == 2) {
                        player.sendMessage(ChatColor.RED + "/duel set <arena number> <1 or 2> (Must both be numbers)");
                        return true;
                    }
                    if (args.length == 3) {
                        String arenaName = args[1];
                        DuelFiles duelFiles = new DuelFiles();
                        if (args[2].equals("1")) {
                            duelFiles.setArenaPoint1(player, arenaName);
                        } else if (args[2].equals("2")) {
                            duelFiles.setArenaPoint2(player, arenaName);
                        } else {
                            player.sendMessage(ChatColor.RED + "The last argument must be either point 1 or 2!");
                        }
                    }
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }

            player.sendMessage(org.bukkit.ChatColor.RED + "That player is not online!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;
        }

        if (ClimaxPvp.hasRequest.containsKey(player)) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have already sent a duel request to " + ChatColor.GOLD + ClimaxPvp.duelRequest.get(player).getDisplayName());
            return true;
        } else {
            if (ClimaxPvp.inDuel.contains(target)) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "That player is already in a duel!");
                return true;
            } else {
                if (target == player) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You cannot duel yourself!");
                } else {
                    DuelUtils duelUtils = new DuelUtils(plugin);
                    duelUtils.openInventory(player);

                    ClimaxPvp.duelRequest.put(player, target);
                    ClimaxPvp.duelRequestReverse.put(target, player);

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            ClimaxPvp.duelRequest.remove(player);
                            ClimaxPvp.duelRequestReverse.remove(target);
                            ClimaxPvp.duelsKit.remove(player);
                        }
                    }, 20L * 15);
                }
            }
        }

        return false;
    }
}
