package net.climaxmc.KitPvp.Utils.Teams;// AUTHOR: gamer_000 (11/22/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class TeamUtils {
    private ClimaxPvp plugin;

    public TeamUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public static void createPendingRequest(Player target, Player sender) {
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

    public boolean hasRequested(Player player) {
        return KitPvp.pendingTeams.containsValue(player.getName());
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

    public boolean isOnTeam(Player target) {
        return KitPvp.currentTeams.containsKey(target.getName());
    }

    public boolean isTeaming(Player player) {
        return KitPvp.currentTeams.containsValue(player.getName());
    }

    public Player getRequester(Player target) {
        return plugin.getServer().getPlayer(KitPvp.pendingTeams.get(target.getName()));
    }

    public Player getTeammate(Player player) {
        for (Map.Entry<String, String> entry : KitPvp.currentTeams.entrySet()) {
            if (entry.getValue().equals(player.getName())) {
                return Bukkit.getServer().getPlayer(entry.getKey());
            }
        }
        return null;
    }
}
