package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/19/2015)

import org.bukkit.Sound;

import static org.bukkit.ChatColor.*;

public enum KillSound {

    NONE(GRAY + "None", 0, null, 0, 0),
    POP(BLUE + "Item Pickup POP", 1, Sound.ITEM_PICKUP, 2, 2F),
    LEVEL_UP(BLUE + "LEVEL UP!", 1, Sound.LEVEL_UP, 2, 1.1F),
    DING(BLUE + "Ding!", 1, Sound.SUCCESSFUL_HIT, 2, 1.1F),
    MEOW(LIGHT_PURPLE + "Meow", 1, Sound.CAT_MEOW, 3, 1.1F),
    BURP(GOLD + "Burp", 1, Sound.BURP, 2, 1.1F);

    private String name;
    private int price;
    private Sound sound;
    private float volume;
    private float pitch;

    KillSound(String name, int price, Sound sound, float volume, float pitch) {
        this.name = name;
        this.price = price;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

}
