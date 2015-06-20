package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SuicideCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        player.damage(20);

        return true;
    }
}
