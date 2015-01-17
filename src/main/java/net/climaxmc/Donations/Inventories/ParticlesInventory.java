package net.climaxmc.Donations.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ParticlesInventory {
    private Inventory inventory = Bukkit.createInventory(null, 54, "§a§lParticle Settings");

    public ParticlesInventory(Player player) {
        inventory.setItem(53, new ItemStack(Material.REDSTONE_BLOCK));
        player.openInventory(inventory);
    }
}
