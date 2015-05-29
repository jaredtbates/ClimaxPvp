package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpawnCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final Block block = player.getLocation().getBlock();

            player.sendMessage("§aPlease wait §c3 §aseconds to be teleported to spawn.");

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (player.getLocation().getBlock().equals(block)) {
                    if (KitPvp.inKit.contains(player.getUniqueId())) {
                        KitPvp.inKit.remove(player.getUniqueId());
                    }
                    plugin.respawn(player);
                    player.sendMessage("§aYou have been teleported to spawn!");
                } else {
                    player.sendMessage("§cTeleportation canceled.");
                }
            }, 60);
        }
        return true;
    }
}
