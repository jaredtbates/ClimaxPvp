package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class BlazeKit extends Kit {
    private Ability blaze = new Ability(1, 15, TimeUnit.SECONDS);

    public BlazeKit() {
        super("Blaze", new ItemStack(Material.BLAZE_POWDER), "Use your Blaze ability to set everyone within 5 Blocks on fire!", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        player.getInventory().addItem(sword);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze Ability");
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
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack blazePowder = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta blazeMeta = blazePowder.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze Ability");
        blazePowder.setItemMeta(blazeMeta);
        player.getInventory().addItem(blazePowder);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
    }

    /*@EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_POWDER) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!blaze.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Blaze" + ChatColor.GOLD + " Ability!");
                    for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                        if (entity instanceof Player) {
                            Player players = (Player) entity;
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
                            player.getWorld().playSound(player.getLocation(), Sound.BLAZE_BREATH, 2, 2);
                            if (!VanishCommand.getVanished().contains(players.getUniqueId())
                                    && !CheckCommand.getChecking().contains(players.getUniqueId())
                                    && (KitPvp.currentTeams.get(player.getName()) != players.getName()
                                    && KitPvp.currentTeams.get(players.getName()) != player.getName())) {
                                players.setFireTicks(170);
                            }
                        }
                    }
                }
            }
        }
    }*/
    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        final Player player = event.getPlayer();
        if(KitManager.isPlayerInKit(player, this)){
            if(player.isSneaking()){
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
            }else{
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
            }
        }
    }
}
