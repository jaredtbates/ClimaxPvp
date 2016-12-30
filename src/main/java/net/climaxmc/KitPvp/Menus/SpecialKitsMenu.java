package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.RabbitKit;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.KitPvp.Utils.Titles.TitleFiles;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.titles.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpecialKitsMenu implements Listener {

    private ClimaxPvp plugin;

    public SpecialKitsMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 54, "\u00A78\u00A7lSpecial Kits");

        inv.setItem(1, new I(Material.STAINED_GLASS_PANE).name("\u00A76OG Kits").durability(15));
        inv.setItem(2, new I(Material.STAINED_GLASS_PANE).durability(1).name("\u00A76OG Kits").lore("\u00A76\u00A7oSome kits from the original kitpvp!"));
        inv.setItem(3, new I(Material.STAINED_GLASS_PANE).name("\u00A76OG Kits").durability(15));

        inv.setItem(10, new I(Material.CARROT_ITEM).name("\u00A76Rabbit").lore("Gain Jump-Boost 2 while Fighting!").lore("\u00A77Cost: \u00A7a$800"));
        inv.setItem(10, new I(Material.CARROT_ITEM).name("\u00A76Rabbit").lore("Gain Jump-Boost 2 while Fighting!").lore("\u00A77Cost: \u00A7a$800"));

        inv.setItem(5, new I(Material.STAINED_GLASS_PANE).name("\u00A7bidk yet").durability(15));
        inv.setItem(6, new I(Material.STAINED_GLASS_PANE).durability(3).name("\u00A7bidk yet").lore("\u00A7b\u00A7owow!"));
        inv.setItem(7, new I(Material.STAINED_GLASS_PANE).name("\u00A7bidk yet").durability(15));

        inv.setItem(28, new I(Material.STAINED_GLASS_PANE).name("\u00A7aUnlockables").durability(15));
        inv.setItem(29, new I(Material.STAINED_GLASS_PANE).durability(5).name("\u00A7aUnlockables").lore("\u00A7a\u00A7oNew kits unlockable with money!"));
        inv.setItem(30, new I(Material.STAINED_GLASS_PANE).name("\u00A7aUnlockables").durability(15));

        inv.setItem(32, new I(Material.STAINED_GLASS_PANE).name("\u00A7cHoliday Kits").durability(15));
        inv.setItem(33, new I(Material.STAINED_GLASS_PANE).durability(14).name("\u00A7cHoliday Kits").lore("\u00A7c\u00A7oSpecial unlockables reserved for holidays!"));
        inv.setItem(34, new I(Material.STAINED_GLASS_PANE).name("\u00A7cHoliday Kits").durability(15));

        player.openInventory(inv);
    }
}
