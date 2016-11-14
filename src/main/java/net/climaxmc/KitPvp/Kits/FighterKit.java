package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class FighterKit extends Kit {

    public int expTask = 1;

    public FighterKit() {
        super("Fighter", new ItemStack(Material.DIAMOND_SWORD), "magic", ChatColor.GRAY);
    }

    public void wear(Player player) {
        Player killer = player;

        ClimaxPvp.inFighterKit.add(player);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));

        player.setLevel(1);
        player.setExp(0F);

        expTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (ClimaxPvp.inFighterKit.contains(killer)) {
                    Bukkit.broadcastMessage("why");
                    if (killer.getLevel() == 1 && killer.getExp() == 0.5F) {
                        killer.setExp(killer.getExp() - 0.5F);
                        Bukkit.broadcastMessage("debug1");
                    } else if (killer.getLevel() >= 2 && killer.getExp() == 0) {
                        killer.setLevel(killer.getLevel() - 1);
                        killer.setExp(0.5F);
                        Bukkit.broadcastMessage("quue");
                    } else if (killer.getLevel() >= 2 && killer.getExp() == 0.5) {
                        killer.setExp(0F);
                        Bukkit.broadcastMessage("final que");
                    }
                    if (killer.getLevel() == 1 && (killer.getExp() == 0F || killer.getExp() == 0.5F)) {
                        wear1(killer);
                    }

                    if (killer.getLevel() == 2 && (killer.getExp() == 0F || killer.getExp() == 0.5F)) {
                        wear2(killer);
                    }
                } else {
                    Bukkit.getScheduler().cancelTask(expTask);
                }
            }
        }, 300L, 300L);
    }

    protected void wearNoSoup(Player player) {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity().getType().equals(EntityType.PLAYER))){
            return;
        }
        Player player = (Player) event.getEntity();
        Player killer = player.getKiller();
        Bukkit.broadcastMessage("halp");
        if (ClimaxPvp.inFighterKit.contains(killer)) {
            Bukkit.broadcastMessage("help me");
            if (player.getHealth() - event.getDamage() <= 0) {
                if (killer.getExp() != 1F) {
                    killer.setExp(killer.getExp() + 0.5F);
                }
                if (killer.getExp() == 1F && killer.getLevel() != 4) {
                    killer.setLevel(killer.getLevel() + 1);
                    killer.setExp(0F);
                }
                player.setExp(0F);
                player.setLevel(0);

                if (killer.getLevel() == 1 && (killer.getExp() == 0F || killer.getExp() == 1F)) {
                    wear1(killer);
                }

                if (killer.getLevel() == 2 && (killer.getExp() == 0F || killer.getExp() == 1F)) {
                    wear2(killer);
                }
            }
        }
    }
    @EventHandler
    public void onTeleport (PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().distance(ClimaxPvp.getInstance().getWarpLocation("Upgrade")) >= 100) {
            Bukkit.broadcastMessage("debug0");
            ClimaxPvp.inFighterKit.remove(player);
        }
    }

    public void wear1 (Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
    }

    public void wear2 (Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        player.sendMessage("");
    }
}