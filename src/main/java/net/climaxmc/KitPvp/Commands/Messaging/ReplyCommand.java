package net.climaxmc.KitPvp.Commands.Messaging;

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class ReplyCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ReplyCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        for (Punishment punishment : playerData.getPunishments().stream().filter(punishment1 -> punishment1.getType().equals(Punishment.PunishType.MUTE)).collect(Collectors.toSet())) {
            PlayerData punisherData = plugin.getPlayerData(plugin.getServer().getOfflinePlayer(punishment.getPunisherUUID()));
            if (System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration())) {
                player.sendMessage(ChatColor.RED + "You were temporarily muted by " + plugin.getServer().getOfflinePlayer(punisherData.getUuid()).getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "You have " + Time.toString(punishment.getTime() + punishment.getExpiration() - System.currentTimeMillis()) + " left in your mute.\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!");
                return true;
            } else if (punishment.getExpiration() == -1) {
                player.sendMessage(ChatColor.RED + "You were permanently muted by " + plugin.getServer().getOfflinePlayer(punisherData.getUuid()).getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!");
                return true;
            }
        }

        if (!plugin.getMessagers().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You have not messaged anyone!");
            return true;
        }

        Player target = plugin.getServer().getPlayer(plugin.getMessagers().get(player.getUniqueId()));

        if (target == null) {
            player.sendMessage(ChatColor.RED + "The player that you previously messaged is no longer online.");
            return true;
        }

        plugin.getMessagers().put(player.getUniqueId(), target.getUniqueId());
        plugin.getMessagers().put(target.getUniqueId(), player.getUniqueId());

        String message = StringUtils.join(args, ' ', 0, args.length);

        player.sendMessage(ChatColor.DARK_AQUA + "You" + ChatColor.RED + " \u00BB " + ChatColor.AQUA + "" + ChatColor.BOLD + target.getName() + ChatColor.WHITE + ": " + ChatColor.AQUA + message.trim());
        target.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + "" + ChatColor.RED + " \u00BB " + ChatColor.DARK_AQUA + "You" + ChatColor.WHITE + ": " + ChatColor.AQUA + message);

        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 2);
        target.playSound(target.getLocation(), Sound.NOTE_PIANO, 2, 2);

        return true;
    }
}
