package net.climaxmc.Donations.Enums;

import lombok.Getter;
import net.climaxmc.Donations.Perk;

public enum Trail implements Perk {
    CLOUDS("Clouds"),
    FLAME("Flame"),
    RAIN("Rain"),
    MYSTIC("Mystic"),
    PURPLE_SNAKE("Purple Snake"),
    HYPNOTIC("Hypnotic"),
    LOVE("Love");

    @Getter
    private String name;

    Trail(String name) {
        this.name = name;
    }

    @Override
    public String getDBName() {
        return this.toString();
    }
}
