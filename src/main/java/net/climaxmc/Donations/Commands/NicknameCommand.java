package net.climaxmc.Donations.Commands;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Perk;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class NicknameCommand implements Perk, CommandExecutor {
    private ClimaxPvp plugin;

    public NicknameCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasPerk(this)) {
            player.sendMessage(ChatColor.RED + "Please donate at https://donate.climaxmc.net for access to this perk!");
            return true;
        }

        if (args.length == 2) {

        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/" + label + " <nickname/off>");
            return true;
        }

        if (args[0].length() >= 32) {
            player.sendMessage(ChatColor.RED + "You cannot have a nickname longer than 32 characters!");
            return true;
        }

        if (args[0].equalsIgnoreCase("off")) {
            player.sendMessage(ChatColor.GREEN + "Your nickname has been disabled!");
            player.setDisplayName(null);
            return true;
        }

        String nickname = ChatColor.translateAlternateColorCodes('&', args[0]);
        player.setDisplayName(nickname);
        playerData.setNickname(nickname);
        player.sendMessage(ChatColor.GREEN + "Your nickname has been set to " + nickname + ChatColor.GREEN + "!");

        return true;
    }

    @Override
    public String getDBName() {
        return "Nickname";
    }
}
