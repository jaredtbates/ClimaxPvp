package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.Statistics;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    ClimaxPvp plugin;

    public PlayerDeathListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        Player killer = player.getKiller();
        Statistics playerStatistics = plugin.getStatistics(player);
        playerStatistics.setDeaths(playerStatistics.getDeaths() + 1);
        plugin.getDatabase().save(playerStatistics);
        if (KitPvp.inKit.contains(player.getUniqueId())) {
            KitPvp.inKit.remove(player.getUniqueId());
        }
        if (killer != null) {
            Statistics killerStatistics = plugin.getDatabase().find(Statistics.class).where().ieq("UUID", killer.getUniqueId().toString()).findUnique();
            killerStatistics.setKills(killerStatistics.getKills() + 1);
            plugin.getDatabase().save(killerStatistics);
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
                player.spigot().respawn();
            }
        });
    }
}
