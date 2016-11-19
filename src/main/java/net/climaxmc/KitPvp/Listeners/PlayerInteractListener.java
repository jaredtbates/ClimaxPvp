package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Menus.SettingsMenu;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.WHITE;

public class PlayerInteractListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerInteractListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        ItemStack item = event.getItem();

        if (!player.getGameMode().equals(GameMode.CREATIVE) && !(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
            event.setCancelled(true);
        }

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 20.0D) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                player.getInventory().getItemInHand().setType(Material.BOWL);
                player.setHealth(player.getHealth() >= 13.0D ? 20.0D : player.getHealth() + 7.0D);
            }
        }

        if (item != null) {
            if (item.getType().equals(Material.NETHER_STAR) && player.getLocation().distance(plugin.getWarpLocation("upgrade")) > 100) {
                Inventory kitSelectorInventory = Bukkit.createInventory(null, 54, ChatColor.RED + "" + ChatColor.BOLD + "Kit Selector");

                ItemStack grayGlass = new I(Material.STAINED_GLASS_PANE).durability(15).name(" ");
                kitSelectorInventory.setItem(0, grayGlass);
                kitSelectorInventory.setItem(9, grayGlass);
                kitSelectorInventory.setItem(18, grayGlass);
                kitSelectorInventory.setItem(27, grayGlass);
                kitSelectorInventory.setItem(36, grayGlass);
                kitSelectorInventory.setItem(45, grayGlass);
                kitSelectorInventory.setItem(8, grayGlass);
                kitSelectorInventory.setItem(17, grayGlass);
                kitSelectorInventory.setItem(26, grayGlass);
                kitSelectorInventory.setItem(35, grayGlass);
                kitSelectorInventory.setItem(44, grayGlass);
                kitSelectorInventory.setItem(53, grayGlass);

                ItemStack purpleKits = new I(Material.STAINED_CLAY).durability(11).name(ChatColor.DARK_PURPLE + "Purple Kits").lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Level 2000");
                kitSelectorInventory.setItem(1, purpleKits);
                kitSelectorInventory.setItem(7, purpleKits);

                ItemStack goldKits = new I(Material.STAINED_CLAY).durability(1).name(ChatColor.GOLD + "Gold Kits").lore(ChatColor.GOLD + "" + ChatColor.ITALIC + "Level 1500");
                kitSelectorInventory.setItem(10, goldKits);
                kitSelectorInventory.setItem(16, goldKits);

                ItemStack redKits = new I(Material.STAINED_CLAY).durability(14).name(ChatColor.RED + "Red Kits").lore(ChatColor.RED + "" + ChatColor.ITALIC + "Level 1000");
                kitSelectorInventory.setItem(19, redKits);
                kitSelectorInventory.setItem(25, redKits);

                ItemStack greenKits = new I(Material.STAINED_CLAY).durability(5).name(ChatColor.GREEN + "Green Kits").lore(ChatColor.GREEN + "" + ChatColor.ITALIC + "Level 500");
                kitSelectorInventory.setItem(28, greenKits);
                kitSelectorInventory.setItem(34, greenKits);

                ItemStack blueKits = new I(Material.STAINED_CLAY).durability(3).name(ChatColor.BLUE + "Blue Kits").lore(ChatColor.BLUE + "" + ChatColor.ITALIC + "Level 150");
                kitSelectorInventory.setItem(37, blueKits);
                kitSelectorInventory.setItem(43, blueKits);

                ItemStack grayKits = new I(Material.STAINED_CLAY).durability(7).name(ChatColor.GRAY + "Gray Kits");
                kitSelectorInventory.setItem(46, grayKits);
                kitSelectorInventory.setItem(52, grayKits);

                ItemStack purpleKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(10).name(ChatColor.DARK_PURPLE + "Purple Kit").lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Unlocked at Level 2000");
                for (int i = 2; i < 7; i++) {
                    kitSelectorInventory.setItem(i, purpleKitsGlass);
                }

                ItemStack goldKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(1).name(ChatColor.GOLD + "Gold Kit").lore(ChatColor.GOLD + "" + ChatColor.ITALIC + "Unlocked at Level 1500");
                for (int i = 11; i < 16; i++) {
                    kitSelectorInventory.setItem(i, goldKitsGlass);
                }

                ItemStack redKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(14).name(ChatColor.RED + "Red Kit").lore(ChatColor.RED + "" + ChatColor.ITALIC + "Unlocked at Level 1000");
                for (int i = 20; i < 25; i++) {
                    kitSelectorInventory.setItem(i, redKitsGlass);
                }

                ItemStack greenKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(5).name(ChatColor.GREEN + "Green Kit").lore(ChatColor.GREEN + "" + ChatColor.ITALIC + "Unlocked at Level 500");
                for (int i = 29; i < 34; i++) {
                    kitSelectorInventory.setItem(i, greenKitsGlass);
                }

                ItemStack blueKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(3).name(ChatColor.BLUE + "Blue Kit").lore(ChatColor.BLUE + "" + ChatColor.ITALIC + "Unlocked at Level 150");
                for (int i = 38; i < 43; i++) {
                    kitSelectorInventory.setItem(i, blueKitsGlass);
                }

                ItemStack grayKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(7).name(ChatColor.GRAY + "Gray Kit").lore(ChatColor.GRAY + "" + ChatColor.ITALIC + "Unlocked at Level 0");
                for (int i = 47; i < 52; i++) {
                    kitSelectorInventory.setItem(i, grayKitsGlass);
                }

                int purpleSlot = 1, goldSlot = 10, redSlot = 19, limeSlot = 28, blueSlot = 37, graySlot = 46;

                for (Kit kit : KitManager.getKits()) {
                    if ((playerData.hasData("Admin Mode")
                            || playerData.getLevelColor().contains(kit.getColor() + "")
                            || (((playerData.hasRank(Rank.NINJA) && (kit.getColor().equals(ChatColor.BLUE) || kit.getColor().equals(ChatColor.GREEN)))
                            || (playerData.hasRank(Rank.TITAN) && kit.getColor().equals(ChatColor.RED))
                            || (playerData.hasRank(Rank.MASTER) && kit.getColor().equals(ChatColor.GOLD)))
                                && !playerData.hasRank(Rank.TRUSTED)))
                            || KitManager.isAllKitsEnabled()) {
                        if (kit.getColor().equals(ChatColor.DARK_PURPLE)) {
                            kitSelectorInventory.setItem(++purpleSlot, kit.getItem());
                        } else if (kit.getColor().equals(ChatColor.GOLD)) {
                            kitSelectorInventory.setItem(++goldSlot, kit.getItem());
                        } else if (kit.getColor().equals(ChatColor.RED)) {
                            kitSelectorInventory.setItem(++redSlot, kit.getItem());
                        } else if (kit.getColor().equals(ChatColor.GREEN)) {
                            kitSelectorInventory.setItem(++limeSlot, kit.getItem());
                        } else if (kit.getColor().equals(ChatColor.BLUE)) {
                            kitSelectorInventory.setItem(++blueSlot, kit.getItem());
                        } else if (kit.getColor().equals(ChatColor.GRAY)) {
                            kitSelectorInventory.setItem(++graySlot, kit.getItem());
                        }
                    }
                }
                player.openInventory(kitSelectorInventory);

            } else if (item.getType().equals(Material.SEEDS)) {
                new TrailsInventory(player);
            } else if (item.getType().equals(Material.REDSTONE)) {
                if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
                    KitManager.getPreviousKit().get(player.getUniqueId()).wearCheckLevel(player);
                }
            }
        }
        if (item != null) {
            if (item.getType().equals(Material.NETHER_STAR)) {
                if ((player.getLocation().distance(plugin.getWarpLocation("upgrade")) <= 100)) {
                    Inventory upgradeInventory = Bukkit.createInventory(null, 54, "Class Selector");

                    upgradeInventory.setItem(10, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(12, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(14, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(16, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(31, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(37, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(39, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(41, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));
                    upgradeInventory.setItem(43, new I(Material.STAINED_GLASS_PANE).durability(0).name(" "));

                    upgradeInventory.setItem(2, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(6, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(20, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(24, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(29, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(33, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(47, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));
                    upgradeInventory.setItem(51, new I(Material.STAINED_GLASS_PANE).durability(7).name(" "));

                    ItemStack fighter = new I(Material.DIAMOND_SWORD).name("§c§lFighter").lore("§7Fighters use hand-to-hand combat and ")
                            .lore("§7light but durable armor to ")
                            .lore("§7defeat their opponents.")
                            .lore(" ")
                            .lore("§eUpgrades §6(Tier 1 §e- §6Tier 4):")
                            .lore("§cArmor: §7Full Chain §f- §7Full Iron")
                            .lore("§cSword: §7Iron §f- §7Diamond")
                            .lore("§cAbilities: §7None §f- §73");
                    upgradeInventory.setItem(11, fighter);

                    player.openInventory(upgradeInventory);
                }
            }
        }
        if (player.getItemInHand().getType().equals(Material.BOOK)) {
            if (player.getGameMode().equals(GameMode.CREATIVE) && ClimaxPvp.deadPeoples.contains(player)) {
                ClimaxPvp.deadPeoples.remove(player);

                plugin.respawn(player);

                if (player.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {
                    new PvpKit().wearCheckLevel(player);
                }
                player.setAllowFlight(false);
                player.setFlying(false);
                for(Player players : Bukkit.getServer().getOnlinePlayers()){
                    players.showPlayer(player);
                }
                Bukkit.getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
                    }
                }, 1);
            }
        }
        if (player.getItemInHand().getType().equals(Material.WATCH)) {
            if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Settings")) {
                SettingsMenu settingsMenu = new SettingsMenu(plugin);
                settingsMenu.openInventory(player);
            }
        }
    }
}