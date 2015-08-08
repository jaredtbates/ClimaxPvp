package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Menus.ChallengesMenu;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInteractListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerInteractListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CachedPlayerData playerData = plugin.getPlayerData(player);
        ItemStack item = event.getItem();

        if(!player.isOp()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().equals(Material.TRAP_DOOR)) {
                    event.setCancelled(true);
                }
        }

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 20.0D) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                player.getInventory().getItemInHand().setType(Material.BOWL);
                player.setHealth(player.getHealth() >= 13.0D ? 20.0D : player.getHealth() + 7.0D);
            }
        }

        if (item != null) {
            if (item.getType().equals(Material.NETHER_STAR)) {
                Inventory kitSelectorInventory = Bukkit.createInventory(null, 54, ChatColor.RED + "" + ChatColor.BOLD + "Kit Selector");

                // TODO: Clean up this code! It sucks!
                ItemStack goldKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 1);
                ItemMeta goldKitsMeta = goldKits.getItemMeta();
                goldKitsMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kits");
                goldKits.setItemMeta(goldKitsMeta);
                kitSelectorInventory.setItem(1, goldKits);
                kitSelectorInventory.setItem(7, goldKits);

                ItemStack redKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                ItemMeta redKitsMeta = redKits.getItemMeta();
                redKitsMeta.setDisplayName(ChatColor.RED + "Red Kits");
                redKits.setItemMeta(redKitsMeta);
                kitSelectorInventory.setItem(10, redKits);
                kitSelectorInventory.setItem(16, redKits);

                ItemStack greenKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                ItemMeta greenKitsMeta = greenKits.getItemMeta();
                greenKitsMeta.setDisplayName(ChatColor.GREEN + "Green Kits");
                greenKits.setItemMeta(greenKitsMeta);
                kitSelectorInventory.setItem(19, greenKits);
                kitSelectorInventory.setItem(25, greenKits);

                ItemStack blueKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
                ItemMeta blueKitsMeta = blueKits.getItemMeta();
                blueKitsMeta.setDisplayName(ChatColor.BLUE + "Blue Kits");
                blueKits.setItemMeta(blueKitsMeta);
                kitSelectorInventory.setItem(28, blueKits);
                kitSelectorInventory.setItem(34, blueKits);

                ItemStack grayKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 7);
                ItemMeta grayKitsMeta = grayKits.getItemMeta();
                grayKitsMeta.setDisplayName(ChatColor.GRAY + "Gray Kits");
                grayKits.setItemMeta(grayKitsMeta);
                kitSelectorInventory.setItem(37, grayKits);
                kitSelectorInventory.setItem(43, grayKits);

                ItemStack goldKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
                ItemMeta goldKitsGlassMeta = goldKits.getItemMeta();
                goldKitsGlassMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kit");
                goldKitsGlassMeta.setLore(Collections.singletonList(ChatColor.GOLD + "" + ChatColor.ITALIC + "Unlocked at Level 1100"));
                goldKitsGlass.setItemMeta(goldKitsGlassMeta);
                for (int i = 2; i < 7; i++) {
                    kitSelectorInventory.setItem(i, goldKitsGlass);
                }

                ItemStack redKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                ItemMeta redKitsGlassMeta = goldKits.getItemMeta();
                redKitsGlassMeta.setDisplayName(ChatColor.RED + "Red Kit");
                redKitsGlassMeta.setLore(Collections.singletonList(ChatColor.RED + "" + ChatColor.ITALIC + "Unlocked at Level 700"));
                redKitsGlass.setItemMeta(redKitsGlassMeta);
                for (int i = 11; i < 16; i++) {
                    kitSelectorInventory.setItem(i, redKitsGlass);
                }

                ItemStack greenKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta greenKitsGlassMeta = goldKits.getItemMeta();
                greenKitsGlassMeta.setDisplayName(ChatColor.GREEN + "Green Kit");
                greenKitsGlassMeta.setLore(Collections.singletonList(ChatColor.GREEN + "" + ChatColor.ITALIC + "Unlocked at Level 400"));
                greenKitsGlass.setItemMeta(greenKitsGlassMeta);
                for (int i = 20; i < 25; i++) {
                    kitSelectorInventory.setItem(i, greenKitsGlass);
                }

                ItemStack blueKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
                ItemMeta blueKitsGlassMeta = goldKits.getItemMeta();
                blueKitsGlassMeta.setDisplayName(ChatColor.BLUE + "Blue Kit");
                blueKitsGlassMeta.setLore(Collections.singletonList(ChatColor.BLUE + "" + ChatColor.ITALIC + "Unlocked at Level 110"));
                blueKitsGlass.setItemMeta(blueKitsGlassMeta);
                for (int i = 29; i < 34; i++) {
                    kitSelectorInventory.setItem(i, blueKitsGlass);
                }

                ItemStack grayKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                ItemMeta grayKitsGlassMeta = goldKits.getItemMeta();
                grayKitsGlassMeta.setDisplayName(ChatColor.GRAY + "Gray Kit");
                grayKitsGlassMeta.setLore(Collections.singletonList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Unlocked at Level 0"));
                grayKitsGlass.setItemMeta(grayKitsGlassMeta);
                for (int i = 38; i < 42; i++) {
                    kitSelectorInventory.setItem(i, grayKitsGlass);
                }

                int goldSlot = 1, redSlot = 10, limeSlot = 19, blueSlot = 28, graySlot = 37;

                for (Kit kit : KitManager.getKits()) {
                    if (kit.getColor().equals(ChatColor.GOLD)) {
                        if (playerData.hasData("Admin Mode") ||
                                playerData.getLevelColor().contains(kit.getColor() + "")) {
                            kitSelectorInventory.setItem(++goldSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.RED)) {
                        if (playerData.hasData("Admin Mode") ||
                                playerData.getLevelColor().contains(kit.getColor() + "")) {
                            kitSelectorInventory.setItem(++redSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.GREEN)) {
                        if (playerData.hasData("Admin Mode") ||
                                playerData.getLevelColor().contains(kit.getColor() + "")) {
                            kitSelectorInventory.setItem(++limeSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.BLUE)) {
                        if (playerData.hasData("Admin Mode") ||
                                playerData.getLevelColor().contains(kit.getColor() + "")) {
                            kitSelectorInventory.setItem(++blueSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.GRAY)) {
                        if (playerData.hasData("Admin Mode") ||
                                playerData.getLevelColor().contains(kit.getColor() + "")) {
                            kitSelectorInventory.setItem(++graySlot, kit.getItem());
                        }
                    }
                }

                ItemStack moreKits = new ItemStack(Material.THIN_GLASS);
                ItemMeta moreKitsMeta = moreKits.getItemMeta();
                moreKitsMeta.setDisplayName(ChatColor.AQUA + "More Kits " + ChatColor.ITALIC + "(Currently Unavailable)");
                moreKits.setItemMeta(moreKitsMeta);
                kitSelectorInventory.setItem(53, moreKits);

                player.openInventory(kitSelectorInventory);
            } else if (item.getType().equals(Material.SEEDS)) {
                new TrailsInventory(player);
            } else if (item.getType().equals(Material.REDSTONE)) {
                if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
                    KitManager.getPreviousKit().get(player.getUniqueId()).wearCheckLevel(player);
                }
            }
        }
    }
}