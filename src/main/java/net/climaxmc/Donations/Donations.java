package net.climaxmc.Donations;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.SpectateCommand;
import net.climaxmc.Donations.Commands.TrailsCommand;
import net.climaxmc.Donations.Enums.Trail;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;
import net.climaxmc.Donations.Web.CommandChecker;

import java.util.*;

public class Donations {
    @Getter
    private Map<UUID, Trail> trailsEnabled = new HashMap<>();

    public Donations(ClimaxPvp plugin) {
        // Initialize command checker for Minecraft Market
        if (plugin.getConfig().getString("Donations.APIKey").matches("[0-9a-f]+") && plugin.getConfig().getString("Donations.APIKey").length() == 32) {
            new CommandChecker(plugin).runTaskTimerAsynchronously(plugin, 600L, 1200L);
        } else {
            plugin.getLogger().severe("Donations API key invalid! Disabling Minecraft Market functionality!");
        }

        // Register Commands
        plugin.getCommand("trails").setExecutor(new TrailsCommand(plugin));
        plugin.getCommand("spectate").setExecutor(new SpectateCommand(plugin));

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin, this), plugin);
    }
}
