package net.climaxmc.common.titles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum Title {
    MAGIC("Magic", ChatColor.translateAlternateColorCodes('&', "&8&l[&d||&5&lMagic&d||&8&l]"), Material.NAME_TAG, 500, 10),
    DAPPER("Dapper", ChatColor.translateAlternateColorCodes('&', "&8&l[&3&lDapper&8&l]"), Material.NAME_TAG, 500, 11),
    SHADY("Shady", ChatColor.translateAlternateColorCodes('&', "&8&l[&7||&8&lShady&7||&8&l]"), Material.NAME_TAG, 700, 12),
    BAE("Bae", ChatColor.translateAlternateColorCodes('&', "&8&l[&4&l♡&c&lBae&4&l♡&8&l]"), Material.NAME_TAG, 500, 13),
    TRASH("Trash", ChatColor.translateAlternateColorCodes('&', "&8&l[&2&lTrash&8&l]"), Material.NAME_TAG, 300, 14),
    HYPNOTIC("Dank", ChatColor.translateAlternateColorCodes('&', "&8&l[&6||&e&lDank&6||&8&l]"), Material.NAME_TAG, 700, 15),
    TOXIC("Toxic", ChatColor.translateAlternateColorCodes('&', "&8&l[&2&l☣&aToxic&2&l☣&8&l]"), Material.NAME_TAG, 1000, 16),
    DEADLY("Deadly", ChatColor.translateAlternateColorCodes('&', "&8&l[&8&l☬&7&lDeadly&8&l☬&8&l]"), Material.NAME_TAG, 1300, 18),
    RICH("Rich", ChatColor.translateAlternateColorCodes('&', "&8&l[&2&l$&a&lRich&2&l$&8&l]"), Material.NAME_TAG, 700, 19),
    LUCKY("Lucky", ChatColor.translateAlternateColorCodes('&', "&8&l[&a☘&f&lLucky&a☘&8&l]"), Material.NAME_TAG, 1000, 20),
    ERROR("Error", ChatColor.translateAlternateColorCodes('&', "&8&l[&c&l⚠&4&lError&c&l⚠&8&l]"), Material.NAME_TAG, 1300, 21),
    FROSTY("Frosty", ChatColor.translateAlternateColorCodes('&', "&8&l[&f❄&b&lFrosty&f❄&8&l]"), Material.NAME_TAG, 1000, 22),
    CAKE("Cake", ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lC&6&la&f&lk&6&le&8&l]"), Material.NAME_TAG, 500, 23),
    WIZARD("Wizard", ChatColor.translateAlternateColorCodes('&', "&8&l[&f&l⚡&5&lWizard&f&l⚡&8&l]"), Material.NAME_TAG, 1000, 24),
    TRYHARD("Tryhard", ChatColor.translateAlternateColorCodes('&', "&8&l[&b✔&a&lTryhard&8&l]"), Material.NAME_TAG, 1000, 25),
    SCRUB("Scrub", ChatColor.translateAlternateColorCodes('&', "&8&l[&6&lScrub&8&l]"), Material.NAME_TAG, 1000, 26),
    EZ("eZ", ChatColor.translateAlternateColorCodes('&', "&8&l[&f&l…&b&leZ&8&l]"), Material.NAME_TAG, 1000, 27),
    SPOOKY("Spooky", ChatColor.translateAlternateColorCodes('&', "&8&l[&f&lSp&8&loo&f&lky&8&l]"), Material.NAME_TAG, 1000, 28),
    THIEF("Thief", ChatColor.translateAlternateColorCodes('&', "&8&l[&f➴&7&lThief&f➶&8&l]"), Material.NAME_TAG, 1000, 29);

    private String name;
    private String title;
    private Material material;
    private double cost;
    private int slot;
}