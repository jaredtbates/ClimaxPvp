package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FighterKit extends Kit {
    public FighterKit() {
        super("Fighter", new ItemStack(Material.DIAMOND_SWORD), "magic", ChatColor.GRAY);
    }

    public void wear(Player player) {
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

        if (player.getLevel() == 1 && player.getExp() == 0F) {
            return;
        } else {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), () -> player.setExp(player.getExp() - 0.5F), 0, 20 * 15);
        }
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
    }

    @EventHandler
    public void onDeath (EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
            Player target = (Player) event.getEntity();
            Player player = (Player) event.getDamager();
            if (target.getHealth() == 0 && player.getHealth() != 0) {
                if (player.getExp() != 1F) {
                    player.setExp(player.getExp() + 0.5F);
                }
                if (player.getExp() == 1F && player.getLevel() != 4) {
                    player.setLevel(player.getLevel() + 1);
                    player.setExp(0F);
                }
            }
        }
    }
}