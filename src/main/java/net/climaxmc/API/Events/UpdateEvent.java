package net.climaxmc.API.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return UpdateEvent.handlers;
    }

    public HandlerList getHandlers() {
        return UpdateEvent.handlers;
    }
}
