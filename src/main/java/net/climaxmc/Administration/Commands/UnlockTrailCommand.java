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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class UnlockTrailCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public UnlockTrailCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.ADMINISTRATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/unlocktrail <player> <trail name (caps matter)>");
            return true;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);

        if (args.length == 2) {
            SettingsFiles settingsFiles = new SettingsFiles();
            settingsFiles.forceUnlockTrail(target, args[1]);
        } else {
            sender.sendMessage(ChatColor.RED + "/unlocktrail <player> <trail name (caps matter)>");
        }
        return true;
    }
}
