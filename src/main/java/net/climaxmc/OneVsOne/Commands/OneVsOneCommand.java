package net.climaxmc.OneVsOne.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OneVsOneCommand implements CommandExecutor {
    private ClimaxPvp plugin;
    private OneVsOne instance;

    public OneVsOneCommand(ClimaxPvp plugin, OneVsOne instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("1v1")) {
                if (args.length > 0) {
                    if (player.hasPermission("ClimaxPvp.1v1.ArenaCommands")) {
                        if (args[0].equalsIgnoreCase("setlobby")) {
                            plugin.getConfig().set("Lobby.Spawn.X", player.getLocation().getBlockX());
                            plugin.getConfig().set("Lobby.Spawn.Y", player.getLocation().getBlockY() + 0.5);
                            plugin.getConfig().set("Lobby.Spawn.Z", player.getLocation().getBlockZ());
                            plugin.saveConfig();
                            player.sendMessage(ChatColor.GREEN + "1v1 Lobby Spawn Set!");
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena1spawn1")) {
                            addArenaSpawn(player, 1, 1);
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena1spawn2")) {
                            addArenaSpawn(player, 1, 2);
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena2spawn1")) {
                            addArenaSpawn(player, 2, 1);
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena2spawn2")) {
                            addArenaSpawn(player, 2, 2);
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena3spawn1")) {
                            addArenaSpawn(player, 3, 1);
                            return true;
                        } else if (args[0].equalsIgnoreCase("setarena3spawn2")) {
                            addArenaSpawn(player, 3, 2);
                            return true;
                        }
                    }
                }
                int x = plugin.getConfig().getInt("Lobby.Spawn.X");
                int y = plugin.getConfig().getInt("Lobby.Spawn.Y");
                int z = plugin.getConfig().getInt("Lobby.Spawn.Z");
                player.teleport(new Location(player.getWorld(), x, y, z));
                ItemStack stick = new ItemStack(Material.STICK);
                ItemMeta stickMeta = stick.getItemMeta();
                stickMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Regular 1v1");
                stick.setItemMeta(stickMeta);
                player.getInventory().clear();
                player.getInventory().addItem(stick);
                player.sendMessage(plugin.getPrefix() + ChatColor.GRAY + " Teleported to the 1v1 Lobby!");
                return true;
            }
        }
        return false;
    }

    private void addArenaSpawn(Player player, int arena, int spawn) {
        plugin.getConfig().set("Arenas." + arena + ".Spawns." + spawn + ".X", player.getLocation().getBlockX());
        plugin.getConfig().set("Arenas." + arena + ".Spawns." + spawn + ".Y", player.getLocation().getBlockY() + 0.5);
        plugin.getConfig().set("Arenas." + arena + ".Spawns." + spawn + ".Z", player.getLocation().getBlockZ());
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Arena " + arena + " spawn " + spawn + " set!");
    }
}
