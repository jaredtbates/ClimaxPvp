package net.climaxmc;

import java.sql.Connection;

import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.Commands.SpawnCommand;
import net.climaxmc.Listeners.*;
import net.climaxmc.OneVsOne.OneVsOne;
import net.climaxmc.Utils.Title;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String climax = "§0§l[§cClimax§0§l] §r";
	public Economy economy = null;
	public Connection connection;
	public Title password = new Title("§c§lENTER YOUR PASSWORD", "", 0, 12000, 0);
	
	public void onEnable() {
		saveDefaultConfig();
		connection = Database.openConnection(this);
		setupEconomy();
		new KitPvp(this);
		new OneVsOne(this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getCommand("repair").setExecutor(new RepairCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
	}
	
	public void onDisable() {
		Database.closeConnection(this, connection);
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
