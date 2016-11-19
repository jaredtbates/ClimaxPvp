package net.climaxmc.KitPvp.Utils.Settings;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by Joshua on 11/19/2016.
 */
public class SettingsFiles {

    private File file;
    private FileConfiguration config;

    public SettingsFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "settings.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void set(String path, Object object) {
        config.set(path, object);
        Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), () -> {
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setRespawnTrue(Player player) {
        set(player.getUniqueId() + ".instaRespawn", true);
    }

    public void setRespawnFalse(Player player) {
        set(player.getUniqueId() + ".instaRespawn", false);
    }

    public boolean getRespawnValue(Player player) {
        if (config.get(player.getUniqueId() + ".instaRespawn") == null) {
            set(player.getUniqueId() + ".instaRespawn", false);
        }
        return (boolean) config.get(player.getUniqueId() + ".instaRespawn");
    }
}
