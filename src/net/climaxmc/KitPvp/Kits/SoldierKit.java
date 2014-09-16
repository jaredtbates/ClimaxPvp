package net.climaxmc.KitPvp.Kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Utils.Cooldowns;

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
import org.bukkit.util.Vector;

public class SoldierKit extends Kit {
	final int cooldownTime = 5;
	int task = 0;
	ArrayList<UUID> soldier = new ArrayList<UUID>();
	HashMap<UUID, Integer> cooldown = new HashMap<UUID, Integer>();
	
	public SoldierKit() {
		super("Soldier", new ItemStack(Material.FEATHER), "Take to the skies with Kit Soldier!", 3);
	}
	
	public void wear(Player player) {
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
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
					if (Cooldowns.tryCooldown(player, "", 5)) {
						player.setVelocity(new Vector(0, 0.7, 0));
					} else {
						player.sendMessage("");
					}
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
}
