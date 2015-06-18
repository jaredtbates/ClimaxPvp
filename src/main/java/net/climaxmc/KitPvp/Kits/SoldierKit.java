package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class SoldierKit extends Kit {
    ArrayList<UUID> soldier = new ArrayList<UUID>();

    public SoldierKit() {
        super("Soldier", new ItemStack(Material.FEATHER), "Right Click Blocks with your Sword to Climb Walls!", ChatColor.RED);
    }

    protected void wear(Player player) {
    	ItemStack sword = new ItemStack(Material.IRON_SWORD);
    	sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
        soldier.add(player.getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (soldier.contains(player.getUniqueId())) {
            if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    player.setVelocity(new Vector(0, 0.5, 0));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (soldier.contains(player.getUniqueId())) {
            soldier.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (soldier.contains(player.getUniqueId())) {
            soldier.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (soldier.contains(player.getUniqueId())) {
            soldier.remove(player.getUniqueId());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	Player player = event.getPlayer();
    	if(soldier.contains(player.getUniqueId())){
    		soldier.remove(player.getUniqueId());
    	}
    }
}
