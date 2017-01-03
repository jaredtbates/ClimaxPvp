package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SanicKit extends Kit {
    public SanicKit() {
        super("Sanic", new ItemStack(Material.QUARTZ), "Gotta Go Fast!", ChatColor.BLUE);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setBoots(boots);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setBoots(boots);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
    }
}