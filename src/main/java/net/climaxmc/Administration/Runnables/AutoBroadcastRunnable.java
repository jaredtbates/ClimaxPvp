package net.climaxmc.Administration.Runnables;

import net.climaxmc.ClimaxPvp;

import java.util.List;

public class AutoBroadcastRunnable implements Runnable {
    private ClimaxPvp plugin;
    private List<String> messages;

    public AutoBroadcastRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
        this.messages = plugin.getConfig().getStringList("AutoBroadcast.Messages");
    }

    @Override
    public void run() {

    }
}
