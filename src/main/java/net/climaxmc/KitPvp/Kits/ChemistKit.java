package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class ChemistKit extends Kit {
    public ChemistKit() {
        super("Chemist", new ItemStack(Material.BREWING_STAND_ITEM), "Spam your Potions on everyone ^-^", ChatColor.GREEN);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
    	helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
    	helm.addEnchantment(Enchantment.DURABILITY, 3);
    	LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
    	helmmeta.setColor(Color.BLACK);
    	helm.setItemMeta(helmmeta);
    	player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        legs.addEnchantment(Enchantment.DURABILITY, 2);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) legs.getItemMeta();
        legmeta.setColor(Color.TEAL);
        legs.setItemMeta(legmeta);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta bootmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootmeta);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack harm = new ItemStack(Material.POTION, 20);
        Potion harmpot = new Potion(PotionType.INSTANT_DAMAGE, 1);
        harmpot.setSplash(true);
        harmpot.apply(harm);
        player.getInventory().addItem(harm);
        ItemStack poison = new ItemStack(Material.POTION, 3);
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
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
    	helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
    	helm.addEnchantment(Enchantment.DURABILITY, 3);
    	LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
    	helmmeta.setColor(Color.BLACK);
    	helm.setItemMeta(helmmeta);
    	player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        legs.addEnchantment(Enchantment.DURABILITY, 2);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) legs.getItemMeta();
        legmeta.setColor(Color.TEAL);
        legs.setItemMeta(legmeta);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta bootmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootmeta);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack harm = new ItemStack(Material.POTION, 20);
        Potion harmpot = new Potion(PotionType.INSTANT_DAMAGE, 1);
        harmpot.setSplash(true);
        harmpot.apply(harm);
        player.getInventory().addItem(harm);
        ItemStack poison = new ItemStack(Material.POTION, 3);
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