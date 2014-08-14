package net.jaredbates.mc.ClimaxKits.Listeners;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Utils.TagTime;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
	Main plugin;

	public EntityDamageByEntityListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			plugin.time.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
			if (plugin.tag.contains(player.getName())) {
				player.sendMessage("§cYou are now in combat!");
				new TagTime(player, 5);
			}
			plugin.tag.add(player.getName());
		}
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			plugin.time.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
			if (plugin.time.get(player.getName()).equals("false")) {
				player.sendMessage("§cYou are now in combat!");
				new TagTime(player, 5);
			}
			plugin.tag.add(player.getName());
		}
	}
}
