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
    private int kills;
    @Getter
    private int deaths;

    public void setKills(int kills) {
        mySQL.updateData("Kills", Integer.toString(this.kills = kills), player);
    }

    public void setDeaths(int deaths) {
        mySQL.updateData("Deaths", Integer.toString(this.deaths = deaths), player);
    }
}
