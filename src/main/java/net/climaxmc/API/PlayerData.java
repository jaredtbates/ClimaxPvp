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

    public void setBalance(int balance) {
        mySQL.updateData("balance", Integer.toString(this.balance = balance), player);
    }

    public void setKills(int kills) {
        mySQL.updateData("kills", Integer.toString(this.kills = kills), player);
    }

    public void setDeaths(int deaths) {
        mySQL.updateData("deaths", Integer.toString(this.deaths = deaths), player);
    }
}
