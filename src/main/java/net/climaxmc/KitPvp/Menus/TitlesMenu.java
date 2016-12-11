package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.KitPvp.Utils.Titles.TitleFiles;
import net.climaxmc.common.titles.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class TitlesMenu implements Listener {

    private ClimaxPvp plugin;

    public TitlesMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 54, "§8§lTitles");

        for (int i = 0; i <= 9; i++) {
            inv.setItem(i, new I(Material.STAINED_GLASS_PANE).name(" ").durability(15));
        }
        inv.setItem(17, new I(Material.STAINED_GLASS_PANE).name(" ").durability(15));
        inv.setItem(36, new I(Material.STAINED_GLASS_PANE).name(" ").durability(15));
        for (int i = 44; i <= 48; i++) {
            inv.setItem(i, new I(Material.STAINED_GLASS_PANE).name(" ").durability(15));
        }
        inv.setItem(49, new I(Material.STAINED_GLASS_PANE).name("§cRemove Title").durability(14));
        for (int i = 50; i <= 53; i++) {
            inv.setItem(i, new I(Material.STAINED_GLASS_PANE).name(" ").durability(15));
        }


        for (Title title : Title.values()) {
            TitleFiles titleFiles = new TitleFiles();
            if (titleFiles.isTitleUnlocked(player, title)) {
                inv.setItem(title.getSlot(), new I(title.getMaterial()).name(title.getTitle()).lore("§aUnlocked!").lore("§7Original Cost: $" + title.getCost()));
            } else {
                inv.setItem(title.getSlot(), new I(title.getMaterial()).name(title.getTitle()).lore("§cNot unlocked!").lore("§7Shift-Click to unlock: §c$" + title.getCost()));
            }
        }

        player.openInventory(inv);
    }
}
