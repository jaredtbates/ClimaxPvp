package net.climaxmc.Donations;

import lombok.Getter;
import net.climaxmc.Administration.Utils.VoteReceiver;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.*;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;
import net.climaxmc.common.donations.trails.Trail;

import java.util.*;

public class Donations {
    @Getter
    private Map<UUID, Trail> trailsEnabled = new HashMap<>();
    @Getter
    private VoteReceiver voteReceiver;

    public Donations(ClimaxPvp plugin) {
        // Register Commands
        plugin.getCommand("trails").setExecutor(new TrailsCommand(plugin));
        plugin.getCommand("spectate").setExecutor(new SpectateCommand(plugin));
        plugin.getCommand("nickname").setExecutor(new NicknameCommand(plugin));

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin, this), plugin);

        // Initialize Votifier
        /*try {
            voteReceiver = new VoteReceiver(plugin, "0.0.0.0", 8192);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not enable Votifier!");
        }
        voteReceiver.start();*/

        new DonationsChecker(plugin);
    }
}
