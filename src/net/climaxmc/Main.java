package net.climaxmc;

import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String climax = "§0§l[§cClimax§0§l] §r";
	public Economy economy = null;
	
	public void onEnable() {
		setupEconomy();
		new KitPvp(this);
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
