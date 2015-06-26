package net.climaxmc.KitPvp.Commands.Messaging;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public MessageCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;



        return true;
    }
}
