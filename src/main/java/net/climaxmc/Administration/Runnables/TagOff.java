package net.climaxmc.Administration.Runnables;

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TagOff implements Runnable {
    private Player player;
    private ClimaxPvp plugin;

    public TagOff(ClimaxPvp plugin, Player p, long ticks) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this, ticks * 20L);
        this.player = p;
    }

    public void run() {
        String msg = ChatColor.GRAY + "You are no longer in combat.";
        int tagtime = 15;

        if (CombatLogListeners.tagged.contains(player.getUniqueId())) {
            if (System.currentTimeMillis() - CombatLogListeners.tagTime.get(player.getUniqueId()) > tagtime * 1000 - 20) {
                player.sendMessage(msg);
                CombatLogListeners.tagged.remove(player.getUniqueId());
                CombatLogListeners.tagTime.remove(player.getUniqueId());
            } else {
                long time = System.currentTimeMillis() / 1000L - CombatLogListeners.tagTime.get(player.getUniqueId()) / 1000L;
                //new TagOff(plugin, player, time);
            }
        }
    }
}
