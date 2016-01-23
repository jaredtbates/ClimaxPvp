package net.climaxmc.KitPvp.Utils.Teams;// AUTHOR: gamer_000 (11/22/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;

public class TeamUtils {
    private ClimaxPvp plugin;

    public TeamUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    /*public static void createPendingRequest(Player target, Player sender) {
        KitPvp.pendingTeams.put(target.getName(), sender.getName());
    }

    public static void removePendingRequest(Player target) {
        if (KitPvp.pendingTeams.isEmpty() || !KitPvp.pendingTeams.containsKey(target.getName())) {
            return;
        } else {
            KitPvp.pendingTeams.remove(target.getName());
        }
    }

    public boolean hasPendingRequest(Player target) {
        return KitPvp.pendingTeams.containsKey(target.getName());
    }

    public static void createTeam(Player target, Player sender) {
        KitPvp.currentTeams.put(target.getName(), sender.getName());
        removePendingRequest(target);
    }

    public static void removeTeam(Player player) {
        if (KitPvp.currentTeams.isEmpty() || !KitPvp.currentTeams.containsKey(player.getName()) || KitPvp.currentTeams == null) {
            return;
        } else {
            KitPvp.currentTeams.remove(player.getName());
        }
    }

    public boolean isTeaming(Player target) {
        for (Player sender : plugin.getServer().getOnlinePlayers()) {
            return KitPvp.currentTeams.containsKey(target.getName());
        }
        return false;
    }

    public Player getRequester(Player target) {
        return plugin.getServer().getPlayer(KitPvp.pendingTeams.get(target.getName()));
    }*/
}
