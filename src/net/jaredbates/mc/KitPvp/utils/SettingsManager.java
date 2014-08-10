package net.jaredbates.mc.KitPvp.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import net.jaredbates.mc.KitPvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {
	Main plugin;
	
	private SettingsManager() {
		
	}

	static SettingsManager instance = new SettingsManager();

	public static SettingsManager getInstance() {
		return instance;
	}

	FileConfiguration data;
	File dfile;
	public FileConfiguration getPlayerFile(UUID uuid){
		File playersDir = new File(plugin.getDataFolder() + File.separator + "players");
		if(!playersDir.exists()){
			playersDir.mkdir();
		}
		dfile = new File(plugin.getDataFolder() + File.separator + "players", uuid + ".yml");

		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			}
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create " + uuid + ".yml!");
				e.printStackTrace();
			}
		}

		data = YamlConfiguration.loadConfiguration(dfile);
		return data;
	}


	public void createFile(UUID uuid) {
		dfile = new File(plugin.getDataFolder() + File.separator + "players", uuid + ".yml");
		if (!dfile.exists()) {
			try {
				dfile.createNewFile();
			}
			catch (IOException e) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create " + uuid + ".yml!");
				e.printStackTrace();
			}
		}
	}

	public void savePlayerFile(UUID uuid) {
		dfile = new File(plugin.getDataFolder() + File.separator + "players", uuid + ".yml");
		try {
			data.save(dfile);
		} catch(IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not SAVE " + uuid + ".yml!");
			e.printStackTrace();
		}
	}
}
