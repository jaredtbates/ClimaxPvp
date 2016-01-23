package net.climaxmc.KitPvp.Commands;// AUTHOR: gamer_000 (10/25/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.DuelsMenu;
import net.climaxmc.KitPvp.Utils.Duels.Duel;
import net.climaxmc.KitPvp.Utils.Duels.DuelsMessages;
import net.climaxmc.KitPvp.Utils.Duels.DuelsUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public DuelCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        DuelsMessages duelsMessages = new DuelsMessages(plugin);

        if (command.getName().equalsIgnoreCase("duel")) {
            if (args.length > 2 || args.length == 0) {
                player.sendMessage(ChatColor.RED + " Incorrect Usage! Try this: /duel [<player>, accept, deny, list]");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    Duel duel = DuelsUtils.getDuel(player);
                    if (duel != null) {
                        Player duelSender = DuelsUtils.getDuelRequester(player);
                        duelsMessages.sendDuelAcceptMessage(player, duelSender);
                        duel.setAccepted(true);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + " You do not have a pending duel request!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (DuelsUtils.hasPendingDuel(player)) {
                        Player duelSender = DuelsUtils.getDuelRequester(player);
                        duelsMessages.sendDuelDeclineMessage(player, duelSender);
                        DuelsUtils.removeDuel(player);
                    } else {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        player.sendMessage(ChatColor.RED + " You do not have a pending duel request!");
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    DuelsMenu duelsMenu = new DuelsMenu(plugin);
                    duelsMenu.openInventory(player);
                } else {
                    Player target = Bukkit.getServer().getPlayerExact(args[0]);
                    if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                        player.sendMessage(ChatColor.GOLD + " \"" + ChatColor.YELLOW + args[0] + ChatColor.GOLD + "\"" + ChatColor.RED + " is not online or doesn't exist!");
                        return false;
                    } else {
                        if (DuelsUtils.hasPendingDuel(target)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + " That player already has a pending duel!");
                            return false;
                        } else if (DuelsUtils.hasPendingDuel(player)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + " You already have a pending duel with another player!");
                            return false;
                        } else if (DuelsUtils.isInDuel(target)) {
                            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                            player.sendMessage(ChatColor.RED + " That player is already in a duel!");
                            return false;
                        } else {
                            duelsMessages.sendDuelRequestMessage(player, target);
                            DuelsUtils.createDuel(target, player);
                        }
                    }
                }
            }
        }
        return false;
    }
}
