package net.climaxmc.KitPvp;

import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.Listeners.FoodLevelChangeListener;
import net.climaxmc.KitPvp.Listeners.InventoryClickListener;
import net.climaxmc.KitPvp.Listeners.InventoryOpenListener;
import net.climaxmc.KitPvp.Listeners.ItemSpawnListener;
import net.climaxmc.KitPvp.Listeners.PlayerDeathListener;
import net.climaxmc.KitPvp.Listeners.PlayerDropItemListener;
import net.climaxmc.KitPvp.Listeners.PlayerInteractListener;
import net.climaxmc.KitPvp.Listeners.PlayerJoinListener;
import net.climaxmc.KitPvp.Listeners.PlayerQuitListener;
import net.climaxmc.KitPvp.Listeners.PlayerRespawnListener;
import net.climaxmc.KitPvp.Listeners.WeatherChangeListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String climax = "§0§l[§cClimax§0§l] §r";
	public Economy economy = null;
	public Inventory kitSelector = getServer().createInventory(null, 9, "§a§lKit Selector");
	public Inventory soup = getServer().createInventory(null, 54, "§5§lFree Soup!");
	
	public void onEnable() {
		setupEconomy();
		new KitManager(this);
		getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new ItemSpawnListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
		getServer().getPluginManager().registerEvents(new WeatherChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryOpenListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getCommand("repair").setExecutor(new RepairCommand(this));
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
