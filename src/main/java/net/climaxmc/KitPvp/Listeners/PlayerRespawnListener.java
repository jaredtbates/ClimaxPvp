package net.climaxmc.KitPvp.Listeners;

import java.util.ArrayList;
import java.util.List;

import net.climaxmc.ClimaxPvp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class PlayerRespawnListener implements Listener {
	ClimaxPvp plugin;
	
	public PlayerRespawnListener(ClimaxPvp plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("§a§lKit Selector");
		List<String> lores = new ArrayList<String>();
		lores.add("§5§o(Right Click) to select a kit!");
		itemmeta.setLore(lores);
		item.setItemMeta(itemmeta);
		player.getInventory().setItem(0, item);
	}
}
