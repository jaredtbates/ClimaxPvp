package net.climaxmc.Listeners;

import net.climaxmc.Database;
import net.climaxmc.Main;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {
	Main plugin;
	
	public PlayerJoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isOp()) {
			if (!Database.containsPlayerIP(plugin, plugin.connection, player)) {
				Database.addIP(plugin, plugin.connection, player, player.getAddress());
				player.sendMessage("§cYour IP has been added to the database! If you login with a new IP you will be prompted for a password! Make sure to set it with §a/setpassword§c!");
			} else {
				plugin.inPassword.add(player.getUniqueId());
				plugin.passwordTitle.send(player);
				player.setGameMode(GameMode.ADVENTURE);
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100000, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100000, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100000, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100000, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 10));
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, -5));
			}
		}
	}
}
