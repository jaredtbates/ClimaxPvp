package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class DragonKit extends Kit {
    private Ability flamebreath = new Ability(1, 10, TimeUnit.SECONDS);

    public DragonKit() {
        super("Dragon", new ItemStack(Material.MAGMA_CREAM), "Use your firey blaze breath to murder your foes!", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Flame Breath Ability");
        blazePowder.setItemMeta(blazeMeta);
        player.getInventory().addItem(blazePowder);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 1);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        player.getInventory().addItem(sword);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Flame Breath Ability");
        blazePowder.setItemMeta(blazeMeta);
        player.getInventory().addItem(blazePowder);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 1);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_POWDER) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!flamebreath.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Flame Breath" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.ClassType.FIRE_DRAGON);
                }
            }
        }
    }
}