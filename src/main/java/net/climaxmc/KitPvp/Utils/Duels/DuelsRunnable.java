package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;

public class DuelsRunnable /*implements Runnable*/ {

    private ClimaxPvp plugin;
    DuelsMessages duelsMessages = new DuelsMessages(plugin);

    public DuelsRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    /*@Override
    public void run() {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> KitPvp.duels.stream().filter(Duel::isAccepted).filter(duel -> !duel.isStarted()).forEach(duel -> {
            duelsMessages.sendDuelMessage(plugin.getServer().getPlayer(duel.getPlayer1UUID()), plugin.getServer().getPlayer(duel.getPlayer2UUID()), RED + "Testing 1");
        }), 20);
    }*/
}
