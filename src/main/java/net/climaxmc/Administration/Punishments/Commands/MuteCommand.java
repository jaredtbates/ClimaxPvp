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

public class MuteCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public MuteCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (command.getName().equalsIgnoreCase("mute")) {
            if (!playerData.hasRank(Rank.HELPER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "/mute <player> <reason>");
                return true;
            }

            PlayerData targetData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(args[0]));

            if (targetData == null) {
                player.sendMessage(ChatColor.RED + "That player hasn't ever joined!");
                return true;
            }

            String reason = "";
            for (int i = 1; i < args.length; i++) {
                reason += args[i] + " ";
            }
            reason = reason.trim();

            final String finalReason = reason;
            targetData.addPunishment(new Punishment(targetData.getUuid(), Punishment.PunishType.MUTE, System.currentTimeMillis(), -1, playerData.getUuid(), reason));
            plugin.getServer().getOnlinePlayers().stream().filter(staff ->
                    plugin.getPlayerData(staff).hasRank(Rank.HELPER)).forEach(staff ->
                    staff.sendMessage("" + ChatColor.RED + player.getName() + " permanently muted "
                            + ChatColor.GRAY + plugin.getServer().getPlayer(targetData.getUuid()).getName() + ChatColor.RED + " for " + finalReason));

            Player target = Bukkit.getPlayer(targetData.getUuid());
            if (target != null) {
                target.sendMessage(ChatColor.RED + "You were permanently muted by " + player.getName() + " for " + reason + "\n"
                        + "Appeal on climaxmc.net/forum if you believe that this is an error!");
            }
        }

        return true;
    }
}