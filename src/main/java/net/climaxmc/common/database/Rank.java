package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER("§8§l[§4§o§lOwner§8§l]", "§4§o", Integer.MAX_VALUE),
    MANAGER("§8§l[§b§lManager§8§l]", "§b", 150),
    DEVELOPER("§8§l[§6§lDev§8§l]", "§6", 120),
    ADMINISTRATOR("§8§l[§c§lAdmin§8§l]", "§c", 100),
    SENIOR_MODERATOR("§8§l[§5§o§lSr. Mod§8§l]", "§5§o", 80),
    MODERATOR("§8§l[§5§lMod§8§l]", "§5", 80),
    HEAD_BUILDER("§8§l[§5§lHead-Builder§8§l]", "§5", 70),
    JR_DEVELOPER("§8§l[§6§lJr. Dev§8§l]", "§6", 60),
    TRIAL_MODERATOR("§8§l[§d§lT-Mod§8§l]", "§d", 80),
    BUILDER("§8§l[§e§lBuilder§8§l]", "§e", 40),
    TWITCH("§8§l[" + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Twi" + ChatColor.WHITE + "" + ChatColor.BOLD + "tch" + "§8§l]", "§5", 30),
    YOUTUBE("§8§l[" + ChatColor.RED + "" + ChatColor.BOLD + "You" + ChatColor.WHITE + "" + ChatColor.BOLD + "Tube" + "§8§l]", "§c", 30),
    TRUSTED("§8§l[§3§lFriend§8§l]", "§3", 20),
    MASTER("§8§l[§4§lMaster§8§l]", "§4", 4),
    TITAN("§8§l[§6§lTitan§8§l]", "§6", 3),
    NINJA("§8§[§b§lNinja§8§l]", "§b", 2),
    BETA("", "§6", 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private String color;
    private int permissionLevel;
}
