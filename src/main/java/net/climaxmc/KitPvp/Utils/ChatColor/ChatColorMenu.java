package net.climaxmc.KitPvp.Utils.ChatColor;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffect;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChatColorMenu {

    private ClimaxPvp plugin;

    public ChatColorMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, "\u00A78\u00A7lChat Color");

        for (int slot = 0; slot <= 9; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 27; slot <= 34; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        //inventory.setItem(26, new I(Material.STAINED_CLAY).durability(0).name(ChatColor.ITALIC + "" + ChatColor.GOLD + "Toggle Italics"));

        inventory.setItem(35, new I(Material.STAINED_GLASS_PANE).durability(14).name(ChatColor.RED + "Remove color"));

        int startSlot = 9;
        for (DChatColor color : DChatColor.values()) {
            inventory.setItem(startSlot++, new I(color.getMaterial()).durability(color.getDurability()).name(color.getColor() + color.getName()));
        }

        player.openInventory(inventory);
    }
}
