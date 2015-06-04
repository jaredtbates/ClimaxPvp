package net.climaxmc.API;

import net.climaxmc.Donations.Perk;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * MySQL Tools
 *
 * @author computerwizjared
 */
public class MySQL {
    private static final String GET_DATA = "SELECT * FROM `player_data` WHERE `uuid` = ?;";
    private static final String CREATE_DATA_TABLE = "CREATE TABLE IF NOT EXISTS `player_data` (`uuid` VARCHAR(36) NOT NULL PRIMARY KEY, `rank` VARCHAR(20) DEFAULT 'DEFAULT' NOT NULL, `balance` INT DEFAULT 0 NOT NULL, `kills` INT DEFAULT 0 NOT NULL, `deaths` INT DEFAULT 0 NOT NULL, `perks` TEXT NOT NULL);";
    private static final String CREATE_PLAYER_DATA = "INSERT IGNORE INTO `player_data` (`uuid`, `rank`, `balance`, `kills`, `deaths`, `perks`) VALUES (?, ?, ?, ?, ?, ?);";
    private final String address;
    private final int port;
    private final String name;
    private final String username;
    private final String password;
    private Connection connection;

    /**
     * Defines a new MySQL connection
     *
     * @param address  Address of the MySQL server
     * @param port     Port of the MySQL server
     * @param name     Name of the database
     * @param username Name of user with rights to the database
     * @param password Password of user with rights to the database
     */
    public MySQL(String address, int port, String name, String username, String password) {
        this.address = address;
        this.port = port;
        this.name = name;
        this.username = username;
        this.password = password;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + name, username, password);
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not create MySQL connection! Stopping server.");
            e.printStackTrace();
            Bukkit.shutdown();
        }

        executeUpdate(CREATE_DATA_TABLE);
    }

    /**
     * Gets the MySQL Connection
     *
     * @return The MySQL Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Executes a MySQL query
     *
     * @param query  The query to run on the database
     * @param values The values to insert into the query
     */
    private synchronized ResultSet executeQuery(String query, Object... values) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + name, username, password);
            }

            PreparedStatement statement = connection.prepareStatement(query);

            int i = 0;
            for (Object value : values) {
                statement.setObject(++i, value);
            }

            return statement.executeQuery();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not execute MySQL query!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executes a MySQL update
     *
     * @param query The query to run on the database
     */
    private synchronized void executeUpdate(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + name, username, password);
            }

            connection.prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not execute MySQL query!");
            e.printStackTrace();
        }
    }

    /**
     * Update Player Data
     *
     * @param column The column to update
     * @param to     What to update the column to
     * @param player The player to update
     */
    public void updateData(String column, String to, OfflinePlayer player) {
        executeUpdate("UPDATE `player_data` SET " + column + "=\"" + to + "\" WHERE uuid='" + player.getUniqueId().toString() + "';");
    }

    /**
     * Get data of a player
     *
     * @param player Player to get data of
     * @return Data of player
     */
    public PlayerData getPlayerData(OfflinePlayer player) {
        ResultSet set = executeQuery(GET_DATA, player.getUniqueId().toString());

        if (set == null) {
            return null;
        }

        try {
            if (set.next()) {
                Rank rank = Rank.valueOf(set.getString("rank"));
                int balance = set.getInt("balance");
                int kills = set.getInt("kills");
                int deaths = set.getInt("deaths");
                List<String> perks = Arrays.asList(set.getString("perks").split(","));
                return new PlayerData(this, player, rank, balance, kills, deaths, perks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create player data
     *
     * @param player Player to create data of
     */
    public void createPlayerData(OfflinePlayer player) {
        try {
            PreparedStatement statement = connection.prepareStatement(CREATE_PLAYER_DATA);

            int i = 0;
            statement.setString(++i, player.getUniqueId().toString());
            statement.setString(++i, Rank.DEFAULT.toString());
            statement.setInt(++i, 0);
            statement.setInt(++i, 0);
            statement.setInt(++i, 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Could not execute MySQL query!");
            e.printStackTrace();
        }
    }
}
