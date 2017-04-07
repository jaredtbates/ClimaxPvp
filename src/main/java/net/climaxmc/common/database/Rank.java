package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER(ChatColor.translateAlternateColorCodes('&', "&8&l[&4&o&lOwner&8&l]"), "\u00A74\u00A7o", Integer.MAX_VALUE),
    MANAGER(ChatColor.translateAlternateColorCodes('&', "&8&l[&b&lManager&8&l]"), "\u00A7b", 150),
    DEVELOPER(ChatColor.translateAlternateColorCodes('&', "&8&l[&6&lDev&8&l]"), "\u00A76", 120),
    ADMINISTRATOR(ChatColor.translateAlternateColorCodes('&', "&8&l[&c&lAdmin&8&l]"), "\u00A7c", 100),
    SENIOR_MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8&l[&5&o&lSr. Mod&8&l]"), "\u00A75\u00A7o", 90),
    MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8&l[&5&lMod&8&l]"), "\u00A75", 80),
    HEAD_BUILDER(ChatColor.translateAlternateColorCodes('&', "&8&l[&5&lHead-Builder&8&l]"), "\u00A75", 70),
    JR_DEVELOPER(ChatColor.translateAlternateColorCodes('&', "&8&l[&6&lJr. Dev&8&l]"), "\u00A76", 60),
    TRIAL_MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8&l[&d&lT-Mod&8&l]"), "\u00A7d", 50),
    BUILDER(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lBuilder&8&l]"), "\u00A7e", 40),
    TWITCH(ChatColor.translateAlternateColorCodes('&', "&8&l[&5&lTwi&f&ltch&8&l]"), "\u00A75", 30),
    YOUTUBE(ChatColor.translateAlternateColorCodes('&', "&8&l[&c&lYou&f&lTube&8&l]"), "\u00A7c", 30),
    TRUSTED(ChatColor.translateAlternateColorCodes('&', "&8&l[&3&lFriend&8&l]"), "\u00A73", 20),
    MASTER(ChatColor.translateAlternateColorCodes('&', "&8&l[&4&lMaster&8&l]"), "\u00A74", 4),
    TITAN(ChatColor.translateAlternateColorCodes('&', "&8&l[&6&lTitan&8&l]"), "\u00A76", 3),
    NINJA(ChatColor.translateAlternateColorCodes('&', "&8&[&b&lNinja&8&l]"), "\u00A7b", 2),
    BETA(ChatColor.translateAlternateColorCodes('&', ""), "\u00A76", 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private String color;
    private int permissionLevel;
}
