package net.climaxmc.Administration;

import net.climaxmc.Administration.Listeners.AsyncPlayerChatListener;
import net.climaxmc.Administration.Listeners.PlayerCommandPreprocessListener;
import net.climaxmc.ClimaxPvp;

public class Administration {
    public Administration(ClimaxPvp plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(plugin), plugin);
    }
}
