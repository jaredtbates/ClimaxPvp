package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER("OWNER", ChatColor.RED, Integer.MAX_VALUE),
    DEVELOPER("DEV", ChatColor.GOLD, 120),
    ADMINISTRATOR("ADMIN", ChatColor.RED, 100),
    MODERATOR("MOD", ChatColor.DARK_PURPLE, 80),
    HEAD_BUILDER("BUILDER", ChatColor.AQUA, 70),
    BUILDER("BUILDER", ChatColor.YELLOW, 60),
    JR_DEVELOPER("JrDEV", ChatColor.GOLD, 50),
    HELPER("HELPER", ChatColor.GREEN, 40),
    TWITCH(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "TWI" + ChatColor.WHITE + "" + ChatColor.BOLD + "TCH", null, 30),
    YOUTUBE(ChatColor.RED + "" + ChatColor.BOLD + "YOU" + ChatColor.WHITE + "" + ChatColor.BOLD + "TUBE", null, 30),
    TRUSTED("TRUSTED", ChatColor.DARK_AQUA, 20),
    MASTER("MASTER", ChatColor.DARK_RED, 3),
    TITAN("TITAN", ChatColor.GOLD, 2),
    NINJA("NINJA", ChatColor.AQUA, 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private ChatColor color;
    private int permissionLevel;
}
