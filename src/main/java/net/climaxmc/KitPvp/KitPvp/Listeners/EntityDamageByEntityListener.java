package net.climaxmc.KitPvp.KitPvp.Listeners;

import net.climaxmc.KitPvp.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class EntityDamageByEntityListener implements Listener {
	Main plugin;

	public EntityDamageByEntityListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		/*if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (!plugin.time.containsKey(player.getName())) {
				plugin.time.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
				player.sendMessage("§cYou are now in combat!");
				new TagTime(player, 5, plugin);
				plugin.tag.add(player.getName());
			}
		}*/
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			/*if (!plugin.time.containsKey(player.getName())) {
				plugin.time.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
				player.sendMessage("§cYou are now in combat!");
				new TagTime(player, 5, plugin);
				plugin.tag.add(player.getName());
			}*/
			if (plugin.irongolemKit.contains(player.getName())) {
				if (event.getEntity() instanceof Player) {
					Player target = (Player) event.getEntity();
					if (player.getItemInHand().getType().equals(Material.RED_ROSE)) {
						target.setVelocity(new Vector(0.0D, 1.0D, 0.0D));
					}
				}
			}
		}
	}
}
