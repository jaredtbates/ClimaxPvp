package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HologramFile {

    File file;
    FileConfiguration config;

    public HologramFile(ClimaxPvp plugin) {
        file = new File(plugin.getDataFolder(), "holograms.yml");
        if (!file.exists()) {
            plugin.saveResource("holograms.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLocation(String name, Location location) {
        config.set("locations." + name + ".x", location.getX());
        config.set("locations." + name + ".y", location.getY());
        config.set("locations." + name + ".z", location.getZ());
        saveConfig();
    }

    public void removeLocation(String name) {
        config.set("locations." + name + ".x", null);
        config.set("locations." + name + ".y", null);
        config.set("locations." + name + ".z", null);
        saveConfig();
    }

    public Location getLocation(String name) {
        return new Location(Bukkit.getWorld("KitPvp3.0"),
                (double) config.get("locations." + name + ".x"),
                (double) config.get("locations." + name + ".y"),
                (double) config.get("locations." + name + ".z"));
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        if (config.getConfigurationSection("locations") != null) {
            for (String locations : config.getConfigurationSection("locations").getKeys(false)) {
                names.add(locations);
            }
        }
        return names;
    }
}
