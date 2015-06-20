package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AnvilKit extends Kit {
    public AnvilKit() {
        super("Anvil", new ItemStack(Material.ANVIL), "You take, nor deal knockback!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
    	player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
    }
    protected void wearNoSoup(Player player){
    	player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player){
        	Player player = (Player) event.getDamager();
        	if(event.getEntity() instanceof Player){
        		Player target = (Player) event.getEntity();
        		if(KitManager.isPlayerInKit(player, this)){
        			player.setVelocity(new Vector(0, 0, 0));
        			target.setVelocity(new Vector(0, 0, 0));
        		}
        	}
        }
    }
}
