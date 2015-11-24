package net.climaxmc.KitPvp.Utils;

import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class DuelsUtils {
    public static void createDuel(Player sender, Player target) {
        KitPvp.duels.add(new Duel(sender.getUniqueId(), target.getUniqueId()));
    }

    public static void removeDuel(Player target) {
            Iterator<Duel> pendingDuelsIterator = KitPvp.duels.iterator();
            while (pendingDuelsIterator.hasNext()) {
                Duel pendingDuel = pendingDuelsIterator.next();
                if (pendingDuel.getPlayer1UUID().equals(target.getUniqueId()) || pendingDuel.getPlayer2UUID().equals(target.getUniqueId())) {
                    pendingDuelsIterator.remove();
                }
            }
    }

    public static boolean hasPendingDuel(Player target) {
        for (Duel pendingDuel : KitPvp.duels) {
            if (pendingDuel.getPlayer1UUID().equals(target.getUniqueId()) || pendingDuel.getPlayer2UUID().equals(target.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInDuel(Player target) {
        for (Duel duel : KitPvp.duels) {
            if ((duel.getPlayer1UUID().equals(target.getUniqueId()) || duel.getPlayer2UUID().equals(target.getUniqueId())) && duel.isAccepted()) {
                return true;
            }
        }
        return false;
    }

    public static Player getDuelRequester(Player target) {
        for (Duel pendingDuel : KitPvp.duels) {
            if (pendingDuel.getPlayer2UUID().equals(target.getUniqueId())) {
                return Bukkit.getPlayer(pendingDuel.getPlayer1UUID());
            }
        }
        return null;
    }
}
