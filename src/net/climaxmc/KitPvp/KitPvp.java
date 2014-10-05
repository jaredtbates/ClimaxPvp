package net.climaxmc.KitPvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.climaxmc.Main;
import net.climaxmc.KitPvp.Listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class KitPvp {
	public static Inventory kitSelector = Bukkit.createInventory(null, 54, "§a§lKit Selector");
	public static Inventory soup = Bukkit.createInventory(null, 54, "§5§lFree Soup!");
	public static ArrayList<UUID> inKit = new ArrayList<UUID>();
	public static HashMap<UUID, Integer> killStreak = new HashMap<UUID, Integer>();

	public KitPvp(Main plugin) {
		new KitManager(plugin);
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
	}
}
