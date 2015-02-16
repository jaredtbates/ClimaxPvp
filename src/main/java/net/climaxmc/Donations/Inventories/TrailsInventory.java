package net.climaxmc.Donations.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TrailsInventory {
    public TrailsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§a§lTrail Settings");
        addParticle(inventory, "Clouds", Material.COAL);
        addParticle(inventory, "Flame", Material.LAVA_BUCKET);
        addParticle(inventory, "Rain", Material.WATER_BUCKET);
        addParticle(inventory, "Mystic", Material.ENCHANTMENT_TABLE);
        addParticle(inventory, "Purple Snake", Material.ENDER_PEARL);
        addParticle(inventory, "Hypnotic", Material.QUARTZ);
        addParticle(inventory, "Love", Material.GOLDEN_CARROT);
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
