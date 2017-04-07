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
        Inventory inv = plugin.getServer().createInventory(null, 27, "\u00A78\u00A7lSettings");

        SettingsFiles settingsFiles = new SettingsFiles();

        for (int slot = 0; slot <= 9; slot++) {
            inv.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 17; slot <= 26; slot++) {
            inv.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        if (!plugin.respawnValue.containsKey(player)) {
            plugin.respawnValue.put(player, settingsFiles.getRespawnValue(player));
        }
        if (plugin.respawnValue.get(player)) {
            inv.setItem(11, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("\u00A77Currently: \u00A7a\u00A7lTrue"));
        } else {
            inv.setItem(11, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("\u00A77Currently: \u00A7c\u00A7lFalse"));
        }

        if (settingsFiles.getReceiveMsgValue(player)) {
            inv.setItem(13, new I(Material.BOOK).name(ChatColor.AQUA + "Toggle Receiving Msgs").lore("\u00A77Currently: \u00A7a\u00A7lTrue"));
        } else {
            inv.setItem(13, new I(Material.BOOK).name(ChatColor.AQUA + "Toggle Receiving Msgs").lore("\u00A77Currently: \u00A7c\u00A7lFalse"));
        }

        if (settingsFiles.getGlobalChatValue(player)) {
            inv.setItem(15, new I(Material.PAPER).name(ChatColor.AQUA + "Toggle Global Chat").lore("\u00A77Currently: \u00A7a\u00A7lTrue"));
        } else {
            inv.setItem(15, new I(Material.PAPER).name(ChatColor.AQUA + "Toggle Global Chat").lore("\u00A77Currently: \u00A7c\u00A7lFalse"));
        }

        player.openInventory(inv);
    }
}
