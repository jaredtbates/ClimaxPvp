package net.climaxmc.KitPvp.Utils.Teams;// AUTHOR: gamer_000 (11/22/2015)

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TeamMessages {

    public static void sendRequestMessage(Player sender, Player target) {
        sender.playSound(sender.getLocation(), Sound.ITEM_PICKUP, 2F, 0.3F);
        sender.sendMessage(WHITE + "     \u00AB" + GREEN + " Your request to team has been sent to " + YELLOW + target.getName() + WHITE + " \u00BB");

        target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 2F, 0.3F);
        target.sendMessage(" ");
        target.sendMessage(GOLD + "     \u00AB" + WHITE + " ========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
        target.sendMessage(YELLOW + "             " + sender.getName() + AQUA + " has sent you a request to team!");
        target.sendMessage(" ");
        target.sendMessage(GREEN + "" + BOLD + "                       [ACCEPT]" + RED + BOLD + "     [DECLINE]");
        target.sendMessage(GOLD + "     \u00AB" + WHITE + " ========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
    }

    public static void sendAcceptMessage(Player target, Player sender) {
        sender.playSound(sender.getLocation(), Sound.VILLAGER_YES, 1, 1F);
        sender.sendMessage(GREEN + "     \u00AB " + AQUA + target.getName() + YELLOW + " has " + GREEN + "accepted " + YELLOW + "your request to team!" + GREEN + " \u00BB");

        target.playSound(target.getLocation(), Sound.VILLAGER_YES, 1, 1F);
        target.sendMessage(GREEN + "     \u00AB " + YELLOW + "You have " + GREEN + "accepted " + AQUA + sender.getName() + YELLOW + "'s request to team!" + GREEN + " \u00BB");
    }

    public static void sendDeclineMessage(Player target, Player sender) {
        sender.playSound(sender.getLocation(), Sound.VILLAGER_NO, 1, 0.5F);
        sender.sendMessage(RED + "     \u00AB " + AQUA + target.getName() + YELLOW + " has " + RED + "declined " + YELLOW + "your request to team!" + RED + " \u00BB");

        target.playSound(target.getLocation(), Sound.VILLAGER_NO, 1, 0.5F);
        target.sendMessage(RED + "     \u00AB " + YELLOW + "You have " + RED + "declined " + AQUA + target.getName() + YELLOW + "'s request to team!" + RED + " \u00BB");
    }
}
