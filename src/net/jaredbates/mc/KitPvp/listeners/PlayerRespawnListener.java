package net.jaredbates.mc.KitPvp.listeners;

import java.util.ArrayList;
import java.util.List;

import net.jaredbates.mc.KitPvp.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class PlayerRespawnListener implements Listener {
	Main plugin;
	
	public PlayerRespawnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
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
		player.updateInventory();
	}
}
