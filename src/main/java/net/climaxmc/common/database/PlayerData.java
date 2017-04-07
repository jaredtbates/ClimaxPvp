package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.climaxmc.Administration.Punishments.Punishment;
import net.climaxmc.common.events.PlayerBalanceChangeEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PlayerData {
    private final MySQL mySQL;
    private final UUID uuid;
    private String ip;
    private Rank rank;
    private int balance, kills, deaths;
    private String /*achievements,*/ nickname /*killEffect, killSound, trail*/;
    //private boolean dueling, duelRequests, teamRequests, privateMessaging;
    private double kdr;
    private int topks;
    private List<Punishment> punishments;

    /**
     * Sets the player's IP address
     *
     * @param ip IP address of the player
     */
    public void setIP(String ip) {
        mySQL.updatePlayerData("ip", this.ip = ip, uuid);
    }

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
     * Checks previous top KS and sets a new top ks if greater
     *
     * @param amount Amount to check and set
     */
    public void trySetTopKS(int amount) {
        if (amount > topks) {
            mySQL.updatePlayerData("topks", Integer.toString(topks = amount), uuid);
        }
    }

    public void setKDR() {
        double kdr = (double) kills / (deaths != 0 ? (double) deaths : 1);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double kdr2 = Double.parseDouble(decimalFormat.format(kdr));
        mySQL.updatePlayerData("kdr", decimalFormat.format(this.kdr = kdr2), uuid);
    }

    /**
     * Gets player's level color
     *
     * @return Player's level color
     */
    public String getLevelColor() {
        String color = ChatColor.GRAY + "";
        if (kills >= 100) {
            color += ChatColor.BLUE;
        }
        if (kills >= 300) {
            color += ChatColor.GREEN;
        }
        if (kills >= 500) {
            color += ChatColor.RED;
        }
        if (kills >= 700) {
            color += ChatColor.GOLD + "" + ChatColor.BOLD;
        }
        if (kills >= 1000) {
            color += ChatColor.DARK_PURPLE;
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

    /**
     * Adds a punishment to the player
     *
     * @param punishment Punishment to apply
     */
    public void addPunishment(Punishment punishment) {
        punishments.add(punishment);
        mySQL.executeUpdate(MySQL.CREATE_PUNISHMENT, uuid.toString(), punishment.getType().name(), punishment.getTime(), punishment.getExpiration(), punishment.getPunisherUUID().toString(), punishment.getReason());
    }

    /**
     * Removes a punishment from the player
     *
     * @param punishment Punishment to remove
     */
    public void removePunishment(Punishment punishment) {
        punishments.remove(punishment);
        mySQL.executeUpdate(MySQL.UPDATE_PUNISHMENT_TIME, 0, uuid.toString(), punishment.getType().name(), punishment.getTime());
    }

    /*
    public void addAchievement(Achievement achievement) {
        if (!getAchievements().contains(achievement.toString())) {
            if(getAchievements() != null) {
                setAchievements(getAchievements() + "," + achievement.toString());
            } else {
                setAchievements(achievement.toString());
            }
        }
    }*/

}
