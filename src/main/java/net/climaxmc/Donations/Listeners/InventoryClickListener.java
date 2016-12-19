package net.climaxmc.Donations.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Menus.DeathEffectsMenu;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.donations.trails.ParticleEffect;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

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
        if (inventory.getName().contains("Trail Settings")) {
            event.setCancelled(true);

            SettingsFiles settingsFiles = new SettingsFiles();

            for (Trail trail : Trail.values()) {
                if (event.getCurrentItem().getType().equals(trail.getMaterial())) {
                    if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                        settingsFiles.tryUnlockTrail(player, trail.getName(), trail, (int) trail.getCost());
                        player.closeInventory();
                        if (settingsFiles.isTrailUnlocked(player, trail.getName())) {
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 1);
                            if (KitPvp.inTrail.get(player.getUniqueId()) != null) {
                                if (KitPvp.inTrail.containsKey(player.getUniqueId())) {
                                    if (KitPvp.inTrail.get(player.getUniqueId()).equals(trail)) {
                                        Location location = player.getLocation();
                                        location.setY(location.getY() + trail.getYOffset());
                                        BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> new ParticleEffect(new ParticleEffect.ParticleData(trail.getData().getType(), 0, 5, 0.7)).sendToLocation(location), 1, 1);
                                        plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);
                                    }
                                }
                            }
                        }
                    } else {
                        settingsFiles.setTrail(player, trail.getName(), trail);
                        player.closeInventory();
                        if (settingsFiles.isTrailUnlocked(player, trail.getName())) {
                            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 1);
                            if (KitPvp.inTrail.get(player.getUniqueId()) != null) {
                                if (KitPvp.inTrail.containsKey(player.getUniqueId())) {
                                    if (KitPvp.inTrail.get(player.getUniqueId()).equals(trail)) {
                                        Location location = player.getLocation();
                                        location.setY(location.getY() + trail.getYOffset());
                                        BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> new ParticleEffect(new ParticleEffect.ParticleData(trail.getData().getType(), 0, 5, 0.7)).sendToLocation(location), 1, 1);
                                        plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);
                                    }
                                }
                            }
                        }
                    }

                    /*PlayerData playerData = plugin.getPlayerData(player);

                    if (!playerData.hasRank(Rank.NINJA)) {
                        player.sendMessage(ChatColor.RED + "Please donate for Ninja at https://donate.climaxmc.net for access to trails!");
                        return;
                    }

                    if (instance.getTrailsEnabled().containsKey(player.getUniqueId()) && instance.getTrailsEnabled().get(player.getUniqueId()).equals(trail)) {
                        instance.getTrailsEnabled().remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "You have removed your trail!");
                    } else {
                        instance.getTrailsEnabled().put(player.getUniqueId(), trail);
                        player.sendMessage(ChatColor.GREEN + "You have applied the " + trail.getName() + " trail!");
                    }*/
                }
            }
            if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                    settingsFiles.removeTrails(player);
                    player.closeInventory();
                }
            }
        }
    }
}
