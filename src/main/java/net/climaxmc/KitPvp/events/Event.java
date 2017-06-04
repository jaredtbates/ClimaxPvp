package net.climaxmc.KitPvp.events;

import net.climaxmc.KitPvp.Kit;

import java.util.ArrayList;
import java.util.UUID;

public class Event {

    private UUID host;
    private EventType type;
    public EventState state;
    private Kit kit;
    private ArrayList<UUID> players = new ArrayList<>();
    private ArrayList<UUID> spectators = new ArrayList<>();

    public Event(UUID host, EventType type, EventState state, Kit kit) {
        this.type = type;
        this.host = host;
        this.state = state;
        this.kit = kit;
    }

    public UUID getHost() {
        return host;
    }
    public EventType getType() {
        return type;
    }
    public EventState getState() {
        return state;
    }
    public Kit getKit() {
        return kit;
    }
    public ArrayList<UUID> getPlayers() {
        return players;
    }
    public ArrayList<UUID> getSpectators() {
        return spectators;
    }
}
