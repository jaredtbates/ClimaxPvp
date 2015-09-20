package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public RulesCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage(plugin.getRules().toArray(new String[plugin.getRules().size()]));

        return true;
    }
}
