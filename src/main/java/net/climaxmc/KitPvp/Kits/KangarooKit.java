package net.climaxmc.KitPvp.Kits;


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.antinub.AntiNub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KangarooKit extends Kit implements Listener {

    public KangarooKit() {
        super("Kangaroo", new ItemStack(Material.FIREWORK), "Use your Firework to Jump High! (Shift for higher)", ChatColor.DARK_PURPLE);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = (new ItemStack(Material.LEATHER_LEGGINGS));
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);

        ItemStack firework = new ItemStack(Material.FIREWORK);
        ItemMeta fwmeta = firework.getItemMeta();
        fwmeta.setDisplayName(ChatColor.GOLD + "Kangaroo Ability");
        firework.setItemMeta(fwmeta);
        player.getInventory().addItem(firework);

        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = (new ItemStack(Material.LEATHER_LEGGINGS));
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);

        ItemStack firework = new ItemStack(Material.FIREWORK);
        ItemMeta fwmeta = firework.getItemMeta();
        fwmeta.setDisplayName(ChatColor.GOLD + "Kangaroo Ability");
        firework.setItemMeta(fwmeta);
        player.getInventory().addItem(firework);
    }

    /**
     * 3 jump states
     * 0 - Can't jump but PlayerMoveEvent can set jump state to 1
     * 1 - Can jump
     * 2 - Can't jump and PlayerMoveEvent can't set jump state, this is quickly set to 0 1/4 of a second after jumping
     *
     * The 3 states prevents double jumping from the PlayerMoveEvent allowing a second jump as the first one is made.
     */
    private Map<Player, Integer> jumpState = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!KitManager.isPlayerInKit(player, this)) {
            return;
        }
        if (!(player.getInventory().getItemInHand().getType() == Material.FIREWORK)) {
            return;
        }
        event.setCancelled(true);
        if (!jumpState.containsKey(player)) {
            jumpState.put(player, 1);
        }
        if (jumpState.get(player) == 1) {
            player.setFallDistance(-5);
            Vector vector = player.getEyeLocation().getDirection();
            vector.multiply(0.62F);
            vector.setY(1);
            player.setVelocity(vector);

            jumpState.put(player, 2);

            Bukkit.getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                @Override
                public void run() {
                    jumpState.put(player, 0);
                }
            }, 5L);

            AntiNub.alertsEnabled.put(player.getUniqueId(), false);
            ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                @Override
                public void run() {
                    AntiNub.alertsEnabled.put(player.getUniqueId(), true);
                }
            }, 20L * 4);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
            if (!jumpState.containsKey(player)) {
                jumpState.put(player, 0);
            }
            if (jumpState.get(player) == 0) {
                jumpState.put(player, 1);
            }
        }
    }
}
