package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
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

public class HealerKit extends Kit {
    private Ability heal = new Ability(1, 10, TimeUnit.SECONDS);

    public HealerKit() {
        super("Healer", new ItemStack(Material.GOLDEN_APPLE), "This kit gives you the ability to heal yourself!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        chest.addEnchantment(Enchantment.DURABILITY, 3);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
        chestmeta.setColor(Color.MAROON);
        chest.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack ability = new ItemStack(Material.BLAZE_ROD);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Heal Ability");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        chest.addEnchantment(Enchantment.DURABILITY, 3);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
        chestmeta.setColor(Color.MAROON);
        chest.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
        ItemStack ability = new ItemStack(Material.BLAZE_ROD);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Heal Ability");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_ROD) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!heal.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Heal" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.HEAL);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        final Player player = event.getPlayer();
        if(KitManager.isPlayerInKit(player, this)){
            if(player.isSneaking()){
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.removePotionEffect(PotionEffectType.WEAKNESS);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
            }else{
                Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> {
                    if(KitManager.isPlayerInKit(player, this)) {
                        if (player.isSneaking()) {
                            player.removePotionEffect(PotionEffectType.REGENERATION);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 3));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 10));
                        }
                    }
                }, 20);
            }
        }
    }
}