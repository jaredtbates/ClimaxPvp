package net.climaxmc.Donations.Inventories;

import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TrailsInventory {
    public TrailsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§8§lTrail Settings");

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
        particleMeta.setDisplayName(ChatColor.GREEN + trail.getName());
        if (!settingsFiles.isTrailUnlocked(player, trail.getName())) {
            ArrayList<String> lores = new ArrayList<>();
            lores.add("§cNot unlocked!");
            lores.add("§7Shift-Click to unlock: §a$" + cost);
            particleMeta.setLore(lores);
        } else {
            ArrayList<String> lores = new ArrayList<>();
            lores.add("§aUnlocked!");
            particleMeta.setLore(lores);
        }
        particle.setItemMeta(particleMeta);
        inventory.setItem(startSlot++, particle);
    }
}
