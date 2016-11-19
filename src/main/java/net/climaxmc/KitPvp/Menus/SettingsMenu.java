package net.climaxmc.KitPvp.Menus;
/* Created by GamerBah on 4/3/2016 */


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
        Inventory inv = plugin.getServer().createInventory(null, 27, "§3Settings");

        SettingsFiles settingsFiles = new SettingsFiles();

        if (settingsFiles.getRespawnValue(player) == true) {
            inv.setItem(13, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("§7Currently: §a§lTrue"));
        } else {
            inv.setItem(13, new I(Material.REDSTONE).name(ChatColor.AQUA + "Toggle Insta-Respawn").lore("§7Currently: §c§lFalse"));
        }

        player.openInventory(inv);
    }
}
