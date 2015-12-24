package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class DuelsRunnable implements Runnable {

    private ClimaxPvp plugin;
    private int amount = 0;

    public DuelsRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (Duel duel : KitPvp.duels) {
                if (duel.isAccepted()) {
                    Player p1 = plugin.getServer().getPlayer(duel.getPlayer1UUID());
                    Player p2 = plugin.getServer().getPlayer(duel.getPlayer2UUID());
                    DuelsMessages.sendDuelMessage(p1, p2, RED + "Testing 1");
                }
            }
            amount++;
        }, 20);
    }
}
