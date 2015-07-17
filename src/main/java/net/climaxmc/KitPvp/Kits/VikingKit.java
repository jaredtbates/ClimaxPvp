package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class VikingKit extends Kit {
	
    public VikingKit() {
        super("Viking", new ItemStack(Material.DIAMOND_AXE), "You damage all players within 4 blocks of you!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta axemeta = axe.getItemMeta();
        axemeta.setDisplayName(ChatColor.GOLD + "Viking Axe");
        axe.setItemMeta(axemeta);
        player.getInventory().addItem(axe);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.MAROON);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
    	ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta axemeta = axe.getItemMeta();
        axemeta.setDisplayName(ChatColor.GOLD + "Viking Axe");
        axe.setItemMeta(axemeta);
        player.getInventory().addItem(axe);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.MAROON);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onEntityDamge(EntityDamageByEntityEvent event) {
    	if(event.getDamager() instanceof Player){
    		Player player = (Player) event.getDamager();
    		if (KitManager.isPlayerInKit(player, this)) {
    			if(player.getInventory().getItemInHand().equals(new ItemStack(Material.DIAMOND_AXE))){
    				for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
    					if (entity instanceof Player) {
    						Player players = (Player) entity;
    						event.setCancelled(true);
    						players.damage(5);
    						Vector vector = player.getEyeLocation().getDirection();
    						vector.multiply(0.5F);
    						vector.setY(0.3);
    						players.setVelocity(vector);
    					}
    				}
                }
            }
        }
    }
}
