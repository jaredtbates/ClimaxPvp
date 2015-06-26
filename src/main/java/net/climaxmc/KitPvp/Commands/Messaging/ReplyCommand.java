package net.climaxmc.KitPvp.Commands.Messaging;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;

public class ReplyCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public ReplyCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
