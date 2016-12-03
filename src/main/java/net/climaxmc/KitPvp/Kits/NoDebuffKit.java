package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Listeners.ProjectileLaunchListener;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

public class NoDebuffKit{

    private Ability enderpearl = new Ability("enderpearl", 1, 10, TimeUnit.SECONDS);

    public NoDebuffKit() {
    }

    public void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.getInventory().clear();

        ItemStack helm = new ItemStack(Material.DIAMOND_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        chest.addEnchantment(Enchantment.DURABILITY, 2);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.DIAMOND_LEGGINGS);
        legs.addEnchantment(Enchantment.DURABILITY, 2);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);

        for (int slot = 1; slot <= 35; slot++) {
            player.getInventory().setItem(slot, new I(Material.POTION).durability(16421));
        }

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, new I(Material.ENDER_PEARL).amount(16));
        player.getInventory().setItem(2, new I(Material.POTION).durability(8226));
        player.getInventory().setItem(3, new I(Material.POTION).durability(8259));
        player.getInventory().setItem(17, new I(Material.POTION).durability(8226));
        player.getInventory().setItem(26, new I(Material.POTION).durability(8226));
        player.getInventory().setItem(35, new I(Material.POTION).durability(8226));
        player.getInventory().setItem(8, new I(Material.GOLDEN_CARROT).amount(64));
    }

    protected void wearNoSoup(Player player) {}

    @EventHandler
    public void playerInteract(ProjectileLaunchEvent event) {
        Bukkit.broadcastMessage("debug1");
        if (event.getEntity().getShooter() instanceof Player) {
            Bukkit.broadcastMessage("debug2");
            Player player = (Player) event.getEntity().getShooter();
            if (event.getEntityType().equals(EntityType.ENDER_PEARL)) {
                Bukkit.broadcastMessage("debug3");
                if (!enderpearl.tryUse(player)) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You can't use this for "
                            + ChatColor.GOLD + enderpearl.getStatus(player).getRemainingTime(TimeUnit.SECONDS) + 1
                            + ChatColor.GRAY + " more seconds!");
                }
            }
        }
    }
}
