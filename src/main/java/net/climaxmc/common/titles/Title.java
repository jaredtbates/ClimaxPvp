package net.climaxmc.common.titles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum Title {
    MAGIC("Magic", "§8§l[§d||§5§lMagic§d||§8§l]", Material.NAME_TAG, 500, 10),
    DAPPER("Dapper", "§8§l[§3§lDapper§8§l]", Material.NAME_TAG, 500, 11),
    SHADY("Shady", "§8§l[§7||§8§lShady§7||§8§l]", Material.NAME_TAG, 700, 12),
    BAE("Bae", "§8§l[§4§l♡§c§lBae§4§l♡§8§l]", Material.NAME_TAG, 500, 13),
    TRASH("Trash", "§8§l[§2§lTrash§8§l]", Material.NAME_TAG, 300, 14),
    HYPNOTIC("Dank", "§8§l[§6||§e§lDank§6||§8§l]", Material.NAME_TAG, 700, 15),
    TOXIC("Toxic", "§8§l[§2§l☣§aToxic§2§l☣§8§l]", Material.NAME_TAG, 1000, 16),
    DEADLY("Deadly", "§8§l[§8§l☬§7§lDeadly§8§l☬§8§l]", Material.NAME_TAG, 1300, 18),
    RICH("Rich", "§8§l[§2§l$§a§lRich§2§l$§8§l]", Material.NAME_TAG, 700, 19),
    LUCKY("Lucky","§8§l[§a☘§f§lLucky§a☘§8§l]",Material.NAME_TAG, 1000, 20),
    ERROR("Error","§8§l[§c§l⚠§4§lError§c§l⚠§8§l]",Material.NAME_TAG, 1300, 21),
    FROSTY("Frosty","§8§l[§f❄§b§lFrosty§f❄§8§l]",Material.NAME_TAG, 1000, 22),
    CAKE("Cake","§8§l[§f§lC§6§la§f§lk§6§le§8§l]",Material.NAME_TAG, 500, 23);

    private String name;
    private String title;
    private Material material;
    private double cost;
    private int slot;
}