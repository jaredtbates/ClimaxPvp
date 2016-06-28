package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class IronGolemKit extends Kit {
    private Ability Throw = new Ability(1, 4, TimeUnit.SECONDS);

    public IronGolemKit() {
        super("Iron Golem", new ItemStack(Material.RED_ROSE), "Punch people with your Rose to launch them in the air!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.RED_ROSE));
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
        player.getInventory().addItem(new ItemStack(Material.RED_ROSE));
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setBoots(boots);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player target = (Player) event.getEntity();
                if (KitManager.isPlayerInKit(player, this)) {
                    if (player.getItemInHand().getType().equals(Material.RED_ROSE)) {
                        event.setCancelled(true);
                        if (!Throw.tryUse(player)) {
                            return;
                        }
                        if (!VanishCommand.getVanished().contains(target.getUniqueId())
                                && !CheckCommand.getChecking().contains(target.getUniqueId())
                                && (KitPvp.currentTeams.get(player.getName()) != target.getName()
                                && KitPvp.currentTeams.get(target.getName()) != player.getName())) {
                            target.setVelocity(new Vector(0, 1.3, 0));
                        }
                    }
                }
            }
        }
    }
}
