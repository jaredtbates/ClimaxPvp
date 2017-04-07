package net.climaxmc.KitPvp.Utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
