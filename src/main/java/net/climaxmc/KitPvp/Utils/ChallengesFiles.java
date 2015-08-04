package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ChallengesFiles {

    private File file;
    private FileConfiguration config;

    public ChallengesFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "challenges.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void set(String path, Object object) {
        config.set(path, object);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object get(String path) {
        return config.get(path);
    }

    public void setStarted(Player p, Challenge challenge) {
        set(p.getUniqueId() + "." + challenge + ".Started", true);
        set(p.getUniqueId() + "." + challenge + ".Completed", false);
        set(p.getUniqueId() + "." + challenge + ".StartTime", (System.currentTimeMillis() / 1000));
    }

    public void setCompleted(Player p, Challenge challenge) {
        set(p.getUniqueId() + "." + challenge + ".Started", false);
        set(p.getUniqueId() + "." + challenge + ".Cooldown", true);
        set(p.getUniqueId() + "." + challenge + ".Completed", true);
    }

    /**
     * Gets the time when the player started a challenge
     *
     * @param p Player
     * @return Start time
     */
    public int getStartTime(Player p, Challenge challenge) {
        if (get(p.getUniqueId() + "." + challenge + ".StartTime") == null) {
            set(p.getUniqueId() + "." + challenge + ".StartTime", 0);
        }
        return (int) get(p.getUniqueId() + "." + challenge + ".StartTime");
    }

    public int getChallengeKills(Player p, Challenge challenge) {
        if (get(p.getUniqueId() + "." + challenge + ".Kills") == null) {
            set(p.getUniqueId() + "." + challenge + ".Kills", 0);
        }
        return (int) get(p.getUniqueId() + "." + challenge + ".Kills");
    }

    public void addChallengeKill(Player p, Challenge challenge) {
        set(p.getUniqueId() + "." + challenge + ".Kills", getChallengeKills(p, challenge) + 1);
    }

    public boolean challengeIsStarted(Player p, Challenge challenge) {
        if (get(p.getUniqueId() + "." + challenge + ".Started") == null) {
            set(p.getUniqueId() + "." + challenge + ".Started", false);
        }
        return (boolean) get(p.getUniqueId() + "." + challenge + ".Started");
    }

    public boolean challengeIsCompleted(Player p, Challenge challenge) {
        if (get(p.getUniqueId() + "." + challenge + ".Completed") == null) {
            set(p.getUniqueId() + "." + challenge + ".Completed", false);
        }
        return (boolean) get(p.getUniqueId() + "." + challenge + ".Completed");
    }

    public Challenge getChallenge(String name) {
        if(name.contains("Daily")) {
            if (name.contains("#1")) return Challenge.Daily1;
            if (name.contains("#2")) return Challenge.Daily2;
            if (name.contains("#3")) return Challenge.Daily3;
            if (name.contains("#4")) return Challenge.Daily4;
        } else {
            return Challenge.Weekly1;
        }
        return Challenge.Daily1;
    }

}
