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
			plugin.economy.depositPlayer(killer, 10);
			killer.sendMessage(plugin.climax + "§aYou have gained §6$10§a!");
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
