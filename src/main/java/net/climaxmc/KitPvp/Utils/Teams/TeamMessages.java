package net.climaxmc.KitPvp.Utils.Teams;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.TextComponentMessages;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TeamMessages {

    private ClimaxPvp plugin;

    public TeamMessages(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    TextComponentMessages tcm = new TextComponentMessages();

    public void sendRequestMessage(Player sender, Player target) {
        sender.playSound(sender.getLocation(), Sound.ITEM_PICKUP, 2F, 0.3F);
        sender.sendMessage(WHITE + "     \u00AB" + GREEN + " Your request to team has been sent to " + YELLOW + target.getName() + WHITE + " \u00BB");

        target.playSound(target.getLocation(), Sound.ITEM_PICKUP, 2F, 0.3F);
        target.sendMessage(" ");
        target.sendMessage(GOLD + "     \u00AB" + WHITE + " ========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
        target.sendMessage(YELLOW + "             " + sender.getName() + AQUA + " has sent you a request to team!");
        target.sendMessage(" ");
        BaseComponent component = tcm.centerTextSpacesLeft();
        component.addExtra(tcm.teamAcceptButton());
        component.addExtra(tcm.centerTextSpacesMiddle());
        component.addExtra(tcm.teamDenyButton());
        target.spigot().sendMessage(component);
        target.sendMessage(GOLD + "     \u00AB" + WHITE + " ========================================" + GOLD + " \u00BB");
        target.sendMessage(" ");
    }

    public void sendAcceptMessage(Player target, Player sender) {
        sender.playSound(sender.getLocation(), Sound.VILLAGER_YES, 1, 1F);
        sender.sendMessage(GREEN + "     \u00AB " + AQUA + target.getName() + YELLOW + " has " + GREEN + "accepted " + YELLOW + "your request to team!" + GREEN + " \u00BB");

        target.playSound(target.getLocation(), Sound.VILLAGER_YES, 1, 1F);
        target.sendMessage(GREEN + "     \u00AB " + YELLOW + "You have " + GREEN + "accepted " + AQUA + sender.getName() + YELLOW + "'s request to team!" + GREEN + " \u00BB");
    }

    public void sendDeclineMessage(Player target, Player sender) {
        sender.playSound(sender.getLocation(), Sound.VILLAGER_NO, 1, 0.75F);
        sender.sendMessage(RED + "     \u00AB " + AQUA + target.getName() + YELLOW + " has " + RED + "declined " + YELLOW + "your request to team!" + RED + " \u00BB");

        target.playSound(target.getLocation(), Sound.VILLAGER_NO, 1, 0.75F);
        target.sendMessage(RED + "     \u00AB " + YELLOW + "You have " + RED + "declined " + AQUA + target.getName() + YELLOW + "'s request to team!" + RED + " \u00BB");
    }
}
