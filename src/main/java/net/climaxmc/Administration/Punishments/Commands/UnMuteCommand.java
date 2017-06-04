package net.climaxmc.Administration.Punishments.Commands;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class UnMuteCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public UnMuteCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/unmute <player>");
            return true;
        }

        PlayerData targetData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(args[0]));

        if (targetData == null) {
            player.sendMessage(ChatColor.RED + "That player is not online or doesn't exist!");
            return true;
        }

        if (targetData.getPunishments() != null && targetData.getPunishments().size() != 0) {
            Set<Punishment> remove = new HashSet<>();
            targetData.getPunishments().stream()
                    .filter(punishment -> punishment.getExpiration() == -1 || System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration()))
                    .filter(punishment -> punishment.getType().equals(Punishment.PunishType.MUTE))
                    .forEach(remove::add);
            if (remove.size() == 0) {
                player.sendMessage(ChatColor.RED + "That player is not muted!");
                return true;
            }
            remove.forEach(targetData::removePunishment);
            plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff).hasRank(Rank.TRIAL_MODERATOR)).forEach(staff -> staff.sendMessage(ChatColor.RED + player.getName() + " unmuted " + plugin.getServer().getOfflinePlayer(targetData.getUuid()).getName() + "."));
        } else {
            player.sendMessage(ChatColor.RED + "That player is not muted!");
        }

        return true;
    }
}
