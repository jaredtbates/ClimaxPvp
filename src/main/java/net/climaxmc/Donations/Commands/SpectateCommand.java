package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpectateCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                plugin.sendToSpawn(player);
                player.setGameMode(GameMode.SPECTATOR);
                player.setFlySpeed(0.15F);
                player.sendMessage("§aYou are now spectating");
            } else {
                plugin.sendToSpawn(player);
                player.sendMessage("§aYou are no longer spectating");
            }
        }
        return false;
    }
}
