package net.climaxmc.Donations.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParticlesInventory {
    private Inventory inventory = Bukkit.createInventory(null, 54, "§a§lParticle Settings");

    public ParticlesInventory(Player player) {
        addParticle(inventory, "Smoke", Material.COAL);
        addParticle(inventory, "Lava", Material.LAVA_BUCKET);
        addParticle(inventory, "Water", Material.WATER_BUCKET);
        addParticle(inventory, "Enchantment", Material.ENCHANTMENT_TABLE);
        player.openInventory(inventory);
    }

    private void addParticle(Inventory inventory, String name, Material material) {
        ItemStack particle = new ItemStack(material);
        ItemMeta particleMeta = particle.getItemMeta();
        particleMeta.setDisplayName("§a" + name);
        particle.setItemMeta(particleMeta);
        inventory.addItem(particle);
    }
}
