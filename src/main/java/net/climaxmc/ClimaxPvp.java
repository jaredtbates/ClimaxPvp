package net.climaxmc;

import lombok.Getter;
import net.climaxmc.API.MySQL;
import net.climaxmc.API.PlayerData;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Creative.Creative;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    private MySQL mySQL = null;
    @Getter
    private String prefix = "§0§l[§cClimax§0§l] §r";

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
        new Creative(this);
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
        return mySQL.getPlayerData(player);
    }

    public void respawn(Player player) {
        player.spigot().respawn();
        player.teleport(player.getWorld().getSpawnLocation());
        getServer().getPluginManager().callEvent(new PlayerRespawnEvent(player, player.getWorld().getSpawnLocation(), false));
    }
}
