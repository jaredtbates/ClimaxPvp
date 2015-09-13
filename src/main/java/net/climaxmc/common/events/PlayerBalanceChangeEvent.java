package net.climaxmc.common.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlayerBalanceChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private OfflinePlayer player;

    public PlayerBalanceChangeEvent(UUID uuid) {
        this.player = Bukkit.getOfflinePlayer(uuid);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
