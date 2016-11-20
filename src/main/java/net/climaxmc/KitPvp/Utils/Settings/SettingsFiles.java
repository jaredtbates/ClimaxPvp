package net.climaxmc.KitPvp.Utils.Settings;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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


    public void toggleRespawnValue(Player player) {
        if ((boolean) config.get(player.getUniqueId() + ".instaRespawn") == false) {
            set(player.getUniqueId() + ".instaRespawn", true);
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
            player.sendMessage("§f» §7You have set §eInsta-Respawn §7to: §aTrue");
        } else {
            set(player.getUniqueId() + ".instaRespawn", false);
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
            player.sendMessage("§f» §7You have set §eInsta-Respawn §7to: §cFalse");
        }
    }
    public boolean getRespawnValue(Player player) {
        if (config.get(player.getUniqueId() + ".instaRespawn") == null) {
            set(player.getUniqueId() + ".instaRespawn", false);
        }
        return (boolean) config.get(player.getUniqueId() + ".instaRespawn");
    }

    public void toggleReceiveMsg(Player player) {
        if ((boolean) config.get(player.getUniqueId() + ".receiveMsging") == false) {
            set(player.getUniqueId() + ".receiveMsging", true);
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
            player.sendMessage("§f» §7You have set §eReceiving Msgs §7to: §aTrue");
        } else {
            set(player.getUniqueId() + ".receiveMsging", false);
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
            player.sendMessage("§f» §7You have set §eReceiving Msgs §7to: §cFalse");
        }
    }
    public boolean getReceiveMsgValue(Player player) {
        if (config.get(player.getUniqueId() + ".receiveMsging") == null) {
            set(player.getUniqueId() + ".receiveMsging", true);
        }
        return (boolean) config.get(player.getUniqueId() + ".receiveMsging");
    }
}
