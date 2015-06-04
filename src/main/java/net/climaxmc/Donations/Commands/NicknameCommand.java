package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Perk;
import org.bukkit.command.*;

public class NicknameCommand implements Perk, CommandExecutor {
    private ClimaxPvp plugin;

    public NicknameCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }

    @Override
    public String getDBName() {
        return "Nickname";
    }
}
