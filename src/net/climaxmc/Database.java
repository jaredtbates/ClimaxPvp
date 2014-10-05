package net.climaxmc;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Database {
	public static synchronized Connection openConnection(JavaPlugin plugin) {
		Connection connection;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"
					+ plugin.getConfig().getString("MySQL.Hostname") + ":"
					+ plugin.getConfig().getString("MySQL.Port") + "/"
					+ plugin.getConfig().getString("MySQL.Database"),
					plugin.getConfig().getString("MySQL.Username"),
					plugin.getConfig().getString("MySQL.Password"));
			System.out.println("[" + plugin.getDescription().getName() + "] Successfully initialized MySQL!");
		} catch (Exception e) {
			System.out.println("[" + plugin.getDescription().getName() + "] Could not initialize MySQL!");
			return null;
		}
		
		try {
			Statement statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS " + plugin.getConfig().getString("MySQL.Table") + " (player TEXT not NULL, ip INTEGER(1) not NULL);";
			statement.executeUpdate(sql);
			System.out.println("[" + plugin.getDescription().getName() + "] Successfully initialized Tables!");
		} catch (SQLException e) {
			System.out.println("[" + plugin.getDescription().getName() + "] Could not initialize Tables!");
			return null;
		}
		
		return connection;
	}
	
	public static synchronized void closeConnection(JavaPlugin plugin, Connection connection) {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("[" + plugin.getDescription().getName() + "] Could not deinitialize MySQL!");
		}
	}
	
	public static synchronized boolean addIP(JavaPlugin plugin, Connection connection, Player player, InetSocketAddress ip) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO " + plugin.getConfig().getString("MySQL.Table") + " (`player`, `ip`) VALUES ('" + player.getUniqueId().toString() + "', " + ip.getAddress().toString() + ");");
			return true;
		} catch (Exception e) {
			System.out.println("[" + plugin.getDescription().getName() + "] MySQL could not be contacted!");
			return false;
		}
	}
	
	public static synchronized boolean removeIP(JavaPlugin plugin, Connection connection, Player player) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM " + plugin.getConfig().getString("MySQL.Table") + " WHERE" + player.getUniqueId().toString() + ";");
			return true;
		} catch (Exception e) {
			System.out.println("[" + plugin.getDescription().getName() + "] MySQL could not be contacted!");
			return false;
		}
	}

	public static synchronized boolean containsPlayer(JavaPlugin plugin, Connection connection, Player player) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT player FROM " + plugin.getConfig().getString("MySQL.Table") + " WHERE player=?;");
			statement.setString(1, player.getUniqueId().toString());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("[" + plugin.getDescription().getName() + "] MySQL could not be contacted!");
			e.printStackTrace();
			return false;
		}
	}
}
