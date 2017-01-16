package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.titles.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class WarpsMenu implements Listener {

    private ClimaxPvp plugin;

    public WarpsMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inventory = plugin.getServer().createInventory(null, 27, "\u00A78\u00A7lWarps");

        for (int slot = 0; slot <= 8; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 18; slot <= 26; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        int startSlot = 10;
        for (String warpName : plugin.getWarpsConfig().getKeys(false)) {
            warpName = warpName.toLowerCase();
            int warpNumber = 0;
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (plugin.getPlayersInWarp().containsKey(players.getUniqueId())) {
                    if (plugin.getPlayersInWarp().get(players.getUniqueId()).toLowerCase().equals(warpName)) {
                        warpNumber++;
                    }
                }
            }
            Material material = Material.EYE_OF_ENDER;
            if (warpName.equals("fair")) {
                material = Material.DIAMOND_CHESTPLATE;
                warpName = "Fair";
            }
            if (warpName.equals("soup")) {
                material = Material.MUSHROOM_SOUP;
                warpName = "Soup";
            }
            if (warpName.equals("duel")) {
                material = Material.DIAMOND_AXE;
                warpName = "Duel";
            }
            if (warpName.equals("fps")) {
                material = Material.GLASS;
                warpName = "FPS";
            }
            inventory.setItem(startSlot, new I(material).name(ChatColor.GOLD + warpName).lore(ChatColor.GRAY + "Players playing here: " + ChatColor.AQUA + warpNumber).amount(warpNumber));
            startSlot = startSlot + 2;
        }

        player.openInventory(inventory);
    }
}
