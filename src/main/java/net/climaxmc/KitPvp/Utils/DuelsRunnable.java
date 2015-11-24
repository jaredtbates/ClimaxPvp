package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (11/20/2015)

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

    DuelsUtils duelsUtils = new DuelsUtils(plugin);

    @Override
    public void run() {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {

            amount++;
        }, 20);
    }
}
