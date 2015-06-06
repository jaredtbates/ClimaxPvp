package net.climaxmc.KitPvp.Kits;

import org.bukkit.ChatColor;

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
        super("Wither", new ItemStack(Material.SKULL_ITEM), "Shoot your WitherBow to Launch your Wither Head!", ChatColor.GREEN);
    }

    public void wear(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.BOW));
        ItemStack helmet = new ItemStack(Material.GOLD_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 3);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 34);
        player.getInventory().setItem(17, new ItemStack(Material.ARROW, 64));
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
