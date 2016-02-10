package net.climaxmc.KitPvp.Utils.Challenges;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Challenge {
    Daily1("Daily Challenge #1", 86400, 20, 300),
    Daily2("Daily Challenge #2", 86400, 75, 1200),
    Daily3("Daily Challenge #3", 86400, 150, 2000),
    Daily4("Daily Challenge #4", 86400, 250, 2750),
    Weekly1("Weekly Challenge #1", 604800, 1000, 7500);

    private String name;
    private int cooldown;
    private int killRequirement;
    private int rewardMoney;

}
