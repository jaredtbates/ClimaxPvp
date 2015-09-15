package net.climaxmc.Administration.Punishments.Commands;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class UnBanCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public UnBanCommand(ClimaxPvp plugin) {
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

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/unban <player>");
            return true;
        }

        PlayerData targetData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(args[0]));

        if (targetData == null) {
            player.sendMessage(ChatColor.RED + "That player has never joined!");
            return true;
        }

        if (targetData.getPunishments() != null && targetData.getPunishments().size() != 0) {
            Set<Punishment> remove = new HashSet<>();
            targetData.getPunishments().stream()
                    .filter(punishment -> punishment.getExpiration() == -1 || System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration()))
                    .filter(punishment -> punishment.getType().equals(Punishment.PunishType.BAN))
                    .forEach(remove::add);
            if (remove.size() == 0) {
                player.sendMessage(ChatColor.RED + "That player is not banned!");
                return true;
            }
            remove.forEach(targetData::removePunishment);
            plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff).hasRank(Rank.HELPER)).forEach(staff -> staff.sendMessage(ChatColor.RED + player.getName() + " unbanned " + plugin.getServer().getOfflinePlayer(targetData.getUuid()) + "."));
        } else {
            player.sendMessage(ChatColor.RED + "That player is not banned!");
        }

        return true;
    }
}
