package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    private ClimaxPvp plugin;

    public FoodLevelChangeListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        event.setCancelled(true);
        if (ClimaxPvp.inDuel.contains(player)) {
            return;
        } else {
            if (player.getLocation().distance(player.getWorld().getSpawnLocation()) <= 250) {
                player.setFoodLevel(20);
            } else {
                player.setFoodLevel(20);
            }
        }
    }
}