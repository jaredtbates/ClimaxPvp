package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.titles.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class CosmeticsMenu implements Listener {

    private ClimaxPvp plugin;

    public CosmeticsMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 27, "§8§lCosmetics");

        inv.setItem(11, new I(Material.GHAST_TEAR).name("§aTrails"));

        inv.setItem(13, new I(Material.NAME_TAG).name("§5Titles"));

        player.openInventory(inv);
    }
}
