package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Challenges.Challenge;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

                p.sendMessage(ChatColor.RED + "Challenges are being fixed at the moment!");
                p.playSound(p.getLocation(), Sound.CAT_MEOW, 1, 1);

                Inventory challengesInventory = Bukkit.createInventory(null, 27, "Challenges");

                int i = 0;
                for (Challenge challenge : Challenge.values()) {
                    String cooldownText = "";
                    long cooldown = challenge.getCooldownTime()
                            - ((System.currentTimeMillis() / 1000)
                            - (ClimaxPvp.getInstance().getChallengesFiles().getCompletedTime(p, challenge)));
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
                        if (cooldown == 0) {
                            cooldownText = "";
                            ClimaxPvp.getInstance().getChallengesFiles().setReady(p, challenge);
                        }
                    }

                    if (ClimaxPvp.getInstance().getChallengesFiles().challengeIsCompleted(p, challenge)) {
                        if (cooldownText.equals("")) {
                            ClimaxPvp.getInstance().getChallengesFiles().setReady(p, challenge);
                        }
                    }

                    ItemStack challengeReady = new I(Material.MAP)
                            .name(ChatColor.AQUA + challenge.getName())
                            .lore(ChatColor.GRAY + "Kill " + challenge.getKillRequirement() + " players")
                            .lore(ChatColor.DARK_AQUA + "Reward: " + ChatColor.GREEN + "$" + challenge.getRewardMoney())
                            .lore("")
                            .lore(ChatColor.AQUA + "Ready to Start!");
                    ItemStack challengeStarted = new I(Material.MAP)
                            .name(ChatColor.GREEN + challenge.getName())
                            .lore(ChatColor.GRAY + "Kill " + challenge.getKillRequirement() + " players")
                            .lore("")
                            .lore(ChatColor.RED + "In Progress...")
                            .lore(ChatColor.YELLOW + "Players Killed: "
                                    + ChatColor.GRAY + ClimaxPvp.getInstance().getChallengesFiles().getChallengeKills(p, challenge)
                                    + "/" + challenge.getKillRequirement()).enchantment(Enchantment.DURABILITY, i + 1);
                    ItemStack challengeCompleted = new I(Material.PAPER)
                            .name(ChatColor.RED + challenge.getName())
                            .lore(ChatColor.GRAY + "Kill " + challenge.getKillRequirement() + " players")
                            .lore("")
                            .lore(ChatColor.GREEN + "Complete!")
                            .lore(ChatColor.RED + "You can do this challenge")
                            .lore(ChatColor.RED + "again in " + ChatColor.GOLD + cooldownText);

                    if (ClimaxPvp.getInstance().getChallengesFiles().challengeIsStarted(p, challenge)) {
                        challengesInventory.setItem(11 + i, challengeStarted);
                    } else if (ClimaxPvp.getInstance().getChallengesFiles().challengeIsCompleted(p, challenge)) {
                        challengesInventory.setItem(11 + i, challengeCompleted);
                    } else {
                        challengesInventory.setItem(11 + i, challengeReady);
                    }

                    i++;
                }

                p.openInventory(challengesInventory);

            }

        }
    }
}
