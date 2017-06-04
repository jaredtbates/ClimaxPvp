package net.climaxmc.KitPvp.events.tournament;

import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TPManager {

    private ClimaxPvp plugin;
    public TPManager(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private HashMap<Player, TournamentPlayer> currentPlayers = new HashMap<>();

    public TournamentPlayer getTP(Player player) {
        if (!currentPlayers.keySet().contains(player)) {
            addTP(player);
        }
        for (Player players : currentPlayers.keySet()) {
            if (players.getName().equals(player)) {
                return currentPlayers.get(player);
            }
        }
        return null;
    }

    public void addTP(Player player) {
        currentPlayers.put(player, new TournamentPlayer());
    }
    public void removeTP(Player player) {
        currentPlayers.remove(player);
    }

    public boolean isTP(Player player) {
        return currentPlayers.containsKey(player);
    }
}
