package net.climaxmc.KitPvp.Utils.Duels;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DuelUtils {
    private ClimaxPvp plugin;

    public DuelUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Duel Selector");
        inv.setItem(3, new I(Material.POTION).durability(16421).name(ChatColor.YELLOW + "NoDebuff").clearLore());
        inv.setItem(5, new I(Material.GOLDEN_APPLE).durability(1).name(ChatColor.YELLOW + "Gapple").clearLore());
        player.openInventory(inv);
    }

    public void sendRequest(Player player, Player target, String kitName) {
        target.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getDisplayName() + ChatColor.GRAY + " sent you a duel request with " + ChatColor.GOLD + kitName);
        target.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Do /duel accept <player> to accept!");
        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Requested to duel with " + ChatColor.GOLD + target.getDisplayName() + ChatColor.GRAY + " with kit " + ChatColor.GOLD + kitName);

        ClimaxPvp.hasRequest.put(player, target);

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {

                ClimaxPvp.duelRequest.remove(player);
                ClimaxPvp.duelRequestReverse.remove(target);
                ClimaxPvp.hasRequest.remove(player);
                ClimaxPvp.duelsKit.remove(player);
                ClimaxPvp.duelsKit.remove(target);

                if (!ClimaxPvp.inDuel.contains(player)) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Your duel request to " + ChatColor.GOLD + target.getDisplayName() + ChatColor.GRAY + " has expired!");
                    target.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getDisplayName() + "'s" + ChatColor.GRAY + " duel request has expired!");
                }
            }
        }, 20L * 15);
    }

    public void acceptRequest(Player player) {
        if (ClimaxPvp.hasRequest.containsKey(player)) {
            Player target = ClimaxPvp.hasRequest.get(player);
            DuelFiles duelFiles = new DuelFiles();
            duelFiles.teleport(player, target);
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have not received a request from that player!");
        }
    }
}
