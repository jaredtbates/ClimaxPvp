package net.climaxmc.KitPvp.Utils.DeathEffects;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Menus.DeathEffectsMenu;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffectFiles;
import net.climaxmc.common.donations.trails.ParticleEffect;
import net.climaxmc.common.donations.trails.Trail;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

public class DeathEffectEvents implements Listener {

    private ClimaxPvp plugin;

    public DeathEffectEvents(ClimaxPvp plugin) {this.plugin = plugin;}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (event.getSlotType() == null || event.getCurrentItem() == null || event.getCurrentItem().getType() == null) {
                return;
            }
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                if (inventory.getName().equals("\u00A78\u00A7lDeath Effects")) {
                    event.setCancelled(true);

                    DeathEffectFiles deathEffectFiles = new DeathEffectFiles();

                    for (DeathEffect effect : DeathEffect.values()) {
                        if (event.getCurrentItem().getType().equals(effect.getMaterial())) {
                            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                                deathEffectFiles.tryUnlockEffect(player, effect, (int) effect.getCost());
                                player.closeInventory();
                                if (deathEffectFiles.isEffectUnlocked(player, effect)) {
                                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 1);
                                    if (ClimaxPvp.inEffect.get(player) != null) {
                                        if (ClimaxPvp.inEffect.containsKey(player)) {
                                            if (ClimaxPvp.inEffect.get(player).equals(effect.getName())) {
                                                ClimaxPvp.playerDeathEffect.put(player.getUniqueId(), effect);
                                                deathEffectFiles.setCurrentEffect(player, effect);
                                            }
                                        }
                                    }
                                }
                            } else {
                                deathEffectFiles.setEffect(player, effect);
                                player.closeInventory();
                                if (deathEffectFiles.isEffectUnlocked(player, effect)) {
                                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, 1);
                                    if (ClimaxPvp.inEffect.get(player) != null) {
                                        if (ClimaxPvp.inEffect.containsKey(player)) {
                                            if (ClimaxPvp.inEffect.get(player).equals(effect.getName())) {
                                                ClimaxPvp.playerDeathEffect.put(player.getUniqueId(), effect);
                                                deathEffectFiles.setCurrentEffect(player, effect);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                            deathEffectFiles.removeEffect(player);
                            deathEffectFiles.removeCurrentEffect(player);
                            player.closeInventory();
                        }
                    }
                }
                if (inventory.getName().contains("\u00A78\u00A7lCosmetics")) {
                    if (event.getCurrentItem() != null) {
                        if (event.getCurrentItem().getType().equals(Material.BLAZE_POWDER)) {
                            DeathEffectsMenu deathEffectsMenu = new DeathEffectsMenu(ClimaxPvp.getInstance());
                            deathEffectsMenu.openInventory(player);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DeathEffectFiles deathEffectFiles = new DeathEffectFiles();
        if (deathEffectFiles.getCurrentEffect(player) != null) {
            for (DeathEffect effect : DeathEffect.values()) {
                if (effect.getName().equals(deathEffectFiles.getCurrentEffect(player))) {
                    ClimaxPvp.playerDeathEffect.put(player.getUniqueId(), effect);
                }
            }
        }
    }
}
