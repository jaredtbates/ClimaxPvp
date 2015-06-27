package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class BlazeKit extends Kit {
	private Ability blaze = new Ability(1, 20, TimeUnit.SECONDS);
	
    public BlazeKit() {
        super("Blaze", new ItemStack(Material.BLAZE_POWDER), "Use your Blaze ability to set everyone within 5 Blocks on fire!", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze Ability");
        blazePowder.setItemMeta(blazeMeta);
        player.getInventory().addItem(blazePowder);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze Ability");
        blazePowder.setItemMeta(blazeMeta);
        player.getInventory().addItem(blazePowder);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_POWDER) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!blaze.tryUse(player)) {
                        return;
                    }
                    
                	for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                		if (entity instanceof Player) {
                		    Player players = (Player) entity;
                		    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 0));
                		    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 0));
                		    player.getWorld().playSound(player.getLocation(), Sound.BLAZE_BREATH, 2, 2);
                		    players.setFireTicks(140);
                		}
                	}
                }
            }
        }
    }
}
