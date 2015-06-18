package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.command.*;

import java.io.File;
import java.io.IOException;

public class WarpCommand implements CommandExecutor {
    private ClimaxPvp plugin;
    private File warpFile;


    public WarpCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
        warpFile = new File(plugin.getDataFolder(), "warps.json");

        if (warpFile.exists()) {

        } else {
            try {
                warpFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create warps.json!");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return true;
    }
}
