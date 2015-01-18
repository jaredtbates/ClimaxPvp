package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Utils.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class InventoryClickListener implements Listener {
    private ClimaxPvp plugin;
    private Donations instance;

    public InventoryClickListener(ClimaxPvp plugin, Donations instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        if (inventory.getName().equals("§a§lParticle Settings")) {
            int slot = event.getSlot();
            event.setCancelled(true);
            player.closeInventory();
            switch (slot) {
                case 0:
                    if (instance.getParticlesEnabled().containsKey(player.getUniqueId())) {
                        instance.getParticlesEnabled().remove(player.getUniqueId());
                    } else {
                        instance.getParticlesEnabled().put(player.getUniqueId(), ParticleEffect.ParticleType.EXPLOSION_NORMAL);
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
