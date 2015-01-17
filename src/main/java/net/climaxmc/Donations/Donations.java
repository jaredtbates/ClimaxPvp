package net.climaxmc.Donations;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.ParticlesCommand;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;

import java.util.ArrayList;

public class Donations {
    public Donations(ClimaxPvp plugin) {
        plugin.getCommand("particles").setExecutor(new ParticlesCommand(plugin));
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin, this), plugin);
    }
}
