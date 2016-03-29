package net.climaxmc.Administration.Punishments.Commands;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.gpedro.integrations.slack.SlackMessage;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
            if (args.length != 1) {
                System.out.print(ChatColor.RED + "/unban <player>");
                return true;
            }

            OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
            PlayerData targetData = plugin.getPlayerData(target);

            if (targetData == null) {
                System.out.print(ChatColor.RED + "That player is not online or doesn't exist!");
                return true;
            }

            if (targetData.getPunishments() != null && targetData.getPunishments().size() != 0) {
                Set<Punishment> remove = new HashSet<>();
                targetData.getPunishments().stream()
                        .filter(punishment -> punishment.getExpiration() == -1 || System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration()))
                        .filter(punishment -> punishment.getType().equals(Punishment.PunishType.BAN))
                        .forEach(remove::add);
                if (remove.size() == 0) {
                    System.out.print(ChatColor.RED + "That player is not banned!");
                    return true;
                } else {
                    remove.forEach(targetData::removePunishment);
                    plugin.getServer().getOnlinePlayers().stream().filter(staff ->
                            plugin.getPlayerData(staff).hasRank(Rank.HELPER)).forEach(staff ->
                            staff.sendMessage(ChatColor.RED
                                    + plugin.getServer().getOfflinePlayer(" " + targetData.getUuid()).getName() + " was unbanned by the console."));

                    plugin.getSlackBans().call(new SlackMessage(">>>*" + target.getName() + "* _was unbanned by the console._"));
                }
            } else {
                System.out.print(ChatColor.RED + "That player is not banned!");
            }
        } else {
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

            OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
            PlayerData targetData = plugin.getMySQL().getPlayerData(target.getUniqueId());

            if (targetData == null) {
                player.sendMessage(ChatColor.RED + "That player is not online or doesn't exist!");
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
                } else {
                    remove.forEach(targetData::removePunishment);
                    plugin.getServer().getOnlinePlayers().stream().filter(staff ->
                            plugin.getPlayerData(staff).hasRank(Rank.HELPER)).forEach(staff ->
                            staff.sendMessage(ChatColor.RED + player.getName() + " unbanned "
                                    + plugin.getServer().getOfflinePlayer(targetData.getUuid()).getName() + "."));

                    plugin.getSlackBans().call(new SlackMessage(">>>*" + player.getName() +
                            "* _unbanned_ *" + target.getName() + "*"));
                }
            } else {
                player.sendMessage(ChatColor.RED + "That player is not banned!");
            }
        }
        return true;
    }
}
