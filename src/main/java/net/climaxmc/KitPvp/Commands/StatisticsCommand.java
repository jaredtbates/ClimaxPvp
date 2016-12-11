package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatisticsCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public StatisticsCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        Player target;
        PlayerData data;

        if (args.length == 0) {
            target = player;
            data = plugin.getPlayerData(player);
        } else if (args.length == 1) {
            target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            data = plugin.getPlayerData(target);
        } else {
            player.sendMessage(ChatColor.RED + "/" + label + " [player]");
            return true;
        }

        player.sendMessage(ChatColor.WHITE + "\u00BB " + "§6" + target.getName() + "'s §7statistics " + ChatColor.WHITE + "\u00AB");
        player.sendMessage("§7Rank: §c" + WordUtils.capitalizeFully(data.getRank().toString()));
        player.sendMessage("§7Balance: §c" + "$" + data.getBalance());
        player.sendMessage("§7Kills: §c" + data.getKills());
        player.sendMessage("§7Deaths: §c" + data.getDeaths());
        player.sendMessage("§7KDR: §c" + getRatio(data));

        return true;
    }

    private double getRatio(PlayerData playerData) {
        double kills = playerData.getKills();
        double deaths = playerData.getDeaths();
        double ratio;
        if ((kills == 0.0D) && (deaths == 0.0D)) {
            ratio = 0.0D;
        } else {
            if ((kills > 0.0D) && (deaths == 0.0D)) {
                ratio = kills;
            } else {
                if ((deaths > 0.0D) && (kills == 0.0D)) {
                    ratio = -deaths;
                } else {
                    ratio = kills / deaths;
                }
            }
        }
        ratio = Math.round(ratio * 100.0D) / 100.0D;
        return ratio;
    }
}
