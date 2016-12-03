package net.climaxmc.Administration.Commands;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StaffChatCommand implements CommandExecutor {
    private ClimaxPvp plugin;
    @Getter
    private static Set<UUID> toggled = new HashSet<>();

    public StaffChatCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
        } else {
            if (args.length == 0) {
                if (!toggled.contains(player.getUniqueId())) {
                    toggled.add(player.getUniqueId());
                    player.sendMessage(ChatColor.AQUA + "Staff Chat Toggled " + ChatColor.GREEN + "ON");
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1.5F);
                } else {
                    toggled.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.AQUA + "Staff Chat Toggled " + ChatColor.RED + "OFF");
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1.5F);
                }
            } else {
                String message = StringUtils.join(args, ' ', 0, args.length);

                plugin.getServer().getOnlinePlayers().stream().filter(players ->
                        plugin.getPlayerData(players).hasRank(Rank.TRIAL_MODERATOR))
                        .forEach(players -> players.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[STAFF] "
                                + ChatColor.RED + player.getName() + ": " + ChatColor.WHITE + message));
            }
        }

        return false;
    }

}
