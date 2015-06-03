package net.climaxmc.API;

import lombok.*;
import net.climaxmc.API.Events.PlayerBalanceChangeEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class PlayerData {
    private final MySQL mySQL;
    private final OfflinePlayer player;
    private Rank rank;
    private int balance;
    private int kills;
    private int deaths;

    private final HashMap<String, Object> data = new HashMap<>();

    /**
     * Sets the player's rank
     * @param rank Rank of the player
     */
    public void setRank(Rank rank) {
        mySQL.updateData("rank", (this.rank = rank).toString(), player);
    }

    /**
     * Checks if the player has rank
     * @param rank Rank to compare
     * @return Player has rank
     */
    public boolean hasRank(Rank rank) {
        return this.rank.getPermissionLevel() >= rank.getPermissionLevel();
    }

    /**
     * Sets the player's balance
     * @param amount Amount to set
     */
    public void setBalance(int amount) {
        mySQL.updateData("balance", Integer.toString(balance = amount), player);
        Bukkit.getPluginManager().callEvent(new PlayerBalanceChangeEvent(player));
    }

    /**
     * Deposits to player's balance
     * @param amount Amount to deposit
     */
    public void depositBalance(int amount) {
        setBalance(balance + amount);
    }

    /**
     * Withdraws from player's balance
     * @param amount Amount to withdraw
     */
    public void withdrawBalance(int amount) {
        setBalance(balance - amount);
    }

    /**
     * Sets the player's kills
     * @param amount Amount to set
     */
    public void setKills(int amount) {
        mySQL.updateData("kills", Integer.toString(kills = amount), player);
    }

    /**
     * Adds to player's kills
     * @param amount Amount to add
     */
    public void addKills(int amount) {
        setKills(kills + amount);
    }

    /**
     * Removes from player's kills
     * @param amount Amount to remove
     */
    public void removeKills(int amount) {
        setKills(kills - amount);
    }

    /**
     * Sets the player's deaths
     * @param amount Amount to set
     */
    public void setDeaths(int amount) {
        mySQL.updateData("deaths", Integer.toString(deaths = amount), player);
    }

    /**
     * Adds to player's deaths
     * @param amount Amount to add
     */
    public void addDeaths(int amount) {
        setDeaths(deaths + amount);
    }

    /**
     * Removes from player's deaths
     * @param amount Amount to remove
     */
    public void removeDeaths(int amount) {
        setDeaths(deaths - amount);
    }

    /**
     * Gets player's level color
     * @return Player's level color
     */
    public String getLevelColor() {
        if (kills >= 500) {
            return ChatColor.GOLD + "" + ChatColor.BOLD;
        } else if (kills >= 300) {
            return ChatColor.RED + "";
        } else if (kills >= 150) {
            return ChatColor.GREEN + "";
        } else if (kills >= 50) {
            return ChatColor.BLUE + "";
        } else {
            return ChatColor.GRAY + "";
        }
    }

    /**
     * Gets temporary data from a player
     * @param key Key of temporary data to get
     * @return Value of temporary data
     */
    public Object getData(String key) {
        return this.data.get(key);
    }

    /**
     * Gets if player has temporary data
     * @param key Key of temporary data to get
     * @return If player has temporary data
     */
    public boolean hasData(String key) {
        return this.data.containsKey(key);
    }

    /**
     * Adds temporary data to a player
     * @param key Key of temporary data to add
     * @param value Value of temporary data to add
     */
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    /**
     * Removes temporary data from a player
     * @param key Key of temporary data to remove
     */
    public void removeData(String key) {
        this.data.remove(key);
    }
}
