package net.climaxmc.KitPvp.Kits;

import java.util.ArrayList;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class SoldierKit extends Kit {
	public SoldierKit() {
		super("Soldier", new ItemStack(Material.FEATHER), "Take to the skies with Kit Soldier!", 3);
	}
	
	ArrayList<String> soldier = new ArrayList<String>();

	public void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
		player.getInventory().setBoots(boots);
		addSoup(player.getInventory(), 1, 35);
		soldier.add(player.getName());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(soldier.contains(player.getName())){
			if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					player.setVelocity(new Vector(0, 0.7, 0));
				}
			}
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		soldier.remove(p.getName());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		soldier.remove(p.getName());
	}
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(p instanceof Player){
			soldier.remove(p.getName());
		}
	}
}
