package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpawnCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (CombatLogListeners.getTagged().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Teleporting in 5 seconds, don't move...");

            double x = player.getLocation().getBlockX();
            double y = player.getLocation().getBlockY();
            double z = player.getLocation().getBlockZ();

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClimaxPvp.getInstance(), new Runnable() {
                public void run() {
                    if (player.getLocation().getBlockX() == x && player.getLocation().getBlockY() == y && player.getLocation().getBlockZ() == z) {
                        plugin.respawn(player);
                        plugin.getCurrentWarps().remove(player.getUniqueId());
                        player.sendMessage("\u00A7f» \u00A77You have been teleported to spawn!");
                    } else {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You moved! Teleport cancelled.");
                    }
                }
            }, 20L * 5);
        } else {
            plugin.respawn(player);
            plugin.getCurrentWarps().remove(player.getUniqueId());
            player.sendMessage("\u00A7f» \u00A77You have been teleported to spawn!");
        }

        return true;
    }
}
