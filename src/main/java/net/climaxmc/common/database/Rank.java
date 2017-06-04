package net.climaxmc.common.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    OWNER(ChatColor.translateAlternateColorCodes('&', "&8[&4Owner&8]"), "\u00A74\u00A7o", Integer.MAX_VALUE),
    MANAGER(ChatColor.translateAlternateColorCodes('&', "&8[&bManager&8]"), "\u00A7b", 150),
    DEVELOPER(ChatColor.translateAlternateColorCodes('&', "&8[&6Dev&8]"), "\u00A76", 120),
    ADMINISTRATOR(ChatColor.translateAlternateColorCodes('&', "&8[&cAdmin&8]"), "\u00A7c", 100),
    SENIOR_MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8[&5Sr. Mod&8]"), "\u00A75\u00A7o", 90),
    MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8[&5Mod&8]"), "\u00A75", 80),
    HEAD_BUILDER(ChatColor.translateAlternateColorCodes('&', "&8[&5Head-Builder&8]"), "\u00A75", 70),
    JR_DEVELOPER(ChatColor.translateAlternateColorCodes('&', "&8[&6Jr. Dev&8]"), "\u00A76", 60),
    TRIAL_MODERATOR(ChatColor.translateAlternateColorCodes('&', "&8[&dT-Mod&8]"), "\u00A7d", 50),
    BUILDER(ChatColor.translateAlternateColorCodes('&', "&8[&eBuilder&8]"), "\u00A7e", 40),
    TWITCH(ChatColor.translateAlternateColorCodes('&', "&8[&5Twi&ftch&8]"), "\u00A75", 30),
    YOUTUBE(ChatColor.translateAlternateColorCodes('&', "&8[&cYou&fTube&8]"), "\u00A7c", 30),
    TRUSTED(ChatColor.translateAlternateColorCodes('&', "&8[&3Friend&8]"), "\u00A73", 20),
    TITAN(ChatColor.translateAlternateColorCodes('&', "&8[&6Titan&8]"), "\u00A76", 4),
    MASTER(ChatColor.translateAlternateColorCodes('&', "&8[&4Master&8]"), "\u00A74", 3),
    NINJA(ChatColor.translateAlternateColorCodes('&', "&8&[&bNinja&8]"), "\u00A7b", 2),
    BETA(ChatColor.translateAlternateColorCodes('&', ""), "\u00A76", 1),
    DEFAULT(null, null, 0);

    private String prefix;
    private String color;
    private int permissionLevel;
}
