package net.climaxmc.KitPvp;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Commands.*;
import net.climaxmc.KitPvp.Listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class KitPvp {
    public static Inventory kitSelectorInventory = Bukkit.createInventory(null, 54, "§a§lKit Selector");
    public static Inventory moreKitsInventory = Bukkit.createInventory(null, 54, "§b§lMore Kits");
    public static Inventory soupInventory = Bukkit.createInventory(null, 54, "§5§lFree Soup!");
    public static ArrayList<UUID> inKit = new ArrayList<UUID>();
    public static HashMap<UUID, Integer> killStreak = new HashMap<UUID, Integer>();

    public KitPvp(ClimaxPvp plugin) {
        // Initalize kits
        new KitManager(plugin);

        // Register listeners
        plugin.getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ItemSpawnListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeathListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDropItemListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInteractListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WeatherChangeListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryOpenListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuitListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ScoreboardListener(plugin), plugin);

        // Register commands
        plugin.getCommand("repair").setExecutor(new RepairCommand(plugin));
        plugin.getCommand("spawn").setExecutor(new SpawnCommand(plugin));
        plugin.getCommand("balance").setExecutor(new BalanceCommand(plugin));
        plugin.getCommand("economy").setExecutor(new EconomyCommand(plugin));
        plugin.getCommand("pay").setExecutor(new PayCommand(plugin));
        plugin.getCommand("statistics").setExecutor(new StatisticsCommand(plugin));
    }
}
