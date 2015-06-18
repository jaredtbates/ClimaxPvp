package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class ThorKit extends Kit {
    ArrayList<UUID> thor = new ArrayList<UUID>();

    public ThorKit() {
        super("Thor", new ItemStack(Material.DIAMOND_AXE), "Punch a player with your Axe to Strike Lightning!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
    	ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    	sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.IRON_AXE));
        player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        addSoup(player.getInventory(), 2, 35);
        thor.add(player.getUniqueId());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof LightningStrike){
        	return;
        }
        if(event.getDamager() instanceof Player){
        	Player player = (Player) event.getDamager();
        	if(event.getEntity() instanceof Player){
        		Player target = (Player) event.getEntity();
        		if (thor.contains(player.getUniqueId())){
        			if(player.getItemInHand().getType() == Material.IRON_AXE){
        				event.setCancelled(true);
        				target.getWorld().strikeLightning(target.getLocation());
        			}
        		}
        	}
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (thor.contains(player.getUniqueId())) {
            thor.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (thor.contains(player.getUniqueId())) {
            thor.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (thor.contains(player.getUniqueId())) {
            thor.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	Player player = event.getPlayer();
    	if(thor.contains(player.getUniqueId())){
    		thor.remove(player.getUniqueId());
    	}
    }
}
