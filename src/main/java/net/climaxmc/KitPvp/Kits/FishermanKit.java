package net.climaxmc.KitPvp.Kits;

import java.util.ArrayList;
import java.util.UUID;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class FishermanKit extends Kit {
	ArrayList<UUID> fisherman = new ArrayList<UUID>();
	
    public FishermanKit() {
        super("Fisherman", new ItemStack(Material.FISHING_ROD), "Hook a player and retract your line to Fish them to you!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(fisherman.contains(player.getUniqueId())){
        	if (event.getCaught() instanceof Player) {
                event.getCaught().teleport(player.getLocation());
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (fisherman.contains(player.getUniqueId())) {
            fisherman.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (fisherman.contains(player.getUniqueId())) {
        	fisherman.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (fisherman.contains(player.getUniqueId())) {
        	fisherman.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	Player player = event.getPlayer();
    	if(fisherman.contains(player.getUniqueId())){
    		fisherman.remove(player.getUniqueId());
    	}
    }
}