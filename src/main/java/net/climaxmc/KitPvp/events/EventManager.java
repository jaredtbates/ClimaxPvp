package net.climaxmc.KitPvp.events;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.events.tournament.TournamentManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EventManager {

    private ClimaxPvp plugin;
    public EventManager(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private Event runningEvent;

    public static HashMap<UUID, EventType> eventSelectionType = new HashMap<>();
    public static HashMap<UUID, Kit> kitSelectionName = new HashMap<>();

    public void startEvent(UUID host, EventType type, Kit kit) {
        if (runningEvent != null) {
            return;
        }

        runningEvent = new Event(host, type, EventState.LOBBY, kit);

        if (type.equals(EventType.TOURNAMENT)) {
            TournamentManager tournamentManager = plugin.tournamentManager;
            tournamentManager.start(host, kit);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.color("&f\u00BB &6" + Bukkit.getPlayer(host).getName() + " &7is hosting &6" + type.getName() + " &7with kit &6" + kit.getName()));
            player.sendMessage(ChatUtils.color("&7Do &b&l/event join &7to join!"));
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
        }
    }

    public boolean isRunning() {
        return runningEvent != null;
    }

    public ArrayList<UUID> getPlayers() {
        return runningEvent.getPlayers();
    }

    public ArrayList<UUID> getSpectators() {
        return runningEvent.getSpectators();
    }

    public boolean isInEvent(UUID uuid) {
        if (runningEvent != null) {
            if (runningEvent.getPlayers().contains(uuid)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<UUID> getAllPlayers() {
        ArrayList<UUID> allPlayers = new ArrayList<>();
        allPlayers.addAll(getPlayers());
        allPlayers.addAll(getSpectators());
        return allPlayers;
    }

    public void cancelEvent() {

        runningEvent = null;

        for (UUID uuid : getAllPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            plugin.respawn(player);

        }
    }

    public EventState getState() {
        if (runningEvent != null) {
            return runningEvent.getState();
        }
        return EventState.NONE;
    }

    public void setState(EventState state) {
        runningEvent.state = state;
    }

    public Event getEvent() {
        return runningEvent;
    }

    public void addPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (runningEvent != null) {
            if (runningEvent.getType().equals(EventType.TOURNAMENT)) {
                if (!runningEvent.getPlayers().contains(uuid)) {
                    TournamentManager tournamentManager = plugin.tournamentManager;
                    tournamentManager.join(uuid);
                } else {
                    player.sendMessage(ChatUtils.color("&f\u00BB &cYou are already in the event!"));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                }
            }
        } else {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThere must be an event hosted to join!"));
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }

    public void removePlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (runningEvent != null) {
            if (runningEvent.getType().equals(EventType.TOURNAMENT)) {
                if (runningEvent.getPlayers().contains(uuid)) {
                    TournamentManager tournamentManager = plugin.tournamentManager;
                    tournamentManager.leave(uuid);
                } else {
                    player.sendMessage(ChatUtils.color("&f\u00BB &cYou are not in the event!"));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                }
            }
        } else {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThere must be an event hosted to leave!"));
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }

    public void addSpectator(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (runningEvent != null) {
            if (runningEvent.getType().equals(EventType.TOURNAMENT)) {
                TournamentManager tournamentManager = plugin.tournamentManager;
                tournamentManager.spectate(uuid);
            }
        } else {
            player.sendMessage(ChatUtils.color("&f\u00BB &cThere must be an event hosted to spectate!"));
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
    }
}
