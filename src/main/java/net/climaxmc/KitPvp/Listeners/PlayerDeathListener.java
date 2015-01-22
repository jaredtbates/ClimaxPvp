package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

public class PlayerDeathListener implements Listener {
	ClimaxPvp plugin;

	public PlayerDeathListener(ClimaxPvp plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		Player killer = player.getKiller();
		if (KitPvp.inKit.contains(player.getUniqueId())) {
			KitPvp.inKit.remove(player.getUniqueId());
		}
		if (killer != null) {
			event.setDeathMessage("§c" + player.getName() + " §7was killed by §a" + killer.getName());
			if (!killer.getUniqueId().equals(player.getUniqueId())) {
				if (KitPvp.killStreak.containsKey(killer.getUniqueId())) {
					KitPvp.killStreak.put(killer.getUniqueId(), KitPvp.killStreak.get(killer.getUniqueId()) + 1);
					int killerAmount = KitPvp.killStreak.get(killer.getUniqueId());
					if (killerAmount % 5 == 0) {
						plugin.getServer().broadcastMessage("§a" + killer.getName() + " §7has reached a KillStreak of §c" + killerAmount + "§7!");
						killerAmount = killerAmount * 2 + 10;
						plugin.getEconomy().depositPlayer(killer, killerAmount);
						killer.sendMessage(plugin.getPrefix() + "§aYou have gained §6$" + killerAmount + "§a!");
					} else {
						plugin.getEconomy().depositPlayer(killer, 10);
						killer.sendMessage(plugin.getPrefix() + "§aYou have gained §6$10§a!");
						killer.sendMessage(plugin.getPrefix() + "§aYou have reached a KillStreak of §6" + killerAmount + "§a!");
					}
				} else {
					KitPvp.killStreak.put(killer.getUniqueId(), 1);
					plugin.getEconomy().depositPlayer(killer, 10);
					killer.sendMessage(plugin.getPrefix() + "§aYou have gained §6$10§a!");
					killer.sendMessage(plugin.getPrefix() + "§aYou have reached a KillStreak of §6" + KitPvp.killStreak.get(killer.getUniqueId()) + "§a!");
				}
				if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
					if (KitPvp.killStreak.get(player.getUniqueId()) >= 10) {
						plugin.getServer().broadcastMessage("§a" + killer.getName() + " §7Destroyed §c" + player.getName() + "'s §6KillStreak of §a" + KitPvp.killStreak.get(player.getUniqueId()) + "!");
					}
					KitPvp.killStreak.remove(player.getUniqueId());
				}
			}
		} else {
			event.setDeathMessage("§c" + player.getName() + " §7died");
		}
		plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
			public void run() {
				if (player.isOnline()) {
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
					player.setVelocity(new Vector(0, 0, 0));
				}
			}
		});
	}
}
