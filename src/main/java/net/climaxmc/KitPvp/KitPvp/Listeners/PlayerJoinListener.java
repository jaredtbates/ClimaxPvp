package net.climaxmc.KitPvp.KitPvp.Listeners;

import java.util.ArrayList;
import java.util.List;

import net.climaxmc.KitPvp.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class PlayerJoinListener implements Listener {
	Main plugin;
	
	public PlayerJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage("§3Join§8» " + player.getName());
		player.teleport(player.getWorld().getSpawnLocation());
		player.getInventory().clear();
		player.setHealth(20L);
		player.setMaxHealth(20L);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
		ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
		kitSelectorMeta.setDisplayName("§a§lKit Selector");
		List<String> kitSelectorLores = new ArrayList<String>();
		kitSelectorLores.add("§5§o(Right Click) to select a kit!");
		kitSelectorMeta.setLore(kitSelectorLores);
		kitSelector.setItemMeta(kitSelectorMeta);
		player.getInventory().setItem(0, kitSelector);
		player.updateInventory();
	}
}
