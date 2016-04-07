package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Inventories.TrailsInventory;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                player.getInventory().getItemInHand().setType(Material.BOWL);
                player.setHealth(player.getHealth() >= 13.0D ? 20.0D : player.getHealth() + 7.0D);
            }
        }

        if (item != null) {
            if (item.getType().equals(Material.NETHER_STAR)) {
                Inventory kitSelectorInventory = Bukkit.createInventory(null, 54, ChatColor.RED + "" + ChatColor.BOLD + "Kit Selector");

                ItemStack goldKits = new I(Material.STAINED_CLAY).durability(1).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kits").lore("Level 1500");
                kitSelectorInventory.setItem(1, goldKits);
                kitSelectorInventory.setItem(7, goldKits);

                ItemStack redKits = new I(Material.STAINED_CLAY).durability(14).name(ChatColor.RED + "Red Kits").lore("Level 1000");
                kitSelectorInventory.setItem(10, redKits);
                kitSelectorInventory.setItem(16, redKits);

                ItemStack greenKits = new I(Material.STAINED_CLAY).durability(5).name(ChatColor.GREEN + "Green Kits").lore("Level 500");
                kitSelectorInventory.setItem(19, greenKits);
                kitSelectorInventory.setItem(25, greenKits);

                ItemStack blueKits = new I(Material.STAINED_CLAY).durability(3).name(ChatColor.BLUE + "Blue Kits").lore("Level 150");
                kitSelectorInventory.setItem(28, blueKits);
                kitSelectorInventory.setItem(34, blueKits);

                ItemStack grayKits = new I(Material.STAINED_CLAY).durability(7).name(ChatColor.GRAY + "Gray Kits");
                kitSelectorInventory.setItem(37, grayKits);
                kitSelectorInventory.setItem(43, grayKits);

                ItemStack goldKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(1).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Gold Kit").lore(ChatColor.GOLD + "" + ChatColor.ITALIC + "Unlocked at Level 1500");
                for (int i = 2; i < 7; i++) {
                    kitSelectorInventory.setItem(i, goldKitsGlass);
                }

                ItemStack redKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(14).name(ChatColor.RED + "Red Kit").lore(ChatColor.RED + "" + ChatColor.ITALIC + "Unlocked at Level 1000");
                for (int i = 11; i < 16; i++) {
                    kitSelectorInventory.setItem(i, redKitsGlass);
                }

                ItemStack greenKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(5).name(ChatColor.GREEN + "Green Kit").lore(ChatColor.GREEN + "" + ChatColor.ITALIC + "Unlocked at Level 500");
                for (int i = 20; i < 25; i++) {
                    kitSelectorInventory.setItem(i, greenKitsGlass);
                }

                ItemStack blueKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(3).name(ChatColor.BLUE + "Blue Kit").lore(ChatColor.BLUE + "" + ChatColor.ITALIC + "Unlocked at Level 150");
                for (int i = 29; i < 34; i++) {
                    kitSelectorInventory.setItem(i, blueKitsGlass);
                }

                ItemStack grayKitsGlass = new I(Material.STAINED_GLASS_PANE).durability(7).name(ChatColor.GRAY + "Gray Kit").lore(ChatColor.GRAY + "" + ChatColor.ITALIC + "Unlocked at Level 0");
                for (int i = 38; i < 42; i++) {
                    kitSelectorInventory.setItem(i, grayKitsGlass);
                }

                int goldSlot = 1, redSlot = 10, limeSlot = 19, blueSlot = 28, graySlot = 37;

                for (Kit kit : KitManager.getKits()) {
                    if ((playerData.hasData("Admin Mode")
                            || playerData.getLevelColor().contains(kit.getColor() + "")
                            || (((playerData.hasRank(Rank.NINJA) && (kit.getColor().equals(ChatColor.BLUE) || kit.getColor().equals(ChatColor.GREEN)))
                            || (playerData.hasRank(Rank.TITAN) && kit.getColor().equals(ChatColor.RED))
                            || (playerData.hasRank(Rank.MASTER) && kit.getColor().equals(ChatColor.GOLD)))
                                && !playerData.hasRank(Rank.TRUSTED)))
                            || KitManager.isAllKitsEnabled()) {
                        if (kit.getColor().equals(ChatColor.GOLD)) {
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
    }
}