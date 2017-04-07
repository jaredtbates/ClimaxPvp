package net.climaxmc.Administration.Commands;

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.donations.trails.ParticleEffect;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public class UnbanCombatCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public UnbanCombatCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length == 0 || args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/unbancombat <player>");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
        CombatLogListeners.logged.remove(offlinePlayer.getUniqueId());
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\u00BB If the player was banned, they are now unbanned."));

        return true;
    }
}
