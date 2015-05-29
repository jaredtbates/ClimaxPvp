package net.climaxmc.API;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public enum Rank {
    OWNER("Owner", ChatColor.RED, Integer.MAX_VALUE),
    DEVELOPER("Dev", ChatColor.GOLD, 120),
    ADMINISTRATOR("Admin", ChatColor.RED, 100),
    MODERATOR("Mod", ChatColor.LIGHT_PURPLE, 80),
    BUILDER("Builder", ChatColor.AQUA, 60),
    HELPER("Helper", ChatColor.GREEN, 40),
    TRUSTED("Trusted", ChatColor.DARK_AQUA, 20),
    DEFAULT(null, null, 0);

    @Getter
    private String prefix;
    @Getter
    private ChatColor color;
    @Getter
    private int permissionLevel;

    Rank(String prefix, ChatColor color, int permissionLevel) {
        this.prefix = prefix;
        this.color = color;
        this.permissionLevel = permissionLevel;
    }
}
