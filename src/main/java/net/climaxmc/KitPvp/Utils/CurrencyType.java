package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/22/2015)

import org.bukkit.ChatColor;

import static org.bukkit.ChatColor.*;

public enum CurrencyType {

    GOLD_CURRENCY("Gold", "Gold", GOLD, 1, 0, 0, 0, 0),
    GOLD_BLOCK_CURRENCY("Gold Block", "Gold Blocks", GOLD, 9, 1, 0, 0, 0),
    DIAMOND_CURRENCY("Diamond", "Diamonds", AQUA, 135, 15, 1, 0, 0),
    DIAMOND_BLOCK_CURRENCY("Diamond Block", "Diamond Blocks", AQUA, 1215, 135, 9, 1, 0),
    EMERALD_CURRENCY("Emerald", "Emeralds", GREEN, 30375, 3375, 225, 25, 1);

    private String singleName;
    private String pluralName;
    private ChatColor color;
    private int goldWorth;
    private int goldBlockWorth;
    private int diamondWorth;
    private int diamondBlockWorth;
    private int emeraldWorth;

    CurrencyType(String singleName, String pluralName, ChatColor color,  int goldWorth, int goldBlockWorth, int diamondWorth, int diamondBlockWorth, int emeraldWorth) {
        this.singleName = singleName;
        this.pluralName = pluralName;
        this.color = color;
        this.goldWorth = goldWorth;
        this.goldBlockWorth = goldBlockWorth;
        this.diamondWorth = diamondWorth;
        this.diamondBlockWorth = diamondBlockWorth;
        this.emeraldWorth = emeraldWorth;
    }

    public String getSingularName() {
        return singleName;
    }
    public String getPluralName() {
        return pluralName;
    }
    public ChatColor getColor() {
        return color;
    }
    public int getGoldWorth() {
        return goldWorth;
    }
    public int getGoldBlockWorth() {
        return goldBlockWorth;
    }
    public int getDiamondWorth() {
        return diamondWorth;
    }
    public int getDiamondBlockWorth() {
        return diamondBlockWorth;
    }
    public int getEmeraldWorth() {
        return emeraldWorth;
    }

}
