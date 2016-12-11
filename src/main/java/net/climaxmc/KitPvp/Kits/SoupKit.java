package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class SoupKit {

    public SoupKit() {}

    public void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().clear();

        ItemStack helm = new ItemStack(Material.IRON_HELMET);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        player.getInventory().setBoots(boots);

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        Kit.addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {}
}
