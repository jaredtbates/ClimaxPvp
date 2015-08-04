package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (8/3/2015)

import org.bukkit.ChatColor;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.YELLOW;

public enum Challenges {
    Daily1("Daily Challenge #1", 86400, 20),
    Daily2("Daily Challenge #2", 86400, 75),
    Daily3("Daily Challenge #3", 86400, 150),
    Daily4("Daily Challenge #4", 86400, 200),
    Weekly1("Weekly Challenge #1", 604800, 1000);

    private String name;
    private int cooldown;
    private int killreq;

    private Challenges(String name, int cooldown, int killreq) {
        this.name = name;
        this.cooldown = cooldown;
        this.killreq = killreq;
    }

    public int getCooldownTime() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public int getKillreq() {
        return killreq;
    }
}
