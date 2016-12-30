package net.climaxmc.KitPvp.Utils.Tag;

import net.climaxmc.ClimaxPvp;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
public class TagFiles {

    private File file;
    private FileConfiguration config;

    public TagFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "tag.yml");
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
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArenaPoint(Player player, String pointNumber) {
        set("arena.points." + pointNumber + ".world", player.getWorld().getName());
        set("arena.points." + pointNumber + ".x", player.getLocation().getX() + 0.5);
        set("arena.points." + pointNumber + ".y", player.getLocation().getY() + 0.5);
        set("arena.points." + pointNumber + ".z", player.getLocation().getZ() + 0.5);
        set("arena.points." + pointNumber + ".yaw", player.getLocation().getYaw());
        set("arena.points." + pointNumber + ".pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getArenaPoint(int pointNumber) {
        Location point = new Location(
                Bukkit.getServer().getWorld((String) config.get("arena.points." + pointNumber + ".world")),
                config.getDouble("arena.points." + pointNumber + ".x"),
                config.getDouble("arena.points." + pointNumber + ".y"),
                config.getDouble("arena.points." + pointNumber + ".z"),
                (float) config.getDouble("arena.points." + pointNumber + ".yaw"),
                (float) config.getDouble("arena.points." + pointNumber + ".pitch")
        );
        return point;
    }

    public void setWinPoint(Player player) {
        set("arena.winPoint.world", player.getWorld().getName());
        set("arena.winPoint.x", player.getLocation().getX() + 0.5);
        set("arena.winPoint.y", player.getLocation().getY() + 0.5);
        set("arena.winPoint.z", player.getLocation().getZ() + 0.5);
        set("arena.winPoint.yaw", player.getLocation().getYaw());
        set("arena.winPoint.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getWinPoint() {
        Location point = new Location(
                Bukkit.getServer().getWorld((String) config.get("arena.winPoint.world")),
                config.getDouble("arena.winPoint.x"),
                config.getDouble("arena.winPoint.y"),
                config.getDouble("arena.winPoint.z"),
                (float) config.getDouble("arena.winPoint.yaw"),
                (float) config.getDouble("arena.winPoint.pitch")
        );
        return point;
    }

    public void setLosePoint(Player player) {
        set("arena.losePoint.world", player.getWorld().getName());
        set("arena.losePoint.x", player.getLocation().getX() + 0.5);
        set("arena.losePoint.y", player.getLocation().getY() + 0.5);
        set("arena.losePoint.z", player.getLocation().getZ() + 0.5);
        set("arena.losePoint.yaw", player.getLocation().getYaw());
        set("arena.losePoint.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getLosePoint() {
        Location point = new Location(
                Bukkit.getServer().getWorld((String) config.get("arena.losePoint.world")),
                config.getDouble("arena.losePoint.x"),
                config.getDouble("arena.losePoint.y"),
                config.getDouble("arena.losePoint.z"),
                (float) config.getDouble("arena.losePoint.yaw"),
                (float) config.getDouble("arena.losePoint.pitch")
        );
        return point;
    }
    public void setLobbyPoint(Player player) {
        set("arena.lobbyPoint.world", player.getWorld().getName());
        set("arena.lobbyPoint.x", player.getLocation().getX() + 0.5);
        set("arena.lobbyPoint.y", player.getLocation().getY() + 0.5);
        set("arena.lobbyPoint.z", player.getLocation().getZ() + 0.5);
        set("arena.lobbyPoint.yaw", player.getLocation().getYaw());
        set("arena.lobbyPoint.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getLobbyPoint() {
        Location point = new Location(
                Bukkit.getServer().getWorld(config.get("arena.lobbyPoint.world").toString()),
                config.getDouble("arena.lobbyPoint.x"),
                config.getDouble("arena.lobbyPoint.y"),
                config.getDouble("arena.lobbyPoint.z"),
                (float) config.getDouble("arena.lobbyPoint.yaw"),
                (float) config.getDouble("arena.lobbyPoint.pitch")
        );
        return point;
    }
}