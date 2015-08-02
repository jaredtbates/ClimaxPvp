package net.climaxmc.Administration.Runnables;

import net.climaxmc.Administration.Listeners.OldCombatLogListeners;
import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Deprecated
public class OldTagOff implements Runnable {
    private Player player;
    private ClimaxPvp plugin;

    public OldTagOff(ClimaxPvp plugin, Player p, long ticks) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, ticks * 20L);
        this.player = p;
    }

    public void run() {
        String msg = ChatColor.GRAY + "You are no longer in combat.";
        int tagtime = 15;

        if (OldCombatLogListeners.tagged.contains(player.getUniqueId())) {
            if (System.currentTimeMillis() - OldCombatLogListeners.tagTime.get(player.getUniqueId()) > tagtime * 1000 - 20) {
                player.sendMessage(msg);
                OldCombatLogListeners.tagged.remove(player.getUniqueId());
                OldCombatLogListeners.tagTime.remove(player.getUniqueId());
            } else {
                long time = System.currentTimeMillis() / 1000L - OldCombatLogListeners.tagTime.get(player.getUniqueId()) / 1000L;
                new OldTagOff(plugin, player, time);
            }
        }
    }
}
