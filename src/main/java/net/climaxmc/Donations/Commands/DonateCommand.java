package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonateCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public DonateCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        player.spigot().sendMessage(
                new ComponentBuilder("Donate at donate.climaxmc.net for ranks and perks!")
                .color(ChatColor.GREEN)
                .bold(true)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://donate.climaxmc.net"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to visit donate.climaxmc.net!").color(ChatColor.AQUA).create()))
                .create()
        );

        return true;
    }
}
