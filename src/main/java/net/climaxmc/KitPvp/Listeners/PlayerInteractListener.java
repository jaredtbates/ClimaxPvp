package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import net.climaxmc.KitPvp.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerInteractListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        ItemStack item = event.getItem();

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 20.0D) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                player.getInventory().getItemInHand().setType(Material.BOWL);
                player.setHealth(player.getHealth() >= 13.0D ? 20.0D : player.getHealth() + 7.0D);
            }
        }

        if (item != null) {
            if (item.getType().equals(Material.NETHER_STAR)) {
                // TODO: Clean up this code! It sucks!
                ItemStack goldKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 1);
                ItemMeta goldKitsMeta = goldKits.getItemMeta();
                goldKitsMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kits");
                goldKits.setItemMeta(goldKitsMeta);
                KitPvp.kitSelectorInventory.setItem(1, goldKits);
                KitPvp.kitSelectorInventory.setItem(7, goldKits);

                ItemStack redKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                ItemMeta redKitsMeta = redKits.getItemMeta();
                redKitsMeta.setDisplayName(ChatColor.RED + "Red Kits");
                redKits.setItemMeta(redKitsMeta);
                KitPvp.kitSelectorInventory.setItem(10, redKits);
                KitPvp.kitSelectorInventory.setItem(16, redKits);

                ItemStack greenKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                ItemMeta greenKitsMeta = greenKits.getItemMeta();
                greenKitsMeta.setDisplayName(ChatColor.GREEN + "Green Kits");
                greenKits.setItemMeta(greenKitsMeta);
                KitPvp.kitSelectorInventory.setItem(19, greenKits);
                KitPvp.kitSelectorInventory.setItem(25, greenKits);

                ItemStack blueKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
                ItemMeta blueKitsMeta = blueKits.getItemMeta();
                blueKitsMeta.setDisplayName(ChatColor.BLUE + "Blue Kits");
                blueKits.setItemMeta(blueKitsMeta);
                KitPvp.kitSelectorInventory.setItem(28, blueKits);
                KitPvp.kitSelectorInventory.setItem(34, blueKits);

                ItemStack grayKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 7);
                ItemMeta grayKitsMeta = grayKits.getItemMeta();
                grayKitsMeta.setDisplayName(ChatColor.GRAY + "Gray Kits");
                grayKits.setItemMeta(grayKitsMeta);
                KitPvp.kitSelectorInventory.setItem(37, grayKits);
                KitPvp.kitSelectorInventory.setItem(43, grayKits);

                ItemStack goldKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
                ItemMeta goldKitsGlassMeta = goldKits.getItemMeta();
                goldKitsGlassMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kit");
                goldKitsGlass.setItemMeta(goldKitsGlassMeta);
                for (int i = 2; i < 7; i++) {
                    KitPvp.kitSelectorInventory.setItem(i, goldKitsGlass);
                }

                ItemStack redKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                ItemMeta redKitsGlassMeta = goldKits.getItemMeta();
                redKitsGlassMeta.setDisplayName(ChatColor.RED + "Red Kit");
                redKitsGlass.setItemMeta(redKitsGlassMeta);
                for (int i = 11; i < 16; i++) {
                    KitPvp.kitSelectorInventory.setItem(i, redKitsGlass);
                }

                ItemStack greenKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta greenKitsGlassMeta = goldKits.getItemMeta();
                greenKitsGlassMeta.setDisplayName(ChatColor.GREEN + "Green Kit");
                greenKitsGlass.setItemMeta(greenKitsGlassMeta);
                for (int i = 20; i < 25; i++) {
                    KitPvp.kitSelectorInventory.setItem(i, greenKitsGlass);
                }

                ItemStack blueKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
                ItemMeta blueKitsGlassMeta = goldKits.getItemMeta();
                blueKitsGlassMeta.setDisplayName(ChatColor.BLUE + "Blue Kit");
                blueKitsGlass.setItemMeta(blueKitsGlassMeta);
                for (int i = 29; i < 34; i++) {
                    KitPvp.kitSelectorInventory.setItem(i, blueKitsGlass);
                }

                ItemStack grayKitsGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                ItemMeta grayKitsGlassMeta = goldKits.getItemMeta();
                grayKitsGlassMeta.setDisplayName(ChatColor.GRAY + "Gray Kit");
                grayKitsGlass.setItemMeta(grayKitsGlassMeta);
                for (int i = 38; i < 42; i++) {
                    KitPvp.kitSelectorInventory.setItem(i, grayKitsGlass);
                }

                int goldSlot = 0, redSlot = 10, limeSlot = 19, blueSlot = 28, graySlot = 37;

                for (Kit kit : KitManager.kits) {
                    if (kit.getColor().equals(ChatColor.GOLD)) {
                        if (playerData.getLevelColor().equals(ChatColor.GOLD + "" + ChatColor.BOLD)) {
                            KitPvp.kitSelectorInventory.setItem(++goldSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.RED)) {
                        if (playerData.getLevelColor().equals(ChatColor.GOLD + "" + ChatColor.BOLD) ||
                                playerData.getLevelColor().equals(ChatColor.RED + "")) {
                            KitPvp.kitSelectorInventory.setItem(++redSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.GREEN)) {
                        if (playerData.getLevelColor().equals(ChatColor.GOLD + "" + ChatColor.BOLD) ||
                                playerData.getLevelColor().equals(ChatColor.RED + "") ||
                                playerData.getLevelColor().equals(ChatColor.GREEN + "")) {
                            KitPvp.kitSelectorInventory.setItem(++limeSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.BLUE)) {
                        if (playerData.getLevelColor().equals(ChatColor.GOLD + "" + ChatColor.BOLD) ||
                                playerData.getLevelColor().equals(ChatColor.RED + "") ||
                                playerData.getLevelColor().equals(ChatColor.GREEN + "") ||
                                playerData.getLevelColor().equals(ChatColor.BLUE + "")) {
                            KitPvp.kitSelectorInventory.setItem(++blueSlot, kit.getItem());
                        }
                    } else if (kit.getColor().equals(ChatColor.GRAY)) {
                        if (playerData.getLevelColor().equals(ChatColor.GOLD + "" + ChatColor.BOLD) ||
                                playerData.getLevelColor().equals(ChatColor.RED + "") ||
                                playerData.getLevelColor().equals(ChatColor.GREEN + "") ||
                                playerData.getLevelColor().equals(ChatColor.BLUE + "") ||
                                playerData.getLevelColor().equals(ChatColor.GRAY + "")) {
                            KitPvp.kitSelectorInventory.setItem(++graySlot, kit.getItem());
                        }
                    }
                }

                ItemStack moreKits = new ItemStack(Material.THIN_GLASS);
                ItemMeta moreKitsMeta = moreKits.getItemMeta();
                moreKitsMeta.setDisplayName(ChatColor.AQUA + "More Kits");
                moreKits.setItemMeta(moreKitsMeta);
                KitPvp.kitSelectorInventory.setItem(53, moreKits);

                player.openInventory(KitPvp.kitSelectorInventory);
            } else if (item.getType().equals(Material.SEEDS)) {
                new TrailsInventory(player);
            }
        }
    }
}