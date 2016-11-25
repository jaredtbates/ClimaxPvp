package net.climaxmc.Administration.Punishments.Commands;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempMuteCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public TempMuteCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (command.getName().equalsIgnoreCase("tempmute")) {
            if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }

            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "/tempmute <player> <time> <reason>");
                return true;
            }

            PlayerData targetData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(args[0]));
            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(targetData.getUuid());
            Player target = Bukkit.getPlayer(targetData.getUuid());

            if (targetData == null) {
                player.sendMessage(ChatColor.RED + "That player hasn't ever joined!");
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
                player.sendMessage(ChatColor.RED + "That is not a valid time unit! Here's some examples of time labels: 2m, 5d, 6h");
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
            targetData.addPunishment(new Punishment(targetData.getUuid(), Punishment.PunishType.MUTE, System.currentTimeMillis(), time, playerData.getUuid(), reason));

            if (target != null) {
                plugin.getServer().getOnlinePlayers().stream().filter(staff ->
                        plugin.getPlayerData(staff).hasRank(Rank.TRIAL_MODERATOR)).forEach(staff ->
                        staff.sendMessage("" + ChatColor.RED + player.getName() + " temporarily muted "
                                + ChatColor.GRAY + plugin.getServer().getPlayer(targetData.getUuid()).getName() + ChatColor.RED
                                + " for " + Time.toString(finalTime) + " for " + finalReason));
                target.sendMessage(ChatColor.RED + "You were temporarily muted by " + player.getName() + " for " + Time.toString(time) + " for " + reason + "\n"
                        + "Appeal on climaxmc.net/forum if you believe that this is an error!");
            } else {
                plugin.getServer().getOnlinePlayers().stream().filter(staff ->
                        plugin.getPlayerData(staff).hasRank(Rank.TRIAL_MODERATOR)).forEach(staff ->
                        staff.sendMessage("" + ChatColor.RED + player.getName() + " temporarily muted "
                                + ChatColor.GRAY + offlinePlayer.getName() + ChatColor.RED
                                + " for " + Time.toString(finalTime) + " for " + finalReason));
                player.sendMessage(ChatColor.GREEN + "Offline player " + ChatColor.GOLD + offlinePlayer.getName()
                        + ChatColor.GREEN + " successfully temp-muted.");
            }
        }

        return true;
    }
}