package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.KitPvp.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class PlayerDeathListener implements Listener {
	Main plugin;

	public PlayerDeathListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		Player killer = player.getKiller();
		if (Main.inKit.contains(player.getUniqueId())) {
			Main.inKit.remove(player.getUniqueId());
		}
		if (killer != null) {
			event.setDeathMessage("§c" + player.getName() + " §7was killed by §a" + killer.getName());
			if (plugin.killStreak.containsKey(player.getUniqueId())) {
				plugin.killStreak.put(player.getUniqueId(), plugin.killStreak.get(player.getUniqueId()) + 1);
				int amount = plugin.killStreak.get(player.getUniqueId());
				if (amount % 5 == 0) {
					plugin.getServer().broadcastMessage("§a" + killer.getName() + " §7has reached a KillStreak of §c" + amount + "§7!");
					amount = amount * 2 + 10;
					plugin.economy.depositPlayer(killer, amount);
					killer.sendMessage(plugin.climax + "§aYou have gained §6$" + amount + "§a!");
				} else {
					plugin.economy.depositPlayer(killer, 10);
					killer.sendMessage(plugin.climax + "§aYou have gained §6$10§a!");
					killer.sendMessage(plugin.climax + "§aYou have reached a KillStreak of §6" + amount + "§a!");
				}
			} else {
				plugin.killStreak.put(player.getUniqueId(), 1);
				plugin.economy.depositPlayer(killer, 10);
				killer.sendMessage(plugin.climax + "§aYou have gained §6$10§a!");
				killer.sendMessage(plugin.climax + "§aYou have reached a KillStreak of §6" + plugin.killStreak.get(player.getUniqueId()) + "§a!");
			}
		} else {
			event.setDeathMessage("§c" + player.getName() + " §7died");
		}
		player.setVelocity(new Vector(0, 0, 0));
		plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
			public void run() {
				if (player.isDead()) {
					try {
						Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
						Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
						Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
						for (Object object : enumClass.getEnumConstants()) {
							if (object.toString().equals("PERFORM_RESPAWN")) {
								packet = packet.getClass().getConstructor(enumClass).newInstance(object);
							}
						}
						Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
						connection.getClass().getMethod("a", packet.getClass()).invoke(connection, packet);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
