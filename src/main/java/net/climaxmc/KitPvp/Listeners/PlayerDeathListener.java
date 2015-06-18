package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerDeathListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        Player killer = player.getKiller();

        plugin.getServer().getScheduler().runTask(plugin, () -> plugin.respawn(player));

        PlayerData playerData = plugin.getPlayerData(player);
        playerData.addDeaths(1);

        if (killer == null) {
            event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " died");
            return;
        }

        PlayerData killerData = plugin.getPlayerData(killer);
        killerData.addKills(1);
        event.setDeathMessage(ChatColor.RED + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.GREEN + killer.getName());

        if (killer.getUniqueId().equals(player.getUniqueId())) {
            return;
        }

        if (KitPvp.killStreak.containsKey(killer.getUniqueId())) {
            KitPvp.killStreak.put(killer.getUniqueId(), KitPvp.killStreak.get(killer.getUniqueId()) + 1);
            int killerAmount = KitPvp.killStreak.get(killer.getUniqueId());
            if (killerAmount % 5 == 0) {
                plugin.getServer().broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " has reached a KillStreak of " + ChatColor.RED + killerAmount + ChatColor.GRAY + "!");
                killerAmount = killerAmount * 2 + 10;
                killerData.depositBalance(killerAmount);
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$" + killerAmount + ChatColor.GREEN + "!");
            } else {
                killerData.depositBalance(10);
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$10" + ChatColor.GREEN + "!");
                killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have reached a KillStreak of " + ChatColor.GOLD + killerAmount + ChatColor.GREEN + "!");
            }
        } else {
            KitPvp.killStreak.put(killer.getUniqueId(), 1);
            killerData.depositBalance(10);
            killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have gained " + ChatColor.GOLD + "$10" + ChatColor.GREEN + "!");
            killer.sendMessage(plugin.getPrefix() + ChatColor.GREEN + "You have reached a KillStreak of " + ChatColor.GOLD + KitPvp.killStreak.get(killer.getUniqueId()) + ChatColor.GREEN + "!");
        }

        if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
            if (KitPvp.killStreak.get(player.getUniqueId()) >= 10) {
                plugin.getServer().broadcastMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " destroyed " + ChatColor.RED + player.getName() + "'s " + ChatColor.GOLD + "KillStreak of " + ChatColor.GREEN + KitPvp.killStreak.get(player.getUniqueId()) + "!");
            }
            KitPvp.killStreak.remove(player.getUniqueId());
        }
    }
}
