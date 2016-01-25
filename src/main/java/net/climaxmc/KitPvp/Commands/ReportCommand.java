package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.ReportGUI;
import net.climaxmc.common.database.Rank;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public HashMap<UUID, Integer> cooldown = new HashMap<>();
    public static HashMap<UUID, String> reportBuilders = new HashMap<>();

    public ReportCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/report <player>");
            return true;
        }

        if (args.length > 1) {
            player.sendMessage(ChatColor.RED + "/report <player>");
            return true;
        }

        Player reported = plugin.getServer().getPlayerExact(args[0]);

        if (reported == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (!cooldown.containsKey(player.getUniqueId())) {
            ReportGUI reportGUI = new ReportGUI(plugin);
            reportBuilders.put(player.getUniqueId(), " ");
            reportGUI.openInventory(player, reported);
        } else {
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.YELLOW
                    + cooldown.get(player.getUniqueId()) + " seconds " + ChatColor.RED + "before you report another player!");
            return false;
        }
        return false;
    }
}
