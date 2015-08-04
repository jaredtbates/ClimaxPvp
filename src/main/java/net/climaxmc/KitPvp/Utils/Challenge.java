package net.climaxmc.KitPvp.Utils;

public enum Challenge {
    Daily1("Daily Challenge #1", 86400, 20),
    Daily2("Daily Challenge #2", 86400, 75),
    Daily3("Daily Challenge #3", 86400, 150),
    Daily4("Daily Challenge #4", 86400, 200),
    Weekly1("Weekly Challenge #1", 604800, 1000);

    private String name;
    private int cooldown;
    private int killRequirement;

    Challenge(String name, int cooldown, int killRequirement) {
        this.name = name;
        this.cooldown = cooldown;
        this.killRequirement = killRequirement;
    }

    public int getCooldownTime() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public int getKillRequirement() {
        return killRequirement;
    }
}
