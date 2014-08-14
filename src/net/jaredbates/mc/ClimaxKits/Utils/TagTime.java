package net.jaredbates.mc.ClimaxKits.Utils;

import net.jaredbates.mc.ClimaxKits.Main;

import org.bukkit.entity.Player;

public class TagTime implements Runnable {
	Main plugin;
	Player player;
	
	public TagTime(Player player, long ticks) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this);
		this.player = player;
	}
	
	public void run() {
		if (plugin.tag.contains(player.getName())) {
			if (System.currentTimeMillis() - (plugin.time.get(this.player.getName())).longValue() > 5 * 1000 - 20) {
				player.sendMessage("§aYou are no longer in combat!");
				plugin.tag.remove(player.getName());
			} else {
				long time = System.currentTimeMillis() / 1000L - plugin.time.get(player.getName()).longValue() / 1000L;
				new TagTime(player, time);
			}
		}
	}
}
