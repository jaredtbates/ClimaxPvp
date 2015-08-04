package net.climaxmc.KitPvp.Menus;

import net.climaxmc.KitPvp.Utils.*;
import org.bukkit.*;
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
    public ItemStack createChallengeInCooldownItem(Challenges challenge, String cooldownTime) {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + challenge.getName());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Kill " + challenge.getKillreq() + " players");
        lore.add("");
        lore.add(ChatColor.GREEN + "Complete!");
        lore.add(ChatColor.RED + "You can do this challenge");
        lore.add(ChatColor.RED + "again in " + ChatColor.GOLD + cooldownTime);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return itemStack;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();

        ChallengesFiles challengesFiles = new ChallengesFiles();

        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }

        if (item != null) {
            if (item.getType().equals(Material.DIAMOND)) {
                Inventory challengesInventory = Bukkit.createInventory(null, 27, "Challenges");

                ItemStack c1 = new I(Material.MAP).name(ChatColor.AQUA + "Daily Challenge #1").lore(ChatColor.GRAY + "Kill 20 players").lore("").lore(ChatColor.AQUA + "Ready to Start!");
                ItemStack c1started = new I(Material.MAP).name(ChatColor.GREEN + "Daily Challenge #1").lore(ChatColor.GRAY + "Kill 20 players").lore("").lore(ChatColor.RED + "In Progress...").lore(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily1) + "/20").enchantment(Enchantment.DURABILITY, 1);
                ItemStack c1completed = new I(Material.PAPER).name(ChatColor.RED + "Daily Challenge #1").lore(ChatColor.GRAY + "Kill 20 players").lore("").lore(ChatColor.GREEN + "Complete!").lore(ChatColor.RED + "Come back later!");

                ItemStack c2 = new I(Material.MAP).name(ChatColor.AQUA + "Daily Challenge #2").lore(ChatColor.GRAY + "Kill 75 players").lore("").lore(ChatColor.AQUA + "Ready to Start!");
                ItemStack c2started = new I(Material.MAP).name(ChatColor.GREEN + "Daily Challenge #2").lore(ChatColor.GRAY + "Kill 75 players").lore("").lore(ChatColor.RED + "In Progress...").lore(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily2) + "/75").enchantment(Enchantment.DURABILITY, 2);
                ItemStack c2completed = new I(Material.PAPER).name(ChatColor.RED + "Daily Challenge #2").lore(ChatColor.GRAY + "Kill 75 players").lore("").lore(ChatColor.GREEN + "Complete!").lore(ChatColor.RED + "Come back later!");

                ItemStack c3 = new I(Material.MAP).name(ChatColor.AQUA + "Daily Challenge #3").lore(ChatColor.GRAY + "Kill 150 players").lore("").lore(ChatColor.AQUA + "Ready to Start!");
                ItemStack c3started = new I(Material.MAP).name(ChatColor.GREEN + "Daily Challenge #3").lore(ChatColor.GRAY + "Kill 150 players").lore("").lore(ChatColor.RED + "In Progress...").lore(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily3) + "/150").enchantment(Enchantment.DURABILITY, 3);
                ItemStack c3completed = new I(Material.PAPER).name(ChatColor.RED + "Daily Challenge #3").lore(ChatColor.GRAY + "Kill 150 players").lore("").lore(ChatColor.GREEN + "Complete!").lore(ChatColor.RED + "Come back later!");

                ItemStack c4 = new I(Material.MAP).name(ChatColor.AQUA + "Daily Challenge #4").lore(ChatColor.GRAY + "Kill 200 players").lore("").lore(ChatColor.AQUA + "Ready to Start!");
                ItemStack c4started = new I(Material.MAP).name(ChatColor.GREEN + "Daily Challenge #4").lore(ChatColor.GRAY + "Kill 200 players").lore("").lore(ChatColor.RED + "In Progress...").lore(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Daily4) + "/200").enchantment(Enchantment.DURABILITY, 4);
                ItemStack c4completed = new I(Material.PAPER).name(ChatColor.RED + "Daily Challenge #4").lore(ChatColor.GRAY + "Kill 200 players").lore("").lore(ChatColor.GREEN + "Complete!").lore(ChatColor.RED + "Come back later!");

                ItemStack c5 = new I(Material.MAP).name(ChatColor.AQUA + "Weekly Challenge #1").lore(ChatColor.GRAY + "Kill 1000 players").lore("").lore(ChatColor.AQUA + "Ready to Start!");
                ItemStack c5started = new I(Material.MAP).name(ChatColor.GREEN + "Weekly Challenge #1").lore(ChatColor.GRAY + "Kill 100 players").lore("").lore(ChatColor.RED + "In Progress...").lore(ChatColor.YELLOW + "Players Killed: " + ChatColor.GRAY + challengesFiles.getChallengeKills(p, Challenges.Weekly1) + "/1000").enchantment(Enchantment.DURABILITY, 5);
                ItemStack c5completed = new I(Material.PAPER).name(ChatColor.RED + "Weekly Challenge #1").lore(ChatColor.GRAY + "Kill 1000 players").lore("").lore(ChatColor.GREEN + "Complete!").lore(ChatColor.RED + "Come back later!");

                for (Challenges challenge : Challenges.values()) {
                    String cooldownText = "";
                    long cooldown = challenge.getCooldownTime()
                            - ((System.currentTimeMillis() / 1000)
                            - (challengesFiles.getStartTime(p, challenge)));
                    if (cooldown >= 86400) {
                        cooldown = ((cooldown / 60) / 60) / 24;
                        if (cooldown == 1) cooldownText = "1 day";
                        if (cooldown > 1) cooldownText = cooldown + " days";
                    } else if (cooldown >= 3600) {
                        cooldown = (cooldown / 60) / 60;
                        if (cooldown == 1) cooldownText = "1 hour";
                        if (cooldown > 1) cooldownText = cooldown + " hours";
                    } else if (cooldown >= 60) {
                        cooldown = cooldown / 60;
                        if (cooldown == 1) cooldownText = "1 minute";
                        if (cooldown > 1) cooldownText = cooldown + " minutes";
                    } else {
                        if (cooldown == 1) cooldownText = "1 second";
                        if (cooldown > 1) cooldownText = cooldown + " seconds";
                    }

                    if (challengesFiles.challengeIsStarted(p, Challenges.Daily1)) {
                        challengesInventory.setItem(11, c1started);
                    } else if (challengesFiles.challengeIsCompleted(p, Challenges.Daily1)) {
                        challengesInventory.setItem(11, createChallengeInCooldownItem(challenge, cooldownText));
                    } else {
                        challengesInventory.setItem(11, c1);
                    }

                    if (challengesFiles.challengeIsStarted(p, Challenges.Daily2)) {
                        challengesInventory.setItem(12, c2started);
                    } else if (challengesFiles.challengeIsCompleted(p, Challenges.Daily2)) {
                        challengesInventory.setItem(12, createChallengeInCooldownItem(challenge, cooldownText));
                    } else {
                        challengesInventory.setItem(12, c2);
                    }

                    if (challengesFiles.challengeIsStarted(p, Challenges.Daily3)) {
                        challengesInventory.setItem(13, c3started);
                    } else if (challengesFiles.challengeIsCompleted(p, Challenges.Daily3)) {
                        challengesInventory.setItem(13, createChallengeInCooldownItem(challenge, cooldownText));
                    } else {
                        challengesInventory.setItem(13, c3);
                    }

                    if (challengesFiles.challengeIsStarted(p, Challenges.Daily4)) {
                        challengesInventory.setItem(14, c4started);
                    } else if (challengesFiles.challengeIsCompleted(p, Challenges.Daily4)) {
                        challengesInventory.setItem(14, createChallengeInCooldownItem(challenge, cooldownText));
                    } else {
                        challengesInventory.setItem(14, c4);
                    }

                    if (challengesFiles.challengeIsStarted(p, Challenges.Weekly1)) {
                        challengesInventory.setItem(15, c5started);
                    } else if (challengesFiles.challengeIsCompleted(p, Challenges.Weekly1)) {
                        challengesInventory.setItem(15, createChallengeInCooldownItem(challenge, cooldownText));
                    } else {
                        challengesInventory.setItem(15, c5);
                    }
                }

                p.openInventory(challengesInventory);
            }

        }
    }
}
