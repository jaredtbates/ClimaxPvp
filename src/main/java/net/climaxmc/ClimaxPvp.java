package net.climaxmc;

import lombok.Getter;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import net.climaxmc.database.MySQL;
import net.climaxmc.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    @Getter
    private MySQL mySQL = null;
    @Getter
    private String prefix = ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.RED + "Climax" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.RESET;
    @Getter
    private Map<UUID, Map<String, Object>> temporaryPlayerData = new HashMap<>();

    public void onEnable() {
        // Initialize Instance
        instance = this;

        // Save Configuration
        saveDefaultConfig();

        // Connect to MySQL
        mySQL = new MySQL(
                getConfig().getString("Database.Host"),
                getConfig().getInt("Database.Port"),
                getConfig().getString("Database.Database"),
                getConfig().getString("Database.Username"),
                getConfig().getString("Database.Password")
        );

        // Load Modules
        new KitPvp(this);
        new OneVsOne(this);
        new Donations(this);
        new Administration(this);

        for (Player player : getServer().getOnlinePlayers()) {
            temporaryPlayerData.put(player.getUniqueId(), new HashMap<>());
        }
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
        return temporaryPlayerData.get(player.getUniqueId());
    }
}
