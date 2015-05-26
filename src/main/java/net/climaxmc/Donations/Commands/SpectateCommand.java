package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.GameMode;
import org.bukkit.command.*;
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
                player.spigot().respawn();
                player.setGameMode(GameMode.SPECTATOR);
                player.setFlySpeed(0.15F);
                player.sendMessage("§aYou are now spectating");
            } else {
                player.spigot().respawn();
                player.sendMessage("§aYou are no longer spectating");
            }
        }
        return false;
    }
}
