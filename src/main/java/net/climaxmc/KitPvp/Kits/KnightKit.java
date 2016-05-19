package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class KnightKit extends Kit {
    private Ability ironpunch = new Ability(1, 12, TimeUnit.SECONDS);

    public KnightKit() {
        super("Knight", new ItemStack(Material.IRON_INGOT), "Less armor, but slightly more speed!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
    }
    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        final Player player = event.getPlayer();
        if(KitManager.isPlayerInKit(player, this)){
            if(player.isSneaking()){
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
            }else{
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
            }
        }
    }
}