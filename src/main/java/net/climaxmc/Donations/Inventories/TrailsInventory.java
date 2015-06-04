package net.climaxmc.Donations.Inventories;

import net.climaxmc.Donations.Enums.Trail;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrailsInventory {
    public TrailsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 18, ChatColor.RED + "" + ChatColor.BOLD + "Trail Settings");

        for (Trail trail : Trail.values()) {
            addTrail(inventory, trail);
        }

        player.openInventory(inventory);
    }

    private void addTrail(Inventory inventory, Trail trail) {
        ItemStack particle = new ItemStack(trail.getMaterial());
        ItemMeta particleMeta = particle.getItemMeta();
        particleMeta.setDisplayName("§a" + trail.getName());
        particle.setItemMeta(particleMeta);
        inventory.addItem(particle);
    }
}
