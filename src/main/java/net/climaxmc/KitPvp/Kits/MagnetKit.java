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

public class MagnetKit extends Kit {
    private Ability pull = new Ability(1, 6, TimeUnit.SECONDS);

    public MagnetKit() {
        super("Magnet", new ItemStack(Material.SUGAR), "Pull your foes to you, so you can rek them!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.RED);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack ability = new ItemStack(Material.SUGAR);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Pull");
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
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.RED);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
        ItemStack ability = new ItemStack(Material.SUGAR);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Pull");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.SUGAR) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!pull.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Pull" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.MAGNETIC_IMPULSE);
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