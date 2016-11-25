package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class SettingsMenu implements Listener {

    private ClimaxPvp plugin;

    public SettingsMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 27, "§8§lSettings");

        SettingsFiles settingsFiles = new SettingsFiles();

        if (settingsFiles.getRespawnValue(player)) {
            inv.setItem(11, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("§7Currently: §a§lTrue"));
        } else {
            inv.setItem(11, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("§7Currently: §c§lFalse"));
        }

        if (settingsFiles.getReceiveMsgValue(player)) {
            inv.setItem(13, new I(Material.BOOK).name(ChatColor.AQUA + "Toggle Receiving Msgs").lore("§7Currently: §a§lTrue"));
        } else {
            inv.setItem(13, new I(Material.BOOK).name(ChatColor.AQUA + "Toggle Receiving Msgs").lore("§7Currently: §c§lFalse"));
        }

        if (settingsFiles.getGlobalChatValue(player)) {
            inv.setItem(15, new I(Material.PAPER).name(ChatColor.AQUA + "Toggle Global Chat").lore("§7Currently: §a§lTrue"));
        } else {
            inv.setItem(15, new I(Material.PAPER).name(ChatColor.AQUA + "Toggle Global Chat").lore("§7Currently: §c§lFalse"));
        }

        player.openInventory(inv);
    }
}
