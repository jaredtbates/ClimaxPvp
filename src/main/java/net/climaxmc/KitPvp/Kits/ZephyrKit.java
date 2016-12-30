package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

import static org.bukkit.Material.*;

public class ZephyrKit extends Kit {

    private final int cooldown = 12;
    private ItemStack ability = new ItemStack(FEATHER);

    private Ability absolutezero = new Ability("Gust", 1, cooldown, TimeUnit.SECONDS);

    public ZephyrKit() {
        super("Zephyr", new ItemStack(FEATHER), "Blow a gust of wind knocking opponents up!", ChatColor.DARK_PURPLE);
    }

    protected void wear(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));

        ItemStack helm = new ItemStack(LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta legsmeta = (LeatherArmorMeta) legs.getItemMeta();
        legsmeta.setColor(Color.YELLOW);
        legs.setItemMeta(legsmeta);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(IRON_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        addSoup(player.getInventory(), 2, 35);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Gust \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        regenResistance(player);

        ItemStack helm = new ItemStack(LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta legsmeta = (LeatherArmorMeta) legs.getItemMeta();
        legsmeta.setColor(Color.YELLOW);
        legs.setItemMeta(legsmeta);
        player.getInventory().setLeggings(legs);
        ItemStack boots = new ItemStack(IRON_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        SettingsFiles settingsFiles = new SettingsFiles();
        if (!settingsFiles.getSpawnSoupValue(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            player.getInventory().addItem(rod);
        }

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Gust \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == FEATHER) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!absolutezero.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.DARK_PURPLE + "Gust" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.LAST_BREATH);

                    absolutezero.startCooldown(player, this, cooldown, ability);
                }
            }
        }
    }
}
