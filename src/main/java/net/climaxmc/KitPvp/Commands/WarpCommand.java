package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class WarpCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public WarpCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (args.length == 0) {
            player.sendMessage(ChatColor.GREEN + "Available currentWarps: " + ChatColor.AQUA + plugin.getWarpsConfig().getKeys(false).stream().collect(Collectors.joining(", ")));
            return true;
        }

        switch (args[0]) {
            case ("create"):
                if (!(playerData.hasRank(Rank.ADMINISTRATOR) || playerData.getRank().equals(Rank.BUILDER))) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to create currentWarps!");
                    return true;
                }

                if (args.length != 2) {
                    player.sendMessage(ChatColor.RED + "/warp create <name>");
                    return true;
                }

                Location newWarpLocation = player.getLocation();

                ConfigurationSection newWarpSection = plugin.getWarpsConfig().createSection(args[1]);
                newWarpSection.set("X", newWarpLocation.getX());
                newWarpSection.set("Y", newWarpLocation.getY());
                newWarpSection.set("Z", newWarpLocation.getZ());
                newWarpSection.set("Yaw", newWarpLocation.getYaw());
                newWarpSection.set("Pitch", newWarpLocation.getPitch());
                newWarpSection.set("World", newWarpLocation.getWorld().getName());
                plugin.saveWarpsConfig();

                player.sendMessage(ChatColor.GREEN + "Warp " + newWarpSection.getName() + " created!");
                break;
            case ("delete"):
                if (!(playerData.hasRank(Rank.ADMINISTRATOR) || playerData.getRank().equals(Rank.BUILDER))) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to create currentWarps!");
                    return true;
                }

                if (args.length != 2) {
                    player.sendMessage(ChatColor.RED + "/warp delete <name>");
                    return true;
                }

                ConfigurationSection warpDeleteSection;

                try {
                    warpDeleteSection = plugin.getWarpsConfig().getConfigurationSection(plugin.getWarpsConfig().getKeys(false).stream().filter(key -> key.equalsIgnoreCase(args[1])).findFirst().get());
                } catch (NoSuchElementException e) {
                    player.sendMessage(ChatColor.RED + "That warp does not exist!");
                    return true;
                }

                plugin.getWarpsConfig().set(warpDeleteSection.getName(), null);
                plugin.saveWarpsConfig();

                player.sendMessage(ChatColor.GREEN + "Warp " + warpDeleteSection.getName() + " deleted!");
                break;
            default:
                plugin.warp(args[0], player);
                player.sendMessage(ChatColor.GREEN + "You have been warped to " + args[0] + ".");
                break;
        }

        return true;
    }
}
