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
            ParticleEffect.ParticleType type = null;
            double speed = 0;
            double count = 0;
            double radius = 0;
            switch (slot) {
                case 0:
                    type = ParticleEffect.ParticleType.EXPLOSION_NORMAL;
                    break;
                case 1:
                    type = ParticleEffect.ParticleType.LAVA;
                    break;
                case 2:
                    type = ParticleEffect.ParticleType.DRIP_WATER;
                    break;
                case 3:
                    type = ParticleEffect.ParticleType.ENCHANTMENT_TABLE;
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    break;
                case 16:
                    break;
                case 17:
                    break;
                case 18:
                    break;
                case 19:
                    break;
                case 20:
                    break;
            }
            if (instance.getParticlesEnabled().containsKey(player.getUniqueId()) && instance.getParticlesEnabled().get(player.getUniqueId()).equals(type)) {
                instance.getParticlesEnabled().remove(player.getUniqueId());
                player.sendMessage("§aYou have removed your particle!");
            } else {
                instance.getParticlesEnabled().put(player.getUniqueId(), type);
                player.sendMessage("§aYou have applied the " + event.getCurrentItem().getItemMeta().getDisplayName() + " particle!");
            }
        }
    }
}
