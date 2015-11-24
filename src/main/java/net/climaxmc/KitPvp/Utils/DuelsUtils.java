package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (11/10/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;

public class DuelsUtils {
    private ClimaxPvp plugin;

    public DuelsUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public static void createPendingDuel(Player target, Player sender) {
        KitPvp.pendingDuels.put(target.getUniqueId(), sender.getUniqueId());
    }

    public static void removePendingDuel(Player target) {
        if (KitPvp.pendingDuels.isEmpty() || !KitPvp.pendingDuels.containsKey(target.getUniqueId()) || KitPvp.pendingDuels == null) {
            return;
        } else {
            KitPvp.pendingDuels.remove(target.getUniqueId());
        }
    }

    public boolean hasPendingDuel(Player target) {
        for (Player sender : plugin.getServer().getOnlinePlayers()) {
            return KitPvp.pendingDuels.containsKey(target.getUniqueId());
        }
        return false;
    }

    public static void createCurrentDuel(Player target, Player sender) {
        KitPvp.currentDuels.put(target.getUniqueId(), sender.getUniqueId());
        removePendingDuel(target);
    }

    public static void removeCurrentDuel(Player target) {
        if (KitPvp.currentDuels.isEmpty() || !KitPvp.currentDuels.containsKey(target.getUniqueId()) || KitPvp.currentDuels == null) {
            return;
        } else {
            KitPvp.currentDuels.remove(target.getUniqueId());
        }
    }

    public boolean isInDuel(Player target) {
        for (Player sender : plugin.getServer().getOnlinePlayers()) {
            return KitPvp.currentDuels.containsKey(target.getUniqueId());
        }
        return false;
    }

    public Player getDuelRequester(Player target) {
        return plugin.getServer().getPlayer(KitPvp.pendingDuels.get(target.getUniqueId()));
    }
}
