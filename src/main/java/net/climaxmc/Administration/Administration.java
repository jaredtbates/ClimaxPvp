package net.climaxmc.Administration;

import net.climaxmc.Administration.Commands.*;
import net.climaxmc.Administration.Listeners.*;
import net.climaxmc.Administration.Runnables.AutoBroadcastRunnable;
import net.climaxmc.ClimaxPvp;

public class Administration {
    public Administration(ClimaxPvp plugin) {
        // Register listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CombatLogListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SpawnProtectListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VanishCommand(plugin), plugin);

        // Register commands
        plugin.getCommand("admin").setExecutor(new AdminCommand(plugin));
        plugin.getCommand("inventory").setExecutor(new InventoryCommand(plugin));
        plugin.getCommand("vanish").setExecutor(new VanishCommand(plugin));

        // Start runnables
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new AutoBroadcastRunnable(plugin), 20 * plugin.getConfig().getInt("AutoBroadcast.Time"), 20 * plugin.getConfig().getInt("AutoBroadcast.Time"));
    }
}
