package net.climaxmc.Donations;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;

import java.util.ArrayList;

public class Donations {
    public Donations(ClimaxPvp plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin), plugin);
    }
}
