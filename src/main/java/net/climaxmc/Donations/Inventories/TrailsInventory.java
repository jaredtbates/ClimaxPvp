package net.climaxmc.Donations.Inventories;

import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TrailsInventory {
    public TrailsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§8§lTrail Settings");

        for (int slot = 0; slot <= 9; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 18; slot <= 25; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        inventory.setItem(26, new I(Material.STAINED_GLASS_PANE).durability(14).name(ChatColor.RED + "Remove Trail"));

        for (Trail trail : Trail.values()) {
            addTrail(inventory, trail, player, (int) trail.getCost());
        }

        player.openInventory(inventory);
    }

    int startSlot = 9;

    SettingsFiles settingsFiles = new SettingsFiles();

    private void addTrail(Inventory inventory, Trail trail, Player player, int cost) {
        ItemStack particle = new ItemStack(trail.getMaterial());
        ItemMeta particleMeta = particle.getItemMeta();
        if (!settingsFiles.isTrailUnlocked(player, trail.getName())) {
            particleMeta.setDisplayName(ChatColor.RED + trail.getName());
            ArrayList<String> lores = new ArrayList<>();
            lores.add("§cNot unlocked!");
            lores.add("§7Shift-Click to unlock: §a$" + cost);
            particleMeta.setLore(lores);
        } else {
            particleMeta.setDisplayName(ChatColor.GREEN + trail.getName());
            ArrayList<String> lores = new ArrayList<>();
            lores.add("§aUnlocked!");
            lores.add("§7Original Cost: $" + cost);
            particleMeta.setLore(lores);
        }
        particle.setItemMeta(particleMeta);
        inventory.setItem(startSlot++, particle);
    }
}
