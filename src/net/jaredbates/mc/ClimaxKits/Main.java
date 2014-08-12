package net.jaredbates.mc.ClimaxKits;

import java.util.ArrayList;

import net.jaredbates.mc.ClimaxKits.Commands.RepairCommand;
import net.jaredbates.mc.ClimaxKits.Listeners.FoodLevelChangeListener;
import net.jaredbates.mc.ClimaxKits.Listeners.InventoryClickListener;
import net.jaredbates.mc.ClimaxKits.Listeners.InventoryOpenListener;
import net.jaredbates.mc.ClimaxKits.Listeners.ItemSpawnListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerDeathListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerDropItemListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerInteractListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerJoinListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerPickupItemListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerQuitListener;
import net.jaredbates.mc.ClimaxKits.Listeners.PlayerRespawnListener;
import net.jaredbates.mc.ClimaxKits.Listeners.WeatherChangeListener;
import net.jaredbates.mc.ClimaxKits.Utils.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public SettingsManager players = SettingsManager.getInstance();
	public Inventory kitSelector = Bukkit.createInventory(null, 9, "§a§lKit Selector");
	public Inventory soup = Bukkit.createInventory(null, 54, "§5§lFree Soup!");
	public ArrayList<String> soldierKit = new ArrayList<String>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new ItemSpawnListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
		getServer().getPluginManager().registerEvents(new WeatherChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryOpenListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new RepairCommand(this), this);
	}
	
	public void onDisable() {
		
	}
}