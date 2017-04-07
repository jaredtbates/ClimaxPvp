package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
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

        if (!KitPvp.killStreak.containsKey(player.getUniqueId())) {
            KitPvp.killStreak.put(player.getUniqueId(), 0);
        }

        player.sendMessage(ChatColor.WHITE + "\u00BB " + "\u00A76" + target.getName() + "'s \u00A77statistics " + ChatColor.WHITE + "\u00AB");
        player.sendMessage("\u00A77Rank: \u00A7c" + WordUtils.capitalizeFully(data.getRank().toString()));
        player.sendMessage("\u00A77Balance: \u00A7c" + "$" + data.getBalance());
        player.sendMessage("\u00A77Kills: \u00A7c" + data.getKills());
        player.sendMessage("\u00A77Deaths: \u00A7c" + data.getDeaths());
        player.sendMessage("\u00A77KDR: \u00A7c" + data.getKdr());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Current KS: &c" + KitPvp.killStreak.get(player.getUniqueId())));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Top KS: &c") + data.getTopks());

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
