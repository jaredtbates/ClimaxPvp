package net.climaxmc.KitPvp;

import net.climaxmc.KitPvp.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.KitPvp.Listeners.EntityDamageByEntityListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.FoodLevelChangeListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.InventoryClickListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.InventoryOpenListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.ItemSpawnListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerDeathListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerDropItemListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerInteractListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerJoinListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerPickupItemListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerQuitListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.PlayerRespawnListener;
import net.climaxmc.KitPvp.KitPvp.Listeners.WeatherChangeListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public Economy economy = null;
	
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
		getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
		getServer().getPluginManager().registerEvents(new WeatherChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryOpenListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
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
