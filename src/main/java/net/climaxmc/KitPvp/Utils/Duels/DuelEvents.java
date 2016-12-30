package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.KitPvp.Utils.EntityHider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DuelEvents implements Listener {
    private ClimaxPvp plugin;

    public DuelEvents(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    EntityHider entityHider = new EntityHider(ClimaxPvp.getInstance(), EntityHider.Policy.BLACKLIST);
    @EventHandler
    public void onEntitySpawn(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                if(!allPlayers.getName().equals(player.getName())) {
                    if (ClimaxPvp.inDuel.contains(player)) {
                        if (allPlayers.equals(ClimaxPvp.isDueling.get(player)) || allPlayers.equals(ClimaxPvp.isDuelingReverse.get(player))) {
                            entityHider.showEntity(allPlayers, event.getEntity());
                        } else {
                            entityHider.hideEntity(allPlayers, event.getEntity());
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPotionSplashEvent(PotionSplashEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        if (!ClimaxPvp.inDuel.contains(player)) {
            return;
        }

        Player opponent; //yas

        if (ClimaxPvp.isDueling.containsKey(player)) {
            opponent = ClimaxPvp.isDueling.get(player);
        } else if (ClimaxPvp.isDuelingReverse.containsKey(player)) {
            opponent = ClimaxPvp.isDuelingReverse.get(player);
        } else {
            return;
        }

        event.getAffectedEntities().stream().filter(entity -> entity != player && entity != opponent).forEach(entity -> event.getAffectedEntities().remove(entity));
        event.setCancelled(true);
        event.getAffectedEntities().stream().filter(entity -> entity == player || entity == opponent).forEach(entity -> entity.addPotionEffects(event.getEntity().getEffects()));

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory().getName().contains("Duel Selector")) {
            DuelUtils duelUtils = new DuelUtils(plugin);
            if (event.getCurrentItem().getType().equals(Material.POTION) && event.getCurrentItem().getItemMeta().getDisplayName().contains("NoDebuff")) {
                Player target = ClimaxPvp.initialRequest.get(player);
                duelUtils.sendRequest(player, target, "NoDebuff");
                event.setCancelled(true);
                player.closeInventory();

                ClimaxPvp.duelsKit.put(player, "NoDebuff");
                ClimaxPvp.duelsKit.put(target, "NoDebuff");
            }
            if (event.getCurrentItem().getType().equals(Material.GOLDEN_APPLE) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Gapple")) {
                Player target = ClimaxPvp.initialRequest.get(player);
                duelUtils.sendRequest(player, target, "Gapple");
                event.setCancelled(true);
                player.closeInventory();

                ClimaxPvp.duelsKit.put(player, "Gapple");
                ClimaxPvp.duelsKit.put(target, "Gapple");
            }
            if (event.getCurrentItem().getType().equals(Material.MUSHROOM_SOUP) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Soup")) {
                Player target = ClimaxPvp.initialRequest.get(player);
                duelUtils.sendRequest(player, target, "Soup");
                event.setCancelled(true);
                player.closeInventory();

                ClimaxPvp.duelsKit.put(player, "Soup");
                ClimaxPvp.duelsKit.put(target, "Soup");
            }
        }
    }
}
