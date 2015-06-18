package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class ViperKit extends Kit {
    ArrayList<UUID> viper = new ArrayList<UUID>();

    public ViperKit() {
        super("Viper", new ItemStack(Material.SPIDER_EYE), "With each hit you poison your enemy!", ChatColor.BLUE);
    }

    protected void wear(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setColor(Color.LIME);
        helmet.setItemMeta(meta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        addSoup(player.getInventory(), 1, 35);
        viper.add(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPvp(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player){
        	Player player = (Player) event.getDamager();
        	if (event.getEntity() instanceof Player){
        		Player damaged = (Player) event.getEntity();
        		if (viper.contains(player.getUniqueId())){
        			if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD){
        				damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
        			}
        		}
        	}
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (viper.contains(player.getUniqueId())) {
            viper.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (viper.contains(player.getUniqueId())) {
            viper.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (viper.contains(player.getUniqueId())) {
            viper.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	Player player = event.getPlayer();
    	if(viper.contains(player.getUniqueId())){
    		viper.remove(player.getUniqueId());
    	}
    }
}
