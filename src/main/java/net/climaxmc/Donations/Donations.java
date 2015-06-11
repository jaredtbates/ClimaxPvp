package net.climaxmc.Donations;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.*;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;
import net.climaxmc.common.donations.trails.Trail;

import java.util.*;

public class Donations {
    @Getter
    private Map<UUID, Trail> trailsEnabled = new HashMap<>();

    public Donations(ClimaxPvp plugin) {
        // Register Commands
        plugin.getCommand("trails").setExecutor(new TrailsCommand(plugin));
        plugin.getCommand("spectate").setExecutor(new SpectateCommand(plugin));
        plugin.getCommand("nickname").setExecutor(new NicknameCommand(plugin));

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin, this), plugin);
    }
}
