package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.*;
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

public class AssassinKit extends Kit {
    private Ability cloak = new Ability(1, 15, TimeUnit.SECONDS);

    public AssassinKit() {
        super("Assassin", new ItemStack(Material.GHAST_TEAR), "Use your Cloak Ability to take out opponents with stealth!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        ItemStack cloak = new ItemStack(Material.GHAST_TEAR);
        ItemMeta cloakmeta = cloak.getItemMeta();
        cloakmeta.setDisplayName(ChatColor.AQUA + "Cloak Ability");
        cloak.setItemMeta(cloakmeta);
        player.getInventory().addItem(cloak);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLACK);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        ItemStack cloak = new ItemStack(Material.GHAST_TEAR);
        ItemMeta cloakmeta = cloak.getItemMeta();
        cloakmeta.setDisplayName(ChatColor.AQUA + "Cloak Ability");
        cloak.setItemMeta(cloakmeta);
        player.getInventory().addItem(cloak);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLACK);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.GHAST_TEAR) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!cloak.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Assassin" + ChatColor.GOLD + " Ability!");
                    for (Entity entity : player.getNearbyEntities(9, 9, 9)) {
                        if (entity instanceof Player) {
                            Player players = (Player) entity;
                            players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 2));
                            players.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 140, 1));
                            player.removePotionEffect(PotionEffectType.SPEED);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 1));
                            player.getWorld().playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 1);
                            player.getInventory().setHelmet(null);
                            player.getInventory().setChestplate(null);
                            player.getInventory().setLeggings(null);
                            player.getInventory().setBoots(null);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 140, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 140, 1));
                            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                            }, 140);
                            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> {
                                if (KitManager.isPlayerInKit(player, this)) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                                }
                            }, 141);
                            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> {
                                if (KitManager.isPlayerInKit(player, this)) {
                                    ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
                                    helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                                    LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
                                    helmmeta.setColor(Color.BLACK);
                                    helm.setItemMeta(helmmeta);
                                    player.getInventory().setHelmet(helm);
                                    player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                    player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                                    boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                                    LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
                                    bootsmeta.setColor(Color.BLACK);
                                    boots.setItemMeta(bootsmeta);
                                    player.getInventory().setBoots(boots);
                                }
                            }, 140);
                        }
                    }
                }
            }
        }
    }
}
