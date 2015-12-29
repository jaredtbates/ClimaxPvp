package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.RED;

public class DuelsRunnable implements Runnable {

    private ClimaxPvp plugin;
    private int amount = 0;
    DuelsMessages duelsMessages = new DuelsMessages(plugin);
    public Player p1;
    public Player p2;

    public DuelsRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (Duel duel : KitPvp.duels) {
                if (duel.isAccepted()) {
                    if (!duel.isStarted()) {
                        duelsMessages.sendDuelMessage(p1, p2, RED + "Testing 1");
                    }
                }
            }
            amount++;
        }, 20);
    }
}
