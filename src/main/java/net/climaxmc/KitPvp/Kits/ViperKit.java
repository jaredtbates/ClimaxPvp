package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ViperKit extends Kit {
    public ViperKit() {
        super("Viper", new ItemStack(Material.SPIDER_EYE), "With each hit you poison your enemy!", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setColor(Color.LIME);
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setColor(Color.LIME);
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
                Player target = (Player) event.getEntity();
                if (KitManager.isPlayerInKit(player, this)) {
                    if (player.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                        if (!VanishCommand.getVanished().contains(target.getUniqueId())
                                && !CheckCommand.getChecking().contains(target.getUniqueId())
                                && (KitPvp.currentTeams.get(player.getName()) != target.getName()
                                && KitPvp.currentTeams.get(target.getName()) != player.getName())) {
                            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));
                        }
                    }
                }
            }
        }
    }
}
