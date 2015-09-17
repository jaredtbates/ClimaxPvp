package net.climaxmc.Administration.Punishments.Commands;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempBanCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public TempBanCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.MODERATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + "/tempban <player> <time (d/h/m)> <reason>");
            return true;
        }

        PlayerData targetData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(args[0]));

        if (targetData == null) {
            player.sendMessage(ChatColor.RED + "That player has never joined!");
            return true;
        }

        long time;

        String timeString = args[1];
        char timeChar = Character.toLowerCase(timeString.charAt(timeString.length() - 1));
        Time timeUnit;
        String timeNumeral = timeString.substring(0, timeString.length() - 1);

        try {
            time = Long.parseLong(timeNumeral);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "That is not a valid time!");
            return true;
        }

        timeUnit = Time.fromId(timeChar);

        if (timeUnit == null) {
            player.sendMessage(ChatColor.RED + "Incorrect time unit! Please use one of the following: m, h, d");
            return true;
        }

        time = time * timeUnit.getMilliseconds();

        String reason = "";
        for (int i = 2; i < args.length; i++) {
            reason += args[i] + " ";
        }
        reason = reason.trim();

        final String finalReason = reason;
        final long finalTime = time;
        targetData.addPunishment(new Punishment(targetData.getUuid(), Punishment.PunishType.BAN, System.currentTimeMillis(), time, playerData.getUuid(), reason));
        plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff).hasRank(Rank.HELPER)).forEach(staff -> staff.sendMessage(ChatColor.RED + player.getName() + " temporarily banned " + plugin.getServer().getPlayer(targetData.getUuid()).getName() + " for " + Time.toString(finalTime) + " for " + finalReason + "."));

        Player target = Bukkit.getPlayer(targetData.getUuid());
        if (target != null) {
            target.kickPlayer(ChatColor.RED + "You were temporarily banned by " + player.getName() + " for " + Time.toString(time) + " for " + reason + ".\n"
                    + "Appeal on forum.climaxmc.net if you believe that this is in error!");
        }

        return true;
    }
}