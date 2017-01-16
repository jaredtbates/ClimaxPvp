package net.climaxmc.KitPvp.Utils.ChatColor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum DChatColor {
    DARK_BLUE("Dark Blue", ChatColor.DARK_BLUE, Material.STAINED_CLAY, 11, 1000),
    GREEN("Green", ChatColor.DARK_GREEN, Material.STAINED_CLAY, 13, 500),
    CYAN("Cyan", ChatColor.DARK_AQUA, Material.STAINED_CLAY, 9, 700),
    DARK_RED("Dark Red", ChatColor.DARK_RED, Material.STAINED_CLAY, 14, 1000),
    PURPLE("Purple", ChatColor.DARK_PURPLE, Material.STAINED_CLAY, 10, 1000),
    GOLD("Gold", ChatColor.GOLD, Material.STAINED_CLAY, 1, 700),
    GRAY("Gray", ChatColor.GRAY, Material.STAINED_CLAY, 7, 800),
    BLUE("Blue", ChatColor.BLUE, Material.STAINED_CLAY, 3, 800),
    LIGHT_GREEN("Light Green", ChatColor.GREEN, Material.STAINED_CLAY, 5, 800),
    AQUA("Aqua", ChatColor.AQUA, Material.STAINED_CLAY, 3, 800),
    RED("Red", ChatColor.RED, Material.STAINED_CLAY, 14, 800),
    YELLOW("Yellow", ChatColor.YELLOW, Material.STAINED_CLAY, 4, 800);

    private String name;
    private ChatColor color;
    private Material material;
    private int durability;
    private double cost;
}