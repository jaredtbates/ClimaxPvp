package net.climaxmc.KitPvp.Commands;
/* Created by GamerBah on 3/6/2016 */

import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.Administration.Punishments.Time;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.gpedro.integrations.slack.SlackMessage;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class StaffReqCommand implements CommandExecutor {

    private ClimaxPvp plugin;
    private HashMap<UUID, Integer> cooldown = new HashMap<>();

    public StaffReqCommand(ClimaxPvp plugin) {
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

        if (playerData.hasRank(Rank.HELPER)) {
            player.sendMessage(ChatColor.RED + "You need help from a Staff member? You are a Staff member, silly! Use Slack! xD");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/" + label + " <message>");
            return true;
        }

        if (cooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You must wait " + cooldown.get(player.getUniqueId()) + " seconds until you can use this command again!");
            return true;
        }

        String message = StringUtils.join(args, ' ', 0, args.length);

        plugin.getSlackStaffHelp().call(new SlackMessage(">>>*" + player.getName() + ":* _" + message + "_"));

        player.sendMessage(ChatColor.GREEN + "Your message has been sent! A staff member has been notified of your request and should be able to help you shortly!");
        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
        cooldown.put(player.getUniqueId(), 60);
        plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                if (cooldown.containsKey(player.getUniqueId())) {
                    if (cooldown.get(player.getUniqueId()) >= 0) {
                        cooldown.put(player.getUniqueId(), (cooldown.get(player.getUniqueId()) - 1));
                    } else {
                        cooldown.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "You can now use " + ChatColor.YELLOW + "/staffreq " + ChatColor.GREEN + "again!");
                    }
                }
            }
        }, 1L, 20L);

        return false;
    }
}
