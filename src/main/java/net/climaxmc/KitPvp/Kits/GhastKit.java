package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class GhastKit extends Kit {
    private Ability fireball = new Ability(1, 2, TimeUnit.SECONDS);

    public GhastKit() {
        super("Ghast", new ItemStack(Material.FIREBALL), "Set the world on Fire with the Ghast Kit!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        player.getInventory().addItem(sword);
        ItemStack hoe = new ItemStack(Material.GOLD_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.RED + "Fireball Launcher");
        hoe.setItemMeta(hoeMeta);
        player.getInventory().addItem(hoe);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        player.getInventory().addItem(sword);
        ItemStack hoe = new ItemStack(Material.GOLD_HOE);
        ItemMeta hoeMeta = hoe.getItemMeta();
        hoeMeta.setDisplayName(ChatColor.RED + "Fireball Launcher");
        hoe.setItemMeta(hoeMeta);
        player.getInventory().addItem(hoe);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setBoots(boots);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
    }

    @EventHandler
    protected void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.GOLD_HOE) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!fireball.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Fireball" + ChatColor.GOLD + " Ability!");
                    Fireball fireball = event.getPlayer().launchProjectile(Fireball.class);
                    //player.getWorld().playSound(player.getLocation(), Sound.GHAST_FIREBALL, 1, 1);
                    fireball.setIsIncendiary(false);
                    player.setVelocity(player.getVelocity().setY(0.4));
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player target = (Player) event.getEntity();
            if (event.getDamager() instanceof Fireball) {
                Fireball f = (Fireball) event.getDamager();
                if (f.getShooter() instanceof Player) {
                    if (!VanishCommand.getVanished().contains(target.getUniqueId())
                            && !CheckCommand.getChecking().contains(target.getUniqueId())
                            && (KitPvp.currentTeams.get(target.getName()) != ((Player) f.getShooter()).getName()
                            && KitPvp.currentTeams.get(((Player) f.getShooter()).getName()) != target.getName())) {
                        event.setDamage(23);
                    }
                    target.setVelocity(target.getVelocity().setY(1));
                }
            }
        }
    }
}
