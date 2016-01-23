package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class EntityDamageByEntityListener implements Listener {
    private ClimaxPvp plugin;

    public EntityDamageByEntityListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        if ((KitPvp.currentTeams.containsKey(player.getName())
                && KitPvp.currentTeams.get(player.getName()).equals(target.getName()))
                || (KitPvp.currentTeams.containsKey(target.getName())
                && KitPvp.currentTeams.get(target.getName()).equals(player.getName()))) {
            event.setCancelled(true);
        }
    }
}
