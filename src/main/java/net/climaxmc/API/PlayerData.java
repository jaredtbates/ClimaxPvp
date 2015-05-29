package net.climaxmc.API;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.OfflinePlayer;

@AllArgsConstructor
public class PlayerData {
    private final MySQL mySQL;
    @Getter
    private final OfflinePlayer player;
    @Getter
    private int balance;
    @Getter
    private int kills;
    @Getter
    private int deaths;

    /**
     * Sets the player's balance
     * @param amount Amount to set
     */
    public void setBalance(int amount) {
        mySQL.updateData("balance", Integer.toString(balance = amount), player);
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
}
