package net.jaredbates.mc.ClimaxKits.Listeners;

import java.util.ArrayList;
import java.util.List;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Utils.Soup;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener {
	Main plugin;
	
	public PlayerInteractListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Damageable dm = event.getPlayer();
		ItemStack item = event.getItem();
		Block block = event.getClickedBlock();
		if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && dm.getHealth() < 20.0D) {
			if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				player.getInventory().getItemInHand().setType(Material.BOWL);
				player.setHealth(dm.getHealth() >= 13.0D ? 20.0D : dm.getHealth() + 7.0D);
			}
		}
		if (item != null) {
			if (item.getType().equals(Material.NETHER_STAR)) {
				{
					ItemStack kitPvp = new ItemStack(Material.DIAMOND_SWORD);
					ItemMeta kitPvpMeta = kitPvp.getItemMeta();
					List<String> lores = new ArrayList<String>();
					lores.add("A Standard Pvp Kit, Iron Armor and Diamond Sword.");
					kitPvpMeta.setLore(lores);
					kitPvpMeta.setDisplayName("§eKit Pvp");
					kitPvp.setItemMeta(kitPvpMeta);
					plugin.kitSelector.setItem(0, kitPvp);
				} {
					ItemStack kitHeavy = new ItemStack(Material.DIAMOND_CHESTPLATE);
					ItemMeta kitHeavyMeta = kitHeavy.getItemMeta();
					List<String> lores = new ArrayList<String>();
					lores.add("Better Armor and Sword, but Slow.");
					kitHeavyMeta.setLore(lores);
					kitHeavyMeta.setDisplayName("§eKit Heavy");
					kitHeavy.setItemMeta(kitHeavyMeta);
					plugin.kitSelector.setItem(1, kitHeavy);
				} {
					ItemStack kitArcher = new ItemStack(Material.BOW);
					ItemMeta kitArcherMeta = kitArcher.getItemMeta();
					List<String> lores = new ArrayList<String>();
					lores.add("Snipe them up!");
					kitArcherMeta.setLore(lores);
					kitArcherMeta.setDisplayName("§eKit Archer");
					kitArcher.setItemMeta(kitArcherMeta);
					plugin.kitSelector.setItem(2, kitArcher);
				} {
					ItemStack kitSoldier = new ItemStack(Material.FEATHER);
					ItemMeta kitSoldierMeta = kitSoldier.getItemMeta();
					List<String> lores = new ArrayList<String>();
					lores.add("Take to the skies with Kit Soldier!");
					kitSoldierMeta.setLore(lores);
					kitSoldierMeta.setDisplayName("§eKit Soldier");
					kitSoldier.setItemMeta(kitSoldierMeta);
					plugin.kitSelector.setItem(3, kitSoldier);
				}
				player.openInventory(plugin.kitSelector);
			}
		}
		if (block != null) {
			if (block.equals(Material.CHEST)) {
				event.setCancelled(true);
				Soup.add(plugin.soup, 0, 72);
				player.openInventory(plugin.soup);
				player.sendMessage("Debug 1");
			}
		}
		if (plugin.soldierKit.contains(player.getName())) {
			if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
				if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					player.setVelocity(new Vector(0, 0.7, 0));
				}
			}
		}
	}
}