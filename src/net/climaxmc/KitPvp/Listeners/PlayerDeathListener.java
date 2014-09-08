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
		//final UUID uuid = player.getUniqueId();
		event.getDrops().clear();
		plugin.soldierKit.remove(player.getName());
		plugin.fishermanKit.remove(player.getName());
		/*if (plugin.players.getPlayerFile(uuid).contains("Deaths")) {
			plugin.players.getPlayerFile(uuid).set("Deaths", plugin.players.getPlayerFile(uuid).getInt("Deaths") + 1);
		} else {
			plugin.players.getPlayerFile(uuid).set("Deaths", 1);
		}
		plugin.players.savePlayerFile(uuid);
		player.sendMessage(plugin.players.getPlayerFile(uuid).getInt("Deaths") + "");*/
		player.setVelocity(new Vector(0, 0, 0));
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (player.isDead()) {
					try {
						Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
						Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
						Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
						for(Object ob : enumClass.getEnumConstants()) {
							if(ob.toString().equals("PERFORM_RESPAWN")) {
								packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
							}
						}
						Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
						con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
