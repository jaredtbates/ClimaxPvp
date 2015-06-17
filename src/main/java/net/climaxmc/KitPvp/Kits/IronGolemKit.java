package net.climaxmc.KitPvp.Kits;

import java.util.ArrayList;
import java.util.UUID;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronGolemKit extends Kit {
	ArrayList<UUID> irongolem = new ArrayList<UUID>();

    public IronGolemKit() {
        super("Iron Golem", new ItemStack(Material.RED_ROSE), "Punch people with your Rose to launch them in the air!", ChatColor.GREEN);
    }

    public void wear(Player player) {
        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        sword.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.RED_ROSE));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player target = (Player) event.getEntity();
                if(irongolem.contains(player.getUniqueId())){
                	if (player.getItemInHand().getType().equals(Material.RED_ROSE)) {
                        event.setCancelled(true);
                        target.setVelocity(new Vector(0, 1.2, 0));
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (irongolem.contains(player.getUniqueId())) {
        	irongolem.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (irongolem.contains(player.getUniqueId())) {
        	irongolem.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (irongolem.contains(player.getUniqueId())) {
        	irongolem.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	Player player = event.getPlayer();
    	if(irongolem.contains(player.getUniqueId())){
    		irongolem.remove(player.getUniqueId());
    	}
    }
}
