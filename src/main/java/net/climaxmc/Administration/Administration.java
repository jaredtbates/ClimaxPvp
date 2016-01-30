package net.climaxmc.Administration;

import net.climaxmc.Administration.Commands.*;
import net.climaxmc.Administration.Listeners.*;
import net.climaxmc.Administration.Punishments.Commands.*;
import net.climaxmc.Administration.Runnables.ChatAutoBroadcastRunnable;
import net.climaxmc.Administration.Runnables.SpawnAutoBroadcastRunnable;
import net.climaxmc.ClimaxPvp;

public class Administration {
    public Administration(ClimaxPvp plugin) {
        // Register listeners
        plugin.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CombatLogListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SpawnProtectListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VanishCommand(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CheckCommand(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChatFilter(plugin), plugin);

        // Register commands
        plugin.getCommand("admin").setExecutor(new AdminCommand(plugin));
        plugin.getCommand("inventory").setExecutor(new InventoryCommand(plugin));
        plugin.getCommand("vanish").setExecutor(new VanishCommand(plugin));
        plugin.getCommand("check").setExecutor(new CheckCommand(plugin));
        plugin.getCommand("clearchat").setExecutor(new ChatCommands(plugin));
        plugin.getCommand("rank").setExecutor(new RankCommand(plugin));
        plugin.getCommand("ban").setExecutor(new BanCommand(plugin));
        plugin.getCommand("tempban").setExecutor(new TempBanCommand(plugin));
        plugin.getCommand("unban").setExecutor(new UnBanCommand(plugin));
        plugin.getCommand("mute").setExecutor(new MuteCommand(plugin));
        plugin.getCommand("tempmute").setExecutor(new TempMuteCommand(plugin));
        plugin.getCommand("unmute").setExecutor(new UnMuteCommand(plugin));
        plugin.getCommand("kick").setExecutor(new KickCommand(plugin));
        plugin.getCommand("lockchat").setExecutor(new ChatCommands(plugin));
        plugin.getCommand("cmdspy").setExecutor(new ChatCommands(plugin));
        plugin.getCommand("getip").setExecutor(new GetIPCommand(plugin));

        // Start runnables
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new SpawnAutoBroadcastRunnable(plugin), 0, 20 * plugin.getConfig().getInt("SpawnAutoBroadcast.Time"));
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new ChatAutoBroadcastRunnable(plugin), 0, 20 * plugin.getConfig().getInt("ChatAutoBroadcast.Time"));
    }
}
