package net.climaxmc.KitPvp.Utils.Achievements;// AUTHOR: gamer_000 (12/23/2015)

import net.climaxmc.KitPvp.Utils.Rarity;

import static org.bukkit.ChatColor.*;

public enum Achievement {

    FIRST_KILL("Fresh from the Pile", "Get your first kill", Rarity.COMMON, 10, 0, 0, 0, 0),
    KILLING_1("Killer I", "Get a total of 250 kills", Rarity.COMMON, 10, 0, 0, 0, 0),
    KILLING_2("Killer II", "Get a total of 1000 kills", Rarity.RARE, 25, 0, 0, 0, 0),
    KILLING_3("Killer III", "Get a total of 2500 kills", Rarity.RARE, 75, 0, 0, 0, 0),
    KILLING_4("Killer IV", "Get a total of 5000 kills", Rarity.EPIC, 125, 0, 0, 0, 0),
    KILLING_5("Killer V", "Get a total of 10000 kills", Rarity.LEGENDARY, 300, 0, 0, 0, 0),
    FIRST_DEATH("Into the Grave", "Get your first death", Rarity.COMMON, 5, 0, 0, 0, 0),
    BLOODTHIRSTY("Bloodthirsty", "Get 5 kills without dying", Rarity.COMMON, 15, 0, 0, 0, 0),
    MERCILESS("Merciless", "Get 10 kills without dying", Rarity.RARE, 20, 0, 0, 0, 0),
    UNSTOPPABLE("Unstoppable", "Get 20 kills without dying", Rarity.EPIC, 30, 0, 0, 0, 0),
    NUCLEAR("Nuclear", "Get 30 kills without dying", Rarity.LEGENDARY, 40, 0, 0, 0, 0),
    STAFF_KILL("Look, Mom!", "Successfully kill a Staff member", Rarity.COMMON, 5, 0, 0, 0, 0);


    private String name;
    private String description;
    private Rarity rarity;
    private int goldWorth;
    private int goldBlockWorth;
    private int diamondWorth;
    private int diamondBlockWorth;
    private int emeraldWorth;

    Achievement(String name, String description, Rarity rarity,  int goldWorth, int goldBlockWorth, int diamondWorth, int diamondBlockWorth, int emeraldWorth) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.goldWorth = goldWorth;
        this.goldBlockWorth = goldBlockWorth;
        this.diamondWorth = diamondWorth;
        this.diamondBlockWorth = diamondBlockWorth;
        this.emeraldWorth = emeraldWorth;
    }

    public String getName() {
        return getRarity().getColor() + name;
    }
    public String getDescription() {
        return GRAY + description;
    }
    public Rarity getRarity() {
        return rarity;
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
