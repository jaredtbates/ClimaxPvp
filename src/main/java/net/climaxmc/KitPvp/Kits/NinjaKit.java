package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class NinjaKit extends Kit {

    private final int cooldown = 10;
    private ItemStack ability = new ItemStack(Material.GLOWSTONE_DUST);

    private Ability burst = new Ability("Burst", 1, cooldown, TimeUnit.SECONDS);

    public NinjaKit() {
        super("Ninja", new ItemStack(Material.GOLD_HELMET), "Move and strike with the speed of a Ninja!", ChatColor.BLUE);
    }

    protected void wear(Player player) {
        ItemStack helm = new ItemStack(Material.GOLD_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Burst \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        regenResistance(player);
        ItemStack helm = new ItemStack(Material.GOLD_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        SettingsFiles settingsFiles = new SettingsFiles();
        if (!settingsFiles.getSpawnSoupValue(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            player.getInventory().addItem(rod);
        }

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Burst \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.GLOWSTONE_DUST) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!burst.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Burst" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.ELECTRO_SHOCK);

                    burst.startCooldown(player, this, cooldown, ability);
                }
            }
        }
    }
}