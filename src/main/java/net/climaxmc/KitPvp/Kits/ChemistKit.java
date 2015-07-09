package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class ChemistKit extends Kit {
    public ChemistKit() {
        super("Chemist", new ItemStack(Material.BREWING_STAND_ITEM), "Spam your Potions on everyone ^-^", ChatColor.RED);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack harm = new ItemStack(Material.POTION, 32);
        Potion harmpot = new Potion(PotionType.INSTANT_DAMAGE, 1);
        harmpot.setSplash(true);
        harmpot.apply(harm);
        player.getInventory().addItem(harm);
        ItemStack poison = new ItemStack(Material.POTION, 12);
        Potion poisonpot = new Potion(PotionType.POISON, 1);
        poisonpot.setSplash(true);
        poisonpot.apply(poison);
        player.getInventory().addItem(poison);
        ItemStack speed = new ItemStack(Material.POTION, 3);
        Potion spdpot = new Potion(PotionType.SPEED, 1);
        spdpot.setSplash(true);
        spdpot.apply(speed);
        player.getInventory().addItem(speed);
        addSoup(player.getInventory(), 4, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack harm = new ItemStack(Material.POTION, 32);
        Potion harmpot = new Potion(PotionType.INSTANT_DAMAGE, 1);
        harmpot.setSplash(true);
        harmpot.apply(harm);
        player.getInventory().addItem(harm);
        ItemStack poison = new ItemStack(Material.POTION, 12);
        Potion poisonpot = new Potion(PotionType.POISON, 1);
        poisonpot.setSplash(true);
        poisonpot.apply(poison);
        player.getInventory().addItem(poison);
        ItemStack speed = new ItemStack(Material.POTION, 3);
        Potion spdpot = new Potion(PotionType.SPEED, 1);
        spdpot.setSplash(true);
        spdpot.apply(speed);
        player.getInventory().addItem(speed);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }
}