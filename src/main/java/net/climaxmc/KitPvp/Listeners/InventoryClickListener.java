package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.*;
import net.climaxmc.KitPvp.Menus.ChallengesMenu;
import net.climaxmc.KitPvp.Utils.Challenge;
import net.climaxmc.KitPvp.Utils.ChallengesFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    private ClimaxPvp plugin;

    public InventoryClickListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();
        if (inventory != null && event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
            if (inventory.getName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Kit Selector")) {
                for (Kit kit : KitManager.getKits()) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(kit.getItem().getItemMeta().getDisplayName())) {
                        kit.wearCheckLevel(player);
                        plugin.getServer().getScheduler().runTask(plugin, player::closeInventory);
                    }
                    event.setCancelled(true);
                }
            }

            // TODO: This code can probably be cleaned up a bit -Bert
            if(inventory.getName().equals("Challenges")) {
                ChallengesFiles challengesFiles = new ChallengesFiles();
                ItemStack clickedItem = event.getCurrentItem();
                ChallengesMenu challengesMenu = new ChallengesMenu();
                if(clickedItem.getType().equals(Material.MAP)) {
                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Daily Challenge #1")) {
                        challengesFiles.setStarted(player, Challenge.Daily1);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "You've started " + ChatColor.AQUA + "Daily Challenge #1");
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Daily Challenge #2")) {
                        challengesFiles.setStarted(player, Challenge.Daily2);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "You've started " + ChatColor.AQUA + "Daily Challenge #2");
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Daily Challenge #3")) {
                        challengesFiles.setStarted(player, Challenge.Daily3);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "You've started " + ChatColor.AQUA + "Daily Challenge #3");
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Daily Challenge #4")) {
                        challengesFiles.setStarted(player, Challenge.Daily4);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "You've started " + ChatColor.AQUA + "Daily Challenge #4");
                    }
                    if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Weekly Challenge #1")) {
                        challengesFiles.setStarted(player, Challenge.Weekly1);
                        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "You've started " + ChatColor.AQUA + "Weekly Challenge #1");
                    }
                    if (clickedItem.getEnchantments() != null) {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    }
                }

                if(clickedItem.getType().equals(Material.PAPER)) {
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                        long cooldown = challengesFiles.getChallenge(clickedItem.getItemMeta().getDisplayName()).getCooldownTime()
                                - ((System.currentTimeMillis() / 1000)
                                - (challengesFiles.getStartTime(player, challengesFiles.getChallenge(clickedItem.getItemMeta().getDisplayName()))));
                    if (cooldown >= 86400) {
                        cooldown = ((cooldown / 60) / 60) / 24;
                        if(cooldown == 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " day to start this challenge again!");
                        if(cooldown > 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " days to start this challenge again!");
                    }
                    else if(cooldown >= 3600) {
                        cooldown = (cooldown / 60) / 60;
                        if(cooldown == 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " hour to start this challenge again!");
                        if(cooldown > 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " hours to start this challenge again!");
                    }
                    else if(cooldown >= 60) {
                        cooldown = cooldown / 60;
                        if(cooldown == 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " minute to start this challenge again!");
                        if(cooldown > 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " minutes to start this challenge again!");
                    } else {
                        if(cooldown == 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " second to start this challenge again!");
                        if(cooldown > 1) player.sendMessage(ChatColor.RED + "Come back in " + cooldown + " seconds to start this challenge again!");
                    }
                }

                event.setCancelled(true);
            }
        }
    }
}