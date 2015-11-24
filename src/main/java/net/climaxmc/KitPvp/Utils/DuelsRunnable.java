package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class DuelsRunnable implements Runnable {

    private ClimaxPvp plugin;
    private int amount = 0;

    public DuelsRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

            amount++;
        }, 20);
    }
}
