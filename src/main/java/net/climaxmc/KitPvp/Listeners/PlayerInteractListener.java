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
                ItemStack goldKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 1);
                ItemMeta goldKitsMeta = goldKits.getItemMeta();
                goldKitsMeta.setDisplayName(ChatColor.GOLD + "Gold Kits");
                goldKits.setItemMeta(goldKitsMeta);
                KitPvp.kitSelector.setItem(1, goldKits);
                KitPvp.kitSelector.setItem(7, goldKits);

                ItemStack redKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                ItemMeta redKitsMeta = redKits.getItemMeta();
                redKitsMeta.setDisplayName(ChatColor.RED + "Red Kits");
                redKits.setItemMeta(redKitsMeta);
                KitPvp.kitSelector.setItem(10, redKits);
                KitPvp.kitSelector.setItem(16, redKits);

                ItemStack limeKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                ItemMeta limeKitsMeta = limeKits.getItemMeta();
                limeKitsMeta.setDisplayName(ChatColor.GREEN + "Lime Kits");
                limeKits.setItemMeta(limeKitsMeta);
                KitPvp.kitSelector.setItem(19, limeKits);
                KitPvp.kitSelector.setItem(25, limeKits);

                ItemStack blueKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
                ItemMeta blueKitsMeta = blueKits.getItemMeta();
                blueKitsMeta.setDisplayName(ChatColor.BLUE + "Blue Kits");
                blueKits.setItemMeta(blueKitsMeta);
                KitPvp.kitSelector.setItem(28, blueKits);
                KitPvp.kitSelector.setItem(34, blueKits);

                ItemStack grayKits = new ItemStack(Material.STAINED_CLAY, 1, (short) 7);
                ItemMeta grayKitsMeta = grayKits.getItemMeta();
                grayKitsMeta.setDisplayName(ChatColor.GRAY + "Gray Kits");
                grayKits.setItemMeta(grayKitsMeta);
                KitPvp.kitSelector.setItem(37, grayKits);
                KitPvp.kitSelector.setItem(42, grayKits);

                int goldSlot = 0, redSlot = 0, limeSlot = 0, blueSlot = 0, graySlot = 0;

                for (Kit kit : KitManager.kits) {
                    if (kit.getColor().equals(ChatColor.GOLD)) {
                        KitPvp.kitSelector.setItem(++goldSlot, kit.getItem());
                    } else if (kit.getColor().equals(ChatColor.RED)) {
                        KitPvp.kitSelector.setItem(++redSlot + 10, kit.getItem());
                    } else if (kit.getColor().equals(ChatColor.GREEN)) {
                        KitPvp.kitSelector.setItem(++limeSlot + 19, kit.getItem());
                    } else if (kit.getColor().equals(ChatColor.BLUE)) {
                        KitPvp.kitSelector.setItem(++blueSlot + 28, kit.getItem());
                    } else if (kit.getColor().equals(ChatColor.GRAY)) {
                        KitPvp.kitSelector.setItem(++graySlot + 37, kit.getItem());
                    }
                }

                ItemStack moreKits = new ItemStack(Material.THIN_GLASS);
                ItemMeta moreKitsMeta = moreKits.getItemMeta();
                moreKitsMeta.setDisplayName(ChatColor.AQUA + "More Kits");
                moreKits.setItemMeta(moreKitsMeta);
                KitPvp.kitSelector.setItem(53, moreKits);

                player.openInventory(KitPvp.kitSelector);
            } else if (item.getType().equals(Material.SEEDS)) {
                new TrailsInventory(player);
            }
        }
    }
}