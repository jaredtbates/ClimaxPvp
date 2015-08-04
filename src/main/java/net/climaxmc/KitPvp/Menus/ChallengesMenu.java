package net.climaxmc.KitPvp.Menus;// AUTHOR: gamer_000 (8/3/2015)

import net.climaxmc.KitPvp.Utils.Challenges;
import net.climaxmc.KitPvp.Utils.ChallengesFiles;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChallengesMenu implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (item != null) {
            if (item.getType().equals(Material.DIAMOND)) {
                ChallengesMenu challengesMenu = new ChallengesMenu();
                ChallengesFiles challengesFiles = new ChallengesFiles();
                Inventory challengesInventory = Bukkit.createInventory(null, 27, "Challenges");

                final ItemStack c1 = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c1.getItemMeta();
                    im.setDisplayName(ChatColor.AQUA + "Daily Challenge #1");
                    List<String> c1lore = new ArrayList<String>();
                    c1lore.add(ChatColor.GRAY + "Kill 20 players");
                    c1lore.add("");
                    c1lore.add(ChatColor.AQUA + "Ready to Start!");
                    im.setLore(c1lore);
                    c1.setItemMeta(im);
                }
                ItemStack c1started = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c1started.getItemMeta();
                    im.setDisplayName(ChatColor.GREEN + "Daily Challenge #1");
                    List<String> c1startedlore = new ArrayList<String>();
                    c1startedlore.add(ChatColor.GRAY + "Kill 20 players");
                    c1startedlore.add("");
                    c1startedlore.add(ChatColor.RED + "In Progress...");
                    c1startedlore.add(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily1) + "/20");
                    im.setLore(c1startedlore);
                    c1started.setItemMeta(im);
                    c1started.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                }
                ItemStack c1completed = new ItemStack(Material.PAPER, 1);
                {
                    ItemMeta im = c1completed.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "Daily Challenge #1");
                    List<String> c1completedlore = new ArrayList<String>();
                    c1completedlore.add(ChatColor.GRAY + "Kill 20 players");
                    c1completedlore.add("");
                    c1completedlore.add(ChatColor.GREEN + "Complete!");
                    c1completedlore.add(ChatColor.RED + "Come back later!");
                    im.setLore(c1completedlore);
                    c1completed.setItemMeta(im);
                }
                ItemStack c2 = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c2.getItemMeta();
                    im.setDisplayName(ChatColor.AQUA + "Daily Challenge #2");
                    List<String> c2lore = new ArrayList<String>();
                    c2lore.add(ChatColor.GRAY + "Kill 75 players");
                    c2lore.add("");
                    c2lore.add(ChatColor.AQUA + "Ready to Start!");
                    im.setLore(c2lore);
                    c2.setItemMeta(im);
                }
                ItemStack c2started = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c2started.getItemMeta();
                    im.setDisplayName(ChatColor.GREEN + "Daily Challenge #2");
                    List<String> c2startedlore = new ArrayList<String>();
                    c2startedlore.add(ChatColor.GRAY + "Kill 75 players");
                    c2startedlore.add("");
                    c2startedlore.add(ChatColor.RED + "In Progress...");
                    c2startedlore.add(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily2) + "/75");
                    im.setLore(c2startedlore);
                    c2started.setItemMeta(im);
                    c2started.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
                }
                ItemStack c2completed = new ItemStack(Material.PAPER, 1);
                {
                    ItemMeta im = c2completed.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "Daily Challenge #2");
                    List<String> c2completedlore = new ArrayList<String>();
                    c2completedlore.add(ChatColor.GRAY + "Kill 75 players");
                    c2completedlore.add("");
                    c2completedlore.add(ChatColor.GREEN + "Complete!");
                    c2completedlore.add(ChatColor.RED + "Come back later!");
                    im.setLore(c2completedlore);
                    c2completed.setItemMeta(im);
                }
                ItemStack c3 = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c3.getItemMeta();
                    im.setDisplayName(ChatColor.AQUA + "Daily Challenge #3");
                    List<String> c3lore = new ArrayList<String>();
                    c3lore.add(ChatColor.GRAY + "Kill 150 players");
                    c3lore.add("");
                    c3lore.add(ChatColor.AQUA + "Ready to Start!");
                    im.setLore(c3lore);
                    c3.setItemMeta(im);
                }
                ItemStack c3started = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c3started.getItemMeta();
                    im.setDisplayName(ChatColor.GREEN + "Daily Challenge #3");
                    List<String> c3startedlore = new ArrayList<String>();
                    c3startedlore.add(ChatColor.GRAY + "Kill 150 players");
                    c3startedlore.add("");
                    c3startedlore.add(ChatColor.RED + "In Progress...");
                    c3startedlore.add(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily3) + "/150");
                    im.setLore(c3startedlore);
                    c3started.setItemMeta(im);
                    c3started.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
                }
                ItemStack c3completed = new ItemStack(Material.PAPER, 1);
                {
                    ItemMeta im = c3completed.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "Daily Challenge #3");
                    List<String> c3completedlore = new ArrayList<String>();
                    c3completedlore.add(ChatColor.GRAY + "Kill 150 players");
                    c3completedlore.add("");
                    c3completedlore.add(ChatColor.GREEN + "Complete!");
                    c3completedlore.add(ChatColor.RED + "Come back later!");
                    im.setLore(c3completedlore);
                    c3completed.setItemMeta(im);
                }
                ItemStack c4 = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c4.getItemMeta();
                    im.setDisplayName(ChatColor.AQUA + "Daily Challenge #4");
                    List<String> c4lore = new ArrayList<String>();
                    c4lore.add(ChatColor.GRAY + "Kill 200 players");
                    c4lore.add("");
                    c4lore.add(ChatColor.AQUA + "Ready to Start!");
                    im.setLore(c4lore);
                    c4.setItemMeta(im);
                }
                ItemStack c4started = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c4started.getItemMeta();
                    im.setDisplayName(ChatColor.GREEN + "Daily Challenge #4");
                    List<String> c4startedlore = new ArrayList<String>();
                    c4startedlore.add(ChatColor.GRAY + "Kill 200 players");
                    c4startedlore.add("");
                    c4startedlore.add(ChatColor.RED + "In Progress...");
                    c4startedlore.add(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily4) + "/200");
                    im.setLore(c4startedlore);
                    c4started.setItemMeta(im);
                    c4started.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
                }
                ItemStack c4completed = new ItemStack(Material.PAPER, 1);
                {
                    ItemMeta im = c4completed.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "Daily Challenge #4");
                    List<String> c4completedlore = new ArrayList<String>();
                    c4completedlore.add(ChatColor.GRAY + "Kill 200 players");
                    c4completedlore.add("");
                    c4completedlore.add(ChatColor.GREEN + "Complete!");
                    c4completedlore.add(ChatColor.RED + "Come back later!");
                    im.setLore(c4completedlore);
                    c4completed.setItemMeta(im);
                }
                ItemStack c5 = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c5.getItemMeta();
                    im.setDisplayName(ChatColor.AQUA + "Weekly Challenge #1");
                    List<String> c5lore = new ArrayList<String>();
                    c5lore.add(ChatColor.GRAY + "Kill 1000 players");
                    c5lore.add("");
                    c5lore.add(ChatColor.AQUA + "Ready to Start!");
                    im.setLore(c5lore);
                    c5.setItemMeta(im);
                }
                ItemStack c5started = new ItemStack(Material.MAP, 1);
                {
                    ItemMeta im = c5started.getItemMeta();
                    im.setDisplayName(ChatColor.GREEN + "Weekly Challenge #1");
                    List<String> c5startedlore = new ArrayList<String>();
                    c5startedlore.add(ChatColor.GRAY + "Kill 100 players");
                    c5startedlore.add("");
                    c5startedlore.add(ChatColor.RED + "In Progress...");
                    c5startedlore.add(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Weekly1) + "/1000");
                    im.setLore(c5startedlore);
                    c5started.setItemMeta(im);
                    c5started.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
                }
                ItemStack c5completed = new ItemStack(Material.PAPER, 1);
                {
                    ItemMeta im = c5completed.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "Weekly Challenge #1");
                    List<String> c5completedlore = new ArrayList<String>();
                    c5completedlore.add(ChatColor.GRAY + "Kill 1000 players");
                    c5completedlore.add("");
                    c5completedlore.add(ChatColor.GREEN + "Complete!");
                    c5completedlore.add(ChatColor.RED + "Come back later!");
                    im.setLore(c5completedlore);
                    c5completed.setItemMeta(im);
                }

                if (challengesFiles.challengeIsStarted(p, Challenges.Daily1) == true)
                    challengesInventory.setItem(11, c1started);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily2) == true)
                    challengesInventory.setItem(12, c2started);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily3) == true)
                    challengesInventory.setItem(13, c3started);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily4) == true)
                    challengesInventory.setItem(14, c4started);
                if (challengesFiles.challengeIsStarted(p, Challenges.Weekly1) == true)
                    challengesInventory.setItem(15, c5started);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily1) == false)
                    challengesInventory.setItem(11, c1);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily2) == false)
                    challengesInventory.setItem(12, c2);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily3) == false)
                    challengesInventory.setItem(13, c3);
                if (challengesFiles.challengeIsStarted(p, Challenges.Daily4) == false)
                    challengesInventory.setItem(14, c4);
                if (challengesFiles.challengeIsStarted(p, Challenges.Weekly1) == false)
                    challengesInventory.setItem(15, c5);
                if (challengesFiles.challengeIsCompleted(p, Challenges.Daily1) == true)
                    challengesInventory.setItem(11, c1completed);
                if (challengesFiles.challengeIsCompleted(p, Challenges.Daily2) == true)
                    challengesInventory.setItem(12, c2completed);
                if (challengesFiles.challengeIsCompleted(p, Challenges.Daily3) == true)
                    challengesInventory.setItem(13, c3completed);
                if (challengesFiles.challengeIsCompleted(p, Challenges.Daily4) == true)
                    challengesInventory.setItem(14, c4completed);
                if (challengesFiles.challengeIsCompleted(p, Challenges.Weekly1) == true)
                    challengesInventory.setItem(15, c5completed);

                p.openInventory(challengesInventory);
            }

        }
    }
}
