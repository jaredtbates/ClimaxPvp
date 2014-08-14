package net.jaredbates.mc.ClimaxKits;

import java.util.ArrayList;
import java.util.HashMap;

import net.jaredbates.mc.ClimaxKits.Commands.RepairCommand;
import net.jaredbates.mc.ClimaxKits.Listeners.EntityDamageByEntityListener;
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
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public SettingsManager players = SettingsManager.getInstance();
	public Inventory kitSelector = Bukkit.createInventory(null, 9, "§a§lKit Selector");
	public Inventory soup = Bukkit.createInventory(null, 54, "§5§lFree Soup!");
	public ArrayList<String> soldierKit = new ArrayList<String>();
	public ArrayList<String> fishermanKit = new ArrayList<String>();
	public ArrayList<String> tag = new ArrayList<String>();
	public HashMap<String, Long> time = new HashMap<String, Long>();
	public Economy economy = null;

	public void onEnable() {
		setupEconomy();
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
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
		getServer().getPluginManager().registerEvents(new RepairCommand(this), this);
	}

	public void onDisable() {
		
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}
}