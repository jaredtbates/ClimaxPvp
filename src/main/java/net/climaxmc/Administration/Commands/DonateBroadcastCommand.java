package net.climaxmc.Administration.Commands;

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

public class DonateBroadcastCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public DonateBroadcastCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.OWNER)) {
                player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }


        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/donatebroadcast <player> <perk>");
            return true;
        }

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + args[0] + ChatColor.WHITE + " has donated for " + ChatColor.GOLD + args[1]);
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
            }
        });
        Player target = ClimaxPvp.getInstance().getServer().getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(net.md_5.bungee.api.ChatColor.RED + "That player is not online!");
            return true;
        }
        Location location = target.getLocation();
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> new ParticleEffect(new ParticleEffect.ParticleData(ParticleEffect.ParticleType.CLOUD, 1, 10, 1)).sendToLocation(location), 1, 1);
        plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);

        return true;
    }
}
