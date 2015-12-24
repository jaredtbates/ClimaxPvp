package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/23/2015)

import org.bukkit.ChatColor;

import static org.bukkit.ChatColor.*;

public enum Rarity {

    COMMON(GRAY, "Common"), RARE(BLUE, "Rare"), EPIC(GOLD, "EPIC"), LEGENDARY(LIGHT_PURPLE, BOLD + "LEGENDARY");

    private ChatColor color;
    private String label;

    Rarity(ChatColor color, String label) {
        this.color = color;
        this.label = label;
    }

    public ChatColor getColor() {
        return color;
    }

}
