package net.climaxmc.KitPvp.Utils;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class Cooldowns {
	static Table<UUID, String, Long> cooldowns = HashBasedTable.create();

	public static long getCooldown(Player player, String key) {
		return calculateRemainder(cooldowns.get(player.getUniqueId(), key));
	}

	public static long setCooldown(Player player, String key, long delay) {
		return calculateRemainder(cooldowns.put(player.getUniqueId(), key, System.currentTimeMillis() + delay));
	}

	public static boolean tryCooldown(Player player, String key, long delay) {
		if (getCooldown(player, key) == 0) {
			setCooldown(player, key, delay);
			return true;
		}
		return false;
	}
		 
	private static long calculateRemainder(Long expireTime) {
		return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
	}
}