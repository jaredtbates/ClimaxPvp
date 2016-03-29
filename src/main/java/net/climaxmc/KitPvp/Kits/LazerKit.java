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

public class LazerKit extends Kit {
    private Ability beam = new Ability(1, 9, TimeUnit.SECONDS);

    public LazerKit() {
        super("Lazer", new ItemStack(Material.PRISMARINE_SHARD), "Pew pew!", ChatColor.RED);
    }

    protected void wear(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.AQUA);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack ability = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Beam Ability");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.AQUA);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack ability = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Beam Ability");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.PRISMARINE_SHARD) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!beam.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Beam" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.ClassType.ARCANIST);
                }
            }
        }
    }
}