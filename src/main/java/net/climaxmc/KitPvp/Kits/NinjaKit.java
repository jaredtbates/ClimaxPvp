package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NinjaKit extends Kit {

    public NinjaKit() {
        super("Ninja", new ItemStack(Material.IRON_SWORD), "Gotta Go Fast! with Kit Ninja!", ChatColor.GRAY);
    }

    public void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().clear();
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 5);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        addSoup(player.getInventory(), 1, 35);
    }
}