package net.climaxmc.KitPvp.events;

import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum EventType {

    TOURNAMENT("Tournament", new I(Material.DIAMOND_SWORD).name(ChatUtils.color("&eTournament")).lore(ChatUtils.color("&71v1 tournament")));

    private String name;
    private ItemStack item;

    EventType(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }
    public ItemStack getItem() {
        return item;
    }
}
