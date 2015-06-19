package net.climaxmc;

import lombok.Getter;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.MySQL;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    @Getter
    private MySQL mySQL = null;
    @Getter
    private String prefix = ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.RED + "Climax" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.RESET;

    // Warp Storage
    @Getter
    private FileConfiguration warpsConfig = null;
    private File warpsConfigFile = null;

    @Override
    public void onEnable() {
        // Initialize Instance
        instance = this;

        // Save Default Configuration
        saveDefaultConfig();

        // Save Default Warps Storage File
        saveDefaultWarpsConfig();

        // Connect to MySQL
        mySQL = new MySQL(
                getConfig().getString("Database.Host"),
                getConfig().getInt("Database.Port"),
                getConfig().getString("Database.Database"),
                getConfig().getString("Database.Username"),
                getConfig().getString("Database.Password")
        );

        // Create temporary player data
        getServer().getOnlinePlayers().forEach(player -> mySQL.getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>()));

        // Load Modules
        new KitPvp(this);
        new Donations(this);
        new Administration(this);
    }

    @Override
    public void onDisable() {
        // Close MySQL Connection
        if (mySQL.getConnection() != null) {
            try {
                mySQL.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get player data from MySQL
     *
     * @param player Player to get data of
     * @return Data of player
     */
    public PlayerData getPlayerData(OfflinePlayer player) {
        return mySQL.getPlayerData(player.getUniqueId());
    }

    /**
     * Respawns a player
     * @param player Player to respawn
     */
    public void respawn(Player player) {
        player.spigot().respawn();
        player.teleport(player.getWorld().getSpawnLocation());
        getServer().getPluginManager().callEvent(new PlayerRespawnEvent(player, player.getWorld().getSpawnLocation(), false));
    }

    /**
     * Gets temporary data of a player (clears on player join)
     * @param player Player to get data of
     * @return Temporary data of player
     */
    public Map<String, Object> getTemporaryPlayerData(OfflinePlayer player) {
        return mySQL.getTemporaryPlayerData().get(player.getUniqueId());
    }

    private void saveDefaultWarpsConfig() {
        if (warpsConfigFile == null) {
            warpsConfigFile = new File(getDataFolder(), "warps.yml");
        }

        if (!warpsConfigFile.exists()) {
            saveResource("warps.yml", false);
        }

        warpsConfig = YamlConfiguration.loadConfiguration(warpsConfigFile);
    }
}
