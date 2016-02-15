package net.climaxmc.KitPvp.Menus.PlayerProfile;// AUTHOR: gamer_000 (11/21/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class PlayerProfileMenu implements Listener {
    private ClimaxPvp plugin;

    public PlayerProfileMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
    {
        ItemMeta im = gold.getItemMeta();
        im.setDisplayName(GREEN + "The Bank");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "Check out the amount of");
        lore.add(GRAY + "cosmetic currency you have!");
        im.setLore(lore);
        gold.setItemMeta(im);
    }

    ItemStack iron = new ItemStack(Material.IRON_INGOT, 1);
    {
        ItemMeta im = iron.getItemMeta();
        im.setDisplayName(GREEN + "Achievements");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "See your progress in completing");
        lore.add(GRAY + "all the Achievements!");
        im.setLore(lore);
        iron.setItemMeta(im);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        PlayerData playerData = plugin.getPlayerData(player);

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player.getName());
        meta.setDisplayName(playerData.getLevelColor() + player.getName());
        ArrayList<String> lore = new ArrayList<>();
        if (playerData.getNickname() != null) {
            lore.add(WHITE + "Nickname: " + playerData.getNickname());
            lore.add("");
        }
        lore.add(YELLOW + "" + UNDERLINE + "                 STATS                 ");
        lore.add("");
        lore.add(WHITE + "" + BOLD + "Total Kills: " + ChatColor.GREEN + ChatColor.BOLD + (playerData.getKills() + playerData.getDuelKills()));
        lore.add(GRAY + "   Regular Kills: " + playerData.getKills());
        lore.add(GRAY + "   Duel Kills: " + playerData.getDuelKills());
        lore.add(WHITE + "" + BOLD + "Total Deaths: " + ChatColor.RED + ChatColor.BOLD + (playerData.getDeaths() + playerData.getDuelDeaths()));
        lore.add(GRAY + "   Regular Deaths: " + playerData.getDeaths());
        lore.add(GRAY + "   Duel Deaths: " + playerData.getDuelDeaths());
        lore.add("");
        lore.add(YELLOW + "" + UNDERLINE + "                  KDR's                 ");
        lore.add("");
        lore.add(AQUA + "REGULAR   " + LIGHT_PURPLE + "  DUELS   " + GOLD + "  TOTAL");
        lore.add(AQUA + "" + getRegularRatio(playerData) + "      " + LIGHT_PURPLE + "        "
                + getDuelsRatio(playerData) + "   " + GOLD + "        " + getTotalRatio(playerData));
        lore.add("");
        lore.add(GREEN + "Click to get a link to the forums!");
        meta.setLore(lore);
        head.setItemMeta(meta);

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (item != null) {
            if (item.getType().equals(Material.SKULL_ITEM)) {

                Inventory profileMenu = Bukkit.createInventory(null, 27, "Profile Menu");
                profileMenu.setItem(11, iron);
                profileMenu.setItem(12, gold);
                profileMenu.setItem(13, head);

                player.openInventory(profileMenu);
            }
        }
    }

    public static float getRegularRatio(PlayerData playerData) {
        float kills = playerData.getKills();
        float deaths = playerData.getDeaths();
        float ratio;
        if ((kills == 0.00F) && (deaths == 0.00F)) {
            ratio = 0.00F;
        } else {
            if ((kills > 0.00F) && (deaths == 0.00F)) {
                ratio = kills;
            } else {
                if ((deaths > 0.00) && (kills == 0.00F)) {
                    ratio = 0.00F;
                } else {
                    ratio = kills / deaths;
                }
            }
        }
        ratio = Math.round(ratio * 100.00F) / 100.00F;
        return ratio;
    }

    public static float getDuelsRatio(PlayerData playerData) {
        float kills = playerData.getDuelKills();
        float deaths = playerData.getDuelDeaths();
        float ratio;
        if ((kills == 0.00F) && (deaths == 0.00F)) {
            ratio = 0.00F;
        } else {
            if ((kills > 0.00F) && (deaths == 0.00F)) {
                ratio = kills;
            } else {
                if ((deaths > 0.00F) && (kills == 0.00F)) {
                    ratio = 0.00F;
                } else {
                    ratio = kills / deaths;
                }
            }
        }
        ratio = Math.round(ratio * 100.00F) / 100.00F;
        return ratio;
    }
    public static float getTotalRatio(PlayerData playerData) {
        float kills = playerData.getKills() + playerData.getDuelKills();
        float deaths = playerData.getDeaths() + playerData.getDuelDeaths();
        float ratio;
        if ((kills == 0.00F) && (deaths == 0.00F)) {
            ratio = 0.00F;
        } else {
            if ((kills > 0.00F) && (deaths == 0.00F)) {
                ratio = kills;
            } else {
                if ((deaths > 0.00F) && (kills == 0.00F)) {
                    ratio = 0.00F;
                } else {
                    ratio = kills / deaths;
                }
            }
        }
        ratio = Math.round(ratio * 100.00F) / 100.00F;
        return ratio;
    }


}
