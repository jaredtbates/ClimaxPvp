package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.climaxmc.common.events.PlayerBalanceChangeEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.*;

@Data
@AllArgsConstructor
public class PlayerData {
    private final MySQL mySQL;
    private final UUID uuid;
    private Rank rank;
    private int balance, kills, deaths;
    private String nickname;

    private long banDate;
    private long banTime;
    private String banReason;
    private UUID banner;
    private long muteDate;
    private long muteTime;
    private String muteReason;
    private UUID muter;

    /**
     * Sets the player's rank
     *
     * @param rank Rank of the player
     */
    public void setRank(Rank rank) {
        mySQL.updatePlayerData("rank", (this.rank = rank).toString(), uuid);
    }

    /**
     * Checks if the player has rank
     *
     * @param rank Rank to compare
     * @return Player has rank
     */
    public boolean hasRank(Rank rank) {
        return this.rank.getPermissionLevel() >= rank.getPermissionLevel();
    }

    /**
     * Sets the player's balance
     *
     * @param amount Amount to set
     */
    public void setBalance(int amount) {
        mySQL.updatePlayerData("balance", Integer.toString(balance = amount), uuid);

        Bukkit.getPluginManager().callEvent(new PlayerBalanceChangeEvent(uuid));
    }

    /**
     * Deposits to player's balance
     *
     * @param amount Amount to deposit
     */
    public void depositBalance(int amount) {
        setBalance(balance + amount);
    }

    /**
     * Withdraws from player's balance
     *
     * @param amount Amount to withdraw
     */
    public void withdrawBalance(int amount) {
        setBalance(balance - amount);
    }

    /**
     * Sets the player's kills
     *
     * @param amount Amount to set
     */
    public void setKills(int amount) {
        mySQL.updatePlayerData("kills", Integer.toString(kills = amount), uuid);
    }

    /**
     * Adds to player's kills
     *
     * @param amount Amount to add
     */
    public void addKills(int amount) {
        setKills(kills + amount);
    }

    /**
     * Removes from player's kills
     *
     * @param amount Amount to remove
     */
    public void removeKills(int amount) {
        setKills(kills - amount);
    }

    /**
     * Sets the player's deaths
     *
     * @param amount Amount to set
     */
    public void setDeaths(int amount) {
        mySQL.updatePlayerData("deaths", Integer.toString(deaths = amount), uuid);
    }

    /**
     * Adds to player's deaths
     *
     * @param amount Amount to add
     */
    public void addDeaths(int amount) {
        setDeaths(deaths + amount);
    }

    /**
     * Removes from player's deaths
     *
     * @param amount Amount to remove
     */
    public void removeDeaths(int amount) {
        setDeaths(deaths - amount);
    }

    /**
     * Gets player's level color
     *
     * @return Player's level color
     */
    public String getLevelColor() {
        String color = ChatColor.GRAY + "";
        if (kills >= 250) {
            color += ChatColor.BLUE;
        }
        if (kills >= 500) {
            color += ChatColor.GREEN;
        }
        if (kills >= 1000) {
            color += ChatColor.RED;
        }
        if (kills >= 1500) {
            color += ChatColor.GOLD + "" + ChatColor.BOLD;
        }

        return color;
    }

    /**
     * Sets a player's nickname
     *
     * @param nickname Nickname to set to
     */
    public void setNickname(String nickname) {
        mySQL.updatePlayerData("nickname", this.nickname = nickname, uuid);
    }

    /**
     * Gets temporary data of a player (clears on player join)
     *
     * @return Temporary data of player
     */
    public Map<String, Object> getTemporaryPlayerData() {
        return mySQL.getTemporaryPlayerData().get(uuid);
    }

    /**
     * Gets temporary data from a player
     *
     * @param key Key of temporary data to get
     * @return Value of temporary data
     */
    public Object getData(String key) {
        return getTemporaryPlayerData().get(key);
    }

    /**
     * Gets if player has temporary data
     *
     * @param key Key of temporary data to get
     * @return If player has temporary data
     */
    public boolean hasData(String key) {
        return getTemporaryPlayerData().containsKey(key);
    }

    /**
     * Adds temporary data to a player
     *
     * @param key   Key of temporary data to add
     * @param value Value of temporary data to add
     */
    public void addData(String key, Object value) {
        getTemporaryPlayerData().put(key, value);
    }

    /**
     * Removes temporary data from a player
     *
     * @param key Key of temporary data to remove
     */
    public void removeData(String key) {
        getTemporaryPlayerData().remove(key);
    }

    /*
            ~~~~~ Punishments ~~~~~
     */

    /**
     * Sets the ban date of a player
     *
     * @param date Date to set to
     */
    public void setBanDate(long date) {
        mySQL.updatePunishments("ban_date", banDate = date, uuid);
    }

    /**
     * Sets the remaining ban time of a player
     *
     * @param time Remaining time to set to
     */
    public void setBanTime(long time) {
        mySQL.updatePunishments("ban_time", banTime = time, uuid);
    }

    /**
     * Sets the ban reason of a player
     *
     * @param reason Reason to set to
     */
    public void setBanReason(String reason) {
        mySQL.updatePunishments("ban_reason", banReason = reason, uuid);
    }

    /**
     * Sets the banner of a player
     *
     * @param bannerUUID UUID of the banner
     */
    public void setBanner(UUID bannerUUID) {
        mySQL.updatePunishments("banner", (banner = bannerUUID).toString(), uuid);
    }

    /**
     * Sets the mute date of a player
     *
     * @param date Date to set to
     */
    public void setMuteDate(long date) {
        mySQL.updatePunishments("mute_date", muteDate = date, uuid);
    }

    /**
     * Sets the remaining mute time of a player
     *
     * @param time Remaining time to set to
     */
    public void setMuteTime(long time) {
        mySQL.updatePunishments("mute_time", muteTime = time, uuid);
    }

    /**
     * Sets the mute reason of a player
     *
     * @param reason Reason to set to
     */
    public void setMuteReason(String reason) {
        mySQL.updatePunishments("mute_reason", muteReason = reason, uuid);
    }

    /**
     * Sets the muter of a player
     *
     * @param muterUUID UUID of the muter
     */
    public void setMuter(UUID muterUUID) {
        mySQL.updatePunishments("muter", (muter = muterUUID).toString(), uuid);
    }
}
