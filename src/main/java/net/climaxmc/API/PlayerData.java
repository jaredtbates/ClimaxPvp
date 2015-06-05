package net.climaxmc.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.climaxmc.API.Events.PlayerBalanceChangeEvent;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Perk;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class PlayerData {
    private final MySQL mySQL;
    private final OfflinePlayer player;
    private Rank rank;
    private int balance;
    private int kills;
    private int deaths;
    private List<String> perks;
    private String nickname;

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
     * Checks if player has perk
     * @param perk Perk to check
     * @return If player has perk
     */
    public boolean hasPerk(Perk perk) {
        return perks.contains(perk.getDBName().toLowerCase()) || hasRank(Rank.TRUSTED) || player.isOp();
    }

    /**
     * Adds a perk to a player
     * @param perk Perk to add
     */
    public void addPerk(Perk perk) {
        perks.add(perk.getDBName());
        Collections.sort(perks);
        mySQL.updateData("perks", StringUtils.join(perks.toArray(), ",").toLowerCase(), player);
    }

    /**
     * Removes a perk from a player
     * @param perk Perk to remove
     */
    public void removePerk(Perk perk) {
        perks.remove(perk.getDBName());
        Collections.sort(perks);
        mySQL.updateData("perks", StringUtils.join(perks.toArray(), ",").toLowerCase(), player);
    }

    /**
     * Sets a player's nickname
     * @param nickname Nickname to set to
     */
    public void setNickname(String nickname) {
        mySQL.updateData("nickname", this.nickname = nickname, player);
    }

    /**
     * Gets temporary data from a player
     * @param key Key of temporary data to get
     * @return Value of temporary data
     */
    public Object getData(String key) {
        return ClimaxPvp.getInstance().getTemporaryPlayerData(player).get(key);
    }

    /**
     * Gets if player has temporary data
     * @param key Key of temporary data to get
     * @return If player has temporary data
     */
    public boolean hasData(String key) {
        return ClimaxPvp.getInstance().getTemporaryPlayerData(player).containsKey(key);
    }

    /**
     * Adds temporary data to a player
     * @param key Key of temporary data to add
     * @param value Value of temporary data to add
     */
    public void addData(String key, Object value) {
        ClimaxPvp.getInstance().getTemporaryPlayerData(player).put(key, value);
    }

    /**
     * Removes temporary data from a player
     * @param key Key of temporary data to remove
     */
    public void removeData(String key) {
        ClimaxPvp.getInstance().getTemporaryPlayerData(player).remove(key);
    }
}
