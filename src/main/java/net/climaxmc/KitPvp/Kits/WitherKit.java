package net.climaxmc.KitPvp.Kits;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import net.climaxmc.KitPvp.Kit;

import java.util.ArrayList;
import java.util.UUID;

public class WitherKit extends Kit {

    ArrayList<UUID> wither = new ArrayList<UUID>();

    public WitherKit() {
        super("Wither", new ItemStack(Material.SKULL_ITEM), "Shoot your WitherBow to Launch your Wither Head!", ChatColor.RED);
    }

    public void wear(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.BOW));
        player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
        wither.add(player.getUniqueId());
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
    	if (event.getEntity() instanceof Player){
    		Player player = (Player) event.getEntity();
    		if (wither.contains(player.getUniqueId())) {
    			event.setCancelled(true);
    			player.launchProjectile(WitherSkull.class).setVelocity(event.getProjectile().getVelocity());
    		}
    	}
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (wither.contains(player.getUniqueId())) {
            wither.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (wither.contains(player.getUniqueId())) {
            wither.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (wither.contains(player.getUniqueId())) {
            wither.remove(player.getUniqueId());
        }
    }
}
