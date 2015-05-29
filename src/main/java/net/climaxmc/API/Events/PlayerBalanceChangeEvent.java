package net.climaxmc.API.Events;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerBalanceChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private OfflinePlayer player;

    public PlayerBalanceChangeEvent(OfflinePlayer player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
