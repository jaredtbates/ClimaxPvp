package net.climaxmc.KitPvp.Menus;// AUTHOR: gamer_000 (10/29/2015)

import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DuelsMenu {

    ClimaxPvp plugin;
    private Inventory inv;
    private Inventory inv2;

    public DuelsMenu(ClimaxPvp plugin) {
        /*this.plugin = plugin;
        DuelCommand duelCommand = new DuelCommand(plugin);

        ArrayList<UUID> playersOnline = new ArrayList<UUID>();

        inv = Bukkit.getServer().createInventory(null, 54, "Select a Player to Duel");

        ItemStack cancel = new ItemStack(Material.BARRIER, 1);
        {
            ItemMeta im = cancel.getItemMeta();
            im.setDisplayName(RED + "Cancel");
            cancel.setItemMeta(im);
        }

        ItemStack nextPage = new ItemStack(Material.ARROW, 1);
        {
            ItemMeta im = nextPage.getItemMeta();
            im.setDisplayName("Next Page...");
            nextPage.setItemMeta(im);
        }

        ItemStack previousPage = new ItemStack(Material.ARROW, 1);
        {
            ItemMeta im = previousPage.getItemMeta();
            im.setDisplayName("Previous Page...");
            previousPage.setItemMeta(im);
        }

        if (plugin.getServer().getOnlinePlayers().size() <= 45) {
            int i = 0;
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                for (int p = 0; p <= plugin.getServer().getOnlinePlayers().size() - 1; p++) {
                    playersOnline.add(p++, players.getUniqueId());
                }
                PlayerData playerData = plugin.getPlayerData(players);
                if (playerData.isDueling()) {
                    return;
                } else {
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setOwner(players.getName());
                    if (playerData.getNickname() != null) {
                        meta.setDisplayName(playerData.getNickname());
                    } else {
                        meta.setDisplayName(playerData.getLevelColor() + players.getName());
                    }
                    List<String> lore = new ArrayList<>();
                    if (playerData.getNickname() != null) {
                        lore.add(WHITE + "Real Name: " + playerData.getLevelColor() + players.getName());
                        lore.add("");
                    }
                    lore.add(AQUA + "Left-click " + GRAY + "to send a duel request!");
                    if (playerData.getNickname() != null) {
                        lore.add(YELLOW + "Right-click" + GRAY + " to view all of " + playerData.getNickname() + GRAY + "'s stats!");
                    } else {
                        lore.add(YELLOW + "Right-click" + GRAY + " to view all of " + playerData.getLevelColor() + players.getName() + GRAY + "'s stats!");
                    }
                    lore.add("");
                    lore.add(DARK_GREEN + "Duel Kills: " + WHITE + playerData.getDuelKills());
                    lore.add(RED + "Duel Deaths: " + WHITE + playerData.getDuelDeaths());
                    if (playerData.getDuelDeaths() > 1) {
                        lore.add(GRAY + "Kill Death Ratio: " + WHITE + (double) (playerData.getDuelKills() / playerData.getDuelDeaths()));
                    } else {
                        lore.add(GRAY + "Kill Death Ratio: " + WHITE + (double) playerData.getDuelKills());
                    }
                    meta.setLore(lore);
                    head.setItemMeta(meta);
                    inv.setItem(i++, head);
                    inv.setItem(49, cancel);
                }
            }
        }

        if (plugin.getServer().getOnlinePlayers().size() > 45) {
            int i = 0;
            for (Player players : plugin.getServer().getOnlinePlayers()) {
                PlayerData playerData = plugin.getPlayerData(players);
                if (playerData.isDueling()) {
                    return;
                }else {
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setOwner(players.getName());
                    if (playerData.getNickname() != null) {
                        meta.setDisplayName(playerData.getNickname());
                    } else {
                        meta.setDisplayName(playerData.getLevelColor() + players.getName());
                    }
                    List<String> lore = new ArrayList<>();
                    if (playerData.getNickname() != null) {
                        lore.add(WHITE + "Real Name: " + playerData.getLevelColor() + players.getName());
                        lore.add("");
                    }
                    lore.add(AQUA + "Left-click " + GRAY + "to send a duel request!");
                    if (playerData.getNickname() != null) {
                        lore.add(YELLOW + "Right-click" + GRAY + " to view all of " + playerData.getNickname() + GRAY + "'s stats!");
                    } else {
                        lore.add(YELLOW + "Right-click" + GRAY + " to view all of " + playerData.getLevelColor() + players.getName() + GRAY + "'s stats!");
                    }
                    lore.add("");
                    lore.add(DARK_GREEN + "Duel Kills: " + WHITE + playerData.getDuelKills());
                    lore.add(RED + "Duel Deaths: " + WHITE + playerData.getDuelDeaths());
                    lore.add(GRAY + "Kill Death Ratio: " + WHITE + (playerData.getDuelKills() / playerData.getDuelDeaths()));
                    meta.setLore(lore);
                    head.setItemMeta(meta);
                    if (i <= 45) {
                        inv.setItem(i++, head);
                    } else {
                        inv2.setItem(i++, head);
                    }
                    inv.setItem(49, cancel);
                    inv.setItem(50, nextPage);
                    inv2.setItem(48, previousPage);
                }
            }
        }*/
    }

    public void openInventory(Player player) {
        player.openInventory(inv);
    }

}
