package net.climaxmc.KitPvp.packets;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketClientKeepAliveEvent extends Event {

    private Player player;

    private static final HandlerList handlers = new HandlerList();

    public PacketClientKeepAliveEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
