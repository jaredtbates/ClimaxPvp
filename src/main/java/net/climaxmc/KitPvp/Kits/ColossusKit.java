package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.antinub.AntiNub;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

public class ColossusKit extends Kit {

    private final int cooldown = 12;
    private ItemStack ability = new ItemStack(Material.NETHER_BRICK_ITEM);

    private Ability slam = new Ability("Slam", 1, cooldown, TimeUnit.SECONDS);

    public ColossusKit() {
        super("Colossus", new ItemStack(Material.NETHER_BRICK_ITEM), "COME ON AND SLAM, AND WELCOME TO THE JAM", ChatColor.DARK_PURPLE);
    }

    protected void wear(Player player) {
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        legs.addEnchantment(Enchantment.DURABILITY, 2);
        LeatherArmorMeta legsmeta = (LeatherArmorMeta) legs.getItemMeta();
        legsmeta.setColor(Color.MAROON);
        legs.setItemMeta(legsmeta);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Slam \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);

        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chest);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        legs.addEnchantment(Enchantment.DURABILITY, 2);
        LeatherArmorMeta legsmeta = (LeatherArmorMeta) legs.getItemMeta();
        legsmeta.setColor(Color.MAROON);
        legs.setItemMeta(legsmeta);
        player.getInventory().setLeggings(legs);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        if (!ClimaxPvp.getInstance().spawnSoupTrue.containsKey(player)) {             ClimaxPvp.getInstance().spawnSoupTrue.put(player, false);         }
        if (!ClimaxPvp.getInstance().spawnSoupTrue.get(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            player.getInventory().addItem(rod);
        }

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Slam \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.NETHER_BRICK_ITEM) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!slam.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Slam" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.ASSAULT_AND_BATTERY);

                    for (Entity entities : player.getNearbyEntities(7, 7, 7)) {
                        if (entities.getType().equals(EntityType.PLAYER)) {
                            AntiNub.alertsEnabled.put(entities.getUniqueId(), false);
                            ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    AntiNub.alertsEnabled.put(entities.getUniqueId(), true);
                                }
                            }, 20L * 4);
                        }
                    }

                    slam.startCooldown(player, this, cooldown, ability);
                }
            }
        }
    }
}