package net.climaxmc.KitPvp.Commands;

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

public class UseTokenCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public UseTokenCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);

            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "/usetoken <player>");
                return true;
            }

            OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);

            if (target != null) {
                if (args.length == 1) {
                    SettingsFiles settingsFiles = new SettingsFiles();
                    if (settingsFiles.getWhitelistTokens(player.getUniqueId())) {
                        target.setWhitelisted(true);
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have successfully whitelisted " + ChatColor.GOLD + args[0] + "!");
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 1);
                        settingsFiles.setWhitelistTokens(player.getUniqueId(), false);
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have any whitelist tokens!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/usetoken <player>");
                }
            } else {
                player.sendMessage(ChatColor.RED + "That player has never joined!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be used by a player!");
        }
        return true;
    }
}
