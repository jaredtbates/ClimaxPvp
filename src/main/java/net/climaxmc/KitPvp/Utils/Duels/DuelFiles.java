package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.GappleKit;
import net.climaxmc.KitPvp.Kits.NoDebuffKit;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DuelFiles {

    private File file;
    private FileConfiguration config;

    public DuelFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "duels.yml");
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

    public void setArenaPoint1(Player player, String arenaName) {
        set("arenas." + arenaName + ".point1.world", player.getWorld().getName());
        set("arenas." + arenaName + ".point1.x", player.getLocation().getX() + 0.5);
        set("arenas." + arenaName + ".point1.y", player.getLocation().getY() + 0.5);
        set("arenas." + arenaName + ".point1.z", player.getLocation().getZ() + 0.5);
        set("arenas." + arenaName + ".point1.yaw", player.getLocation().getYaw());
        set("arenas." + arenaName + ".point1.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public void setArenaPoint2(Player player, String arenaName) {
        set("arenas." + arenaName + ".point2.world", player.getWorld().getName());
        set("arenas." + arenaName + ".point2.x", player.getLocation().getX() + 0.5);
        set("arenas." + arenaName + ".point2.y", player.getLocation().getY() + 0.5);
        set("arenas." + arenaName + ".point2.z", player.getLocation().getZ() + 0.5);
        set("arenas." + arenaName + ".point2.yaw", player.getLocation().getYaw());
        set("arenas." + arenaName + ".point2.pitch", player.getLocation().getPitch());
        saveConfig();
    }
    public void teleport(Player player, Player target) {
        int points = 0;
        for (String arenas : config.getConfigurationSection("arenas").getKeys(false)) {
            points++;
        }
        int randomArena = (int)(Math.random() * points + 1);
        if (ClimaxPvp.currentPlayerArena.containsKey(player)) {
            while (ClimaxPvp.currentPlayerArena.get(player) == randomArena) {
                randomArena = (int)(Math.random() * points + 1);
            }
        }
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

        NoDebuffKit noDebuffKit = new NoDebuffKit();
        if (ClimaxPvp.duelsKit.containsKey(target)) {
            if (ClimaxPvp.duelsKit.get(target).equals("NoDebuff")) {
                noDebuffKit.wear(player);
                noDebuffKit.wear(target);
            }
        }
        GappleKit gappleKit = new GappleKit();
        if (ClimaxPvp.duelsKit.containsKey(target)) {
            if (ClimaxPvp.duelsKit.get(target).equals("Gapple")) {
                gappleKit.wear(player);
                gappleKit.wear(target);
            }
        }

        ClimaxPvp.currentPlayerArena.put(player, randomArena);
        ClimaxPvp.inDuel.add(player);
        ClimaxPvp.inDuel.add(target);
        ClimaxPvp.isDueling.put(player, target);
        ClimaxPvp.isDuelingReverse.put(target, player);
        ClimaxPvp.duelRequestReverse.remove(player);
        ClimaxPvp.duelRequest.remove(target);
    }
}