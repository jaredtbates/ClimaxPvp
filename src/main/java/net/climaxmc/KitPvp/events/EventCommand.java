package net.climaxmc.KitPvp.events;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand implements CommandExecutor {

    private ClimaxPvp plugin;
    public EventCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.BETA))) {
            player.sendMessage(ChatColor.RED + "Donate for a rank to host an event @ donate.climaxmc.net!");
            return true;
        }

        EventManager eventManager = plugin.eventManager;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("host")) {
                if (eventManager.isRunning()) {
                    player.sendMessage(ChatUtils.color("&f\u00BB &cAn event is already hosted!"));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                } else {
                    new EventSelectorInv(plugin).openInventory(player);
                }
            } else if (args[0].equalsIgnoreCase("join")) {
                eventManager.addPlayer(player.getUniqueId());
            } else if (args[0].equalsIgnoreCase("leave")) {
                eventManager.removePlayer(player.getUniqueId());
            } else if (args[0].equalsIgnoreCase("spec")) {
                eventManager.addSpectator(player.getUniqueId());
            }
        } else {
            player.sendMessage(ChatUtils.color("&b&lEvent Commands:"));
            player.sendMessage(ChatUtils.color("&c/event host"));
            player.sendMessage(ChatUtils.color("&c/event join"));
            player.sendMessage(ChatUtils.color("&c/event leave"));
            player.sendMessage(ChatUtils.color("&c/event spec"));
        }

        return false;
    }
}
