package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnailKit extends Kit {
    public SnailKit() {
        super("Snail", new ItemStack(Material.INK_SACK, 1, (byte) 7), "With each hit you slow your enemy!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_AXE);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setColor(Color.SILVER);
        helmet.setItemMeta(meta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        regenResistance(player);
        ItemStack sword = new ItemStack(Material.DIAMOND_AXE);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setColor(Color.SILVER);
        helmet.setItemMeta(meta);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPvp(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();
                if (KitManager.isPlayerInKit(player, this)) {
                    if (player.getInventory().getItemInHand().getType() == Material.DIAMOND_AXE) {
                        damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                    }
                }
            }
        }
    }
}
