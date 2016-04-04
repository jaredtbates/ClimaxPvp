package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER("Owner", ChatColor.RED, Integer.MAX_VALUE),
    MANAGER("Manager", ChatColor.RED, 150),
    DEVELOPER("Dev", ChatColor.GOLD, 120),
    ADMINISTRATOR("Admin", ChatColor.RED, 100),
    MODERATOR("Mod", ChatColor.DARK_PURPLE, 80),
    HEAD_BUILDER("Builder", ChatColor.YELLOW, 70),
    JR_DEVELOPER("Jr. Dev", ChatColor.GOLD, 60),
    HELPER("Helper", ChatColor.GREEN, 50),
    BUILDER("Builder", ChatColor.YELLOW, 40),
    TWITCH(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Twi" + ChatColor.WHITE + "" + ChatColor.BOLD + "tch", ChatColor.DARK_PURPLE, 30),
    YOUTUBE(ChatColor.RED + "" + ChatColor.BOLD + "You" + ChatColor.WHITE + "" + ChatColor.BOLD + "Tube", ChatColor.RED, 30),
    TRUSTED("Trusted", ChatColor.DARK_AQUA, 20),
    MASTER("Master", ChatColor.DARK_RED, 3),
    TITAN("Titan", ChatColor.GOLD, 2),
    NINJA("Ninja", ChatColor.AQUA, 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private ChatColor color;
    private int permissionLevel;
}
