package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            player.sendMessage("\u00A7f» \u00A76Available warps: " + ChatColor.GRAY + plugin.getWarpsConfig().getKeys(false).stream().collect(Collectors.joining(", ")));
            return true;
        }

        switch (args[0]) {
            case ("create"):
                if (!(playerData.hasRank(Rank.ADMINISTRATOR) || playerData.getRank().equals(Rank.BUILDER))) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to create warps!");
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
                    player.sendMessage(ChatColor.RED + "You do not have permission to create warps!");
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

                player.sendMessage(ChatColor.GREEN + " Warp " + warpDeleteSection.getName() + " deleted!");
                break;
            default:
                if (CombatLogListeners.getTagged().containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Teleporting in 5 seconds, don't move...");

                    double x = player.getLocation().getBlockX();
                    double y = player.getLocation().getBlockY();
                    double z = player.getLocation().getBlockZ();

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClimaxPvp.getInstance(), new Runnable() {
                        public void run() {
                            if (player.getLocation().getBlockX() == x && player.getLocation().getBlockY() == y && player.getLocation().getBlockZ() == z) {
                                player.sendMessage("\u00A77You have been warped to \u00A76" + args[0]);
                                plugin.warp(args[0], player);
                                if (args[0].equalsIgnoreCase("nosoup")) {
                                    player.setFoodLevel(20);
                                }
                            } else {
                                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You moved! Teleport cancelled.");
                            }
                        }
                    }, 20L * 5);
                } else {
                    player.sendMessage("\u00A77You have been warped to \u00A76" + args[0]);
                    plugin.warp(args[0], player);
                    if (args[0].equalsIgnoreCase("nosoup")) {
                        player.setFoodLevel(20);
                    }
                }
                break;
        }

        return true;
    }
}
