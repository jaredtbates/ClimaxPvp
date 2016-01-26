package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.ReportGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public static HashMap<UUID, Integer> cooldown = new HashMap<>();
    public static HashMap<UUID, String> reportBuilders = new HashMap<>();
    public static HashMap<UUID, ArrayList<String>> reportArray = new HashMap<>();

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
            reportBuilders.put(player.getUniqueId(), null);
            reportArray.put(player.getUniqueId(), new ArrayList<>());
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
