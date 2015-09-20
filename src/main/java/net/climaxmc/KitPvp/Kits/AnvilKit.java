package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AnvilKit extends Kit {
    public AnvilKit() {
        super("Anvil", new ItemStack(Material.ANVIL), "Drop to the ground like an anvil and deal damage to nearby players!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!KitManager.isPlayerInKit(player, this)) {
            return;
        }

        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            return;
        }

        event.setCancelled(true);

        player.getNearbyEntities(10, 10, 10).stream().filter(entity -> entity instanceof Player).forEach(target -> ((Player) target).damage(event.getDamage(), player));
    }
}
