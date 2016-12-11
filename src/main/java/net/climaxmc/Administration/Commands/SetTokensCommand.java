package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetTokensCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SetTokensCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.ADMINISTRATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "/settokens <player> <true/false>");
            return true;
        }

        OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);

        if (target != null) {
            if (args.length == 2) {
                SettingsFiles settingsFiles = new SettingsFiles();
                if (args[1].equals("true")) {
                    sender.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have set " + target.getName() + "'s tokens to true.");
                    settingsFiles.setWhitelistTokens(target.getUniqueId(), true);
                } else {
                    sender.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have set " + target.getName() + "'s tokens to false.");
                    settingsFiles.setWhitelistTokens(target.getUniqueId(), false);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "/settokens <player> <true/false>y");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "That player has never joined!");
        }
        return true;
    }
}
