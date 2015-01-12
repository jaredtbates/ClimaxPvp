package net.climaxmc;

import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.Commands.SpawnCommand;
import net.climaxmc.OneVsOne.OneVsOne;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private static Main instance;
	public String climax = "§0§l[§cClimax§0§l] §r";
	public Economy economy = null;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		setupEconomy();
		new KitPvp(this);
		new OneVsOne(this);
		getCommand("repair").setExecutor(new RepairCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
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
	
	public static Main getPlugin() {
		return instance;
	}
}
