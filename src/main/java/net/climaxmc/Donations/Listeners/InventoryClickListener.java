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
            ParticleEffect.ParticleData particle = null;
            switch (slot) {
                case 0:
                    particle = new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.1, 2, 0.5);
                    break;
                case 1:
                    particle = new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 0, 0, 0);
                    break;
                case 2:
                    particle = new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 0, 0, 0);
                    break;
                case 3:
                    particle = new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0, 0, 0);
                    break;
                case 4:
                    particle = new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0, 0, 0);
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
            if (particle != null) {
                if (instance.getParticlesEnabled().containsKey(player.getUniqueId()) && instance.getParticlesEnabled().get(player.getUniqueId()).equals(particle)) {
                    instance.getParticlesEnabled().remove(player.getUniqueId());
                    player.sendMessage("§aYou have removed your particle!");
                } else {
                    instance.getParticlesEnabled().put(player.getUniqueId(), particle);
                    player.sendMessage("§aYou have applied the " + event.getCurrentItem().getItemMeta().getDisplayName() + " particle!");
                }
            }
        }
    }
}
