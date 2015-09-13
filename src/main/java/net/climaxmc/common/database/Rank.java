package net.climaxmc.common.database;

import lombok.*;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER("Owner", ChatColor.RED, Integer.MAX_VALUE),
    DEVELOPER("Developer", ChatColor.GOLD, 120),
    ADMINISTRATOR("Admin", ChatColor.RED, 100),
    MODERATOR("Mod", ChatColor.DARK_PURPLE, 80),
    HEAD_BUILDER("HeadBuilder", ChatColor.AQUA, 70),
    BUILDER("Builder", ChatColor.YELLOW, 60),
    JR_DEVELOPER("JrDev", ChatColor.GOLD, 50),
    HELPER("Helper", ChatColor.GREEN, 40),
    TWITCH(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Twi" + ChatColor.WHITE + "" + ChatColor.BOLD + "tch", null, 30),
    YOUTUBE(ChatColor.RED + "" + ChatColor.BOLD + "You" + ChatColor.WHITE + "" + ChatColor.BOLD + "Tube", null, 30),
    TRUSTED("Trusted", ChatColor.DARK_AQUA, 20),
    MASTER("Master", ChatColor.DARK_RED, 3),
    TITAN("Titan", ChatColor.GOLD, 2),
    NINJA("Ninja", ChatColor.AQUA, 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private ChatColor color;
    private int permissionLevel;
}
