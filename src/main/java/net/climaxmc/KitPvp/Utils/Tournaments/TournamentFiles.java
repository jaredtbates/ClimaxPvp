package net.climaxmc.KitPvp.Utils.Tournaments;

import net.climaxmc.ClimaxPvp;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
public class TournamentFiles {

    private File file;
    private FileConfiguration config;

    public TournamentFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "tournaments.yml");
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
        //Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), () -> {
        config.set(path, object);
        //});
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDuelPoint(Player player, String pointNumber) {
        set("arena.duelPoint." + pointNumber + ".world", player.getWorld().getName());
        set("arena.duelPoint." + pointNumber + ".x", player.getLocation().getX() + 0.5);
        set("arena.duelPoint." + pointNumber + ".y", player.getLocation().getY() + 0.5);
        set("arena.duelPoint." + pointNumber + ".z", player.getLocation().getZ() + 0.5);
        set("arena.duelPoint." + pointNumber + ".yaw", player.getLocation().getYaw());
        set("arena.duelPoint." + pointNumber + ".pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getDuelPoint(int pointNumber) {
        Location point = new Location(
                Bukkit.getServer().getWorld((String) config.get("arena.duelPoint." + pointNumber + ".world")),
                config.getDouble("arena.duelPoint." + pointNumber + ".x"),
                config.getDouble("arena.duelPoint." + pointNumber + ".y"),
                config.getDouble("arena.duelPoint." + pointNumber + ".z"),
                (float) config.getDouble("arena.duelPoint." + pointNumber + ".yaw"),
                (float) config.getDouble("arena.duelPoint." + pointNumber + ".pitch")
        );
        return point;
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

    public void setDeathPoint(Player player) {
        set("arena.deathPoint.world", player.getWorld().getName());
        set("arena.deathPoint.x", player.getLocation().getX() + 0.5);
        set("arena.deathPoint.y", player.getLocation().getY() + 0.5);
        set("arena.deathPoint.z", player.getLocation().getZ() + 0.5);
        set("arena.deathPoint.yaw", player.getLocation().getYaw());
        set("arena.deathPoint.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public Location getDeathPoint() {
        Location point = new Location(
                Bukkit.getServer().getWorld((String) config.get("arena.deathPoint.world")),
                config.getDouble("arena.deathPoint.x"),
                config.getDouble("arena.deathPoint.y"),
                config.getDouble("arena.deathPoint.z"),
                (float) config.getDouble("arena.deathPoint.yaw"),
                (float) config.getDouble("arena.deathPoint.pitch")
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
                Bukkit.getServer().getWorld((String) config.get("arena.lobbyPoint.world")),
                config.getDouble("arena.lobbyPoint.x"),
                config.getDouble("arena.lobbyPoint.y"),
                config.getDouble("arena.lobbyPoint.z"),
                (float) config.getDouble("arena.lobbyPoint.yaw"),
                (float) config.getDouble("arena.lobbyPoint.pitch")
        );
        return point;
    }

    /*public void teleport(Player player, Player target) {
        int numberOfArenas = 0;
        for (String arenas : config.getConfigurationSection("arenas").getKeys(false)) {
            numberOfArenas++;
        }
        int randomArena = (int)(Math.random() * numberOfArenas + 1);
        Location point1 = new Location(player.getWorld(),
                config.getDouble("arenas." + randomArena + ".point1.x"),
                config.getDouble("arenas." + randomArena + ".point1.y"),
                config.getDouble("arenas." + randomArena + ".point1.z"),
                (float) config.getDouble("arenas." + randomArena + ".point1.yaw"),
                (float) config.getDouble("arenas." + randomArena + ".point1.pitch"));
        Location point2 = new Location(player.getWorld(),
                config.getDouble("arenas." + randomArena + ".point2.x"),
                config.getDouble("arenas." + randomArena + ".point2.y"),
                config.getDouble("arenas." + randomArena + ".point2.z"),
                (float) config.getDouble("arenas." + randomArena + ".point2.yaw"),
                (float) config.getDouble("arenas." + randomArena + ".point2.pitch"));
        player.teleport(point1);
        target.teleport(point2);

        player.setFoodLevel(20);
        player.setFireTicks(0);
        player.setHealth(20);
        target.setFoodLevel(20);
        target.setFireTicks(0);
        target.setHealth(20);

        ClimaxPvp.currentArenas.add(randomArena);
        ClimaxPvp.inDuel.add(player);
        ClimaxPvp.inDuel.add(target);
        ClimaxPvp.isDueling.put(player, target);
        ClimaxPvp.isDuelingReverse.put(target, player);
        ClimaxPvp.duelRequestReverse.remove(player);
        ClimaxPvp.initialRequest.remove(target);
    }*/
}