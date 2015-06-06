package net.climaxmc.Administration;

import net.climaxmc.Administration.Commands.AdminCommand;
import net.climaxmc.Administration.Commands.RankCommand;
import net.climaxmc.Administration.Listeners.*;
import net.climaxmc.ClimaxPvp;

public class Administration {
    public Administration(ClimaxPvp plugin) {
        // Register listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CombatLogListeners(plugin), plugin);

        // Register commands
        plugin.getCommand("admin").setExecutor(new AdminCommand(plugin));
        plugin.getCommand("rank").setExecutor(new RankCommand(plugin));
    }
}
