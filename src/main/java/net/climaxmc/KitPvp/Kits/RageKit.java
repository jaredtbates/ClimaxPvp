package net.climaxmc.KitPvp.Kits;

import java.util.concurrent.TimeUnit;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RageKit extends Kit {
	private Ability rage = new Ability(1, 16, TimeUnit.SECONDS);
	
    public RageKit() {
        super("Rage", new ItemStack(Material.MAGMA_CREAM), "Use your Rage Ability to take down Enemies!", ChatColor.RED);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLUE);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack magmacream = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta magmameta = magmacream.getItemMeta();
        magmameta.setDisplayName(ChatColor.AQUA + "Rage Ability");
        magmacream.setItemMeta(magmameta);
        player.getInventory().addItem(magmacream);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    	ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLUE);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack magmacream = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta magmameta = magmacream.getItemMeta();
        magmameta.setDisplayName(ChatColor.AQUA + "Rage Ability");
        magmacream.setItemMeta(magmameta);
        player.getInventory().addItem(magmacream);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.MAGMA_CREAM) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                	if (!rage.tryUse(player)) {
                        return;
                    }
                	player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Rage" + ChatColor.GOLD + " Ability!");
                	player.getWorld().playSound(player.getLocation(), Sound.GHAST_SCREAM, 3, 1);
                	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
                	player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
                	player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0));
                	player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 1));
                	ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
                    helmmeta.setColor(Color.RED);
                    helm.setItemMeta(helmmeta);
                    player.getInventory().setHelmet(helm);
                    player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
                    bootsmeta.setColor(Color.RED);
                    boots.setItemMeta(bootsmeta);
                    player.getInventory().setBoots(boots);
                    Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> {
                    	player.removePotionEffect(PotionEffectType.SPEED);
                    	player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    	player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    	player.removePotionEffect(PotionEffectType.JUMP);
                    	ItemStack helm2 = new ItemStack(Material.LEATHER_HELMET);
                        helm2.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta helmmeta2 = (LeatherArmorMeta) helm2.getItemMeta();
                        helmmeta2.setColor(Color.BLUE);
                        helm2.setItemMeta(helmmeta2);
                        player.getInventory().setHelmet(helm2);
                        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        ItemStack boots2 = new ItemStack(Material.LEATHER_BOOTS);
                        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        LeatherArmorMeta bootsmeta2 = (LeatherArmorMeta) boots2.getItemMeta();
                        bootsmeta2.setColor(Color.BLUE);
                        boots2.setItemMeta(bootsmeta2);
                        player.getInventory().setBoots(boots2);
                    }, 100);
                }
            }
        }
    }
}
