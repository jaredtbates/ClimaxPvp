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
        Inventory inv = plugin.getServer().createInventory(null, 27, "\u00A78\u00A7lCosmetics");

        for (int slot = 0; slot <= 9; slot++) {
            inv.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 17; slot <= 26; slot++) {
            inv.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        inv.setItem(11, new I(Material.GHAST_TEAR).name("\u00A7aTrails"));

        inv.setItem(13, new I(Material.NAME_TAG).name("\u00A75Titles"));

        inv.setItem(15, new I(Material.BLAZE_POWDER).name("\u00A7cDeath Effects"));

        player.openInventory(inv);
    }
}
