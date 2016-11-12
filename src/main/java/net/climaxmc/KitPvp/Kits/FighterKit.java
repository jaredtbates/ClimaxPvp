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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FighterKit extends Kit {

    public int expTask;

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
            expTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), () -> player.setExp(player.getExp() - 0.5F), 0, 20 * 15);
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
    public void onDeath (PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (player.getHealth() == 0 && killer.getHealth() != 0) {
            if (killer.getExp() != 1F) {
                killer.setExp(killer.getExp() + 0.5F);
            }
            if (killer.getExp() == 1F && killer.getLevel() != 4) {
                killer.setLevel(killer.getLevel() + 1);
                killer.setExp(0F);
            }
        } else if (killer.getHealth() == 0) {
            Bukkit.getScheduler().cancelTask(expTask);
            player.setExp(0F);
            player.setLevel(0);
        }
    }
}