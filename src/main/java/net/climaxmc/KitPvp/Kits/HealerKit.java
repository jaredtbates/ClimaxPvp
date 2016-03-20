package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealerKit extends Kit {
    public HealerKit() {
        super("Pvp", new ItemStack(Material.GOLDEN_APPLE), "This kit gives you the ability to heal yourself!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) chest.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        chest.setItemMeta(helmmeta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        addSoup(player.getInventory(), 1, 35);
        
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) chest.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        chest.setItemMeta(helmmeta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        addSoup(player.getInventory(), 1, 35);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
    }
}