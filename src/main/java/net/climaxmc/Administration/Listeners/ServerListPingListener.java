package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.UUID;

public class ServerListPingListener {

    private ClimaxPvp plugin;

    public ServerListPingListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

}
