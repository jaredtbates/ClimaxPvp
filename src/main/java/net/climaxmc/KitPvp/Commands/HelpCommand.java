package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public HelpCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage(plugin.getHelp().toArray(new String[plugin.getHelp().size()]));

        return true;
    }
}
