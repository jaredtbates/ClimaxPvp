package net.climaxmc.KitPvp.Kits;


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.antinub.AntiNub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class RangerKit extends Kit {
    public RangerKit() {
        super("Ranger", new ItemStack(Material.ARROW), "Equipped with a Combat Bow so you can rek dem all!", ChatColor.DARK_PURPLE);
    }

    protected void wear(Player player) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.ORANGE);
        helmet.setItemMeta(helmetMeta);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        helmet.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setHelmet(helmet);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.WHITE);
        chestplate.setItemMeta(chestplateMeta);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.ORANGE);
        leggings.setItemMeta(leggingsMeta);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowmeta = bow.getItemMeta();
        bowmeta.setDisplayName(ChatColor.RED + "Combat Bow");
        ArrayList<String> lores = new ArrayList<>();
        lores.add("Knocks you back when fired, Shift to avoid Knockback!");
        bowmeta.setLore(lores);
        bow.setItemMeta(bowmeta);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        player.getInventory().addItem(bow);

        addSoup(player.getInventory(), 2, 34);

        ItemStack ability = new ItemStack(Material.ARROW);
        player.getInventory().addItem(ability);
        player.updateInventory();
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.ORANGE);
        helmet.setItemMeta(helmetMeta);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setHelmet(helmet);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.WHITE);
        chestplate.setItemMeta(chestplateMeta);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.ORANGE);
        leggings.setItemMeta(leggingsMeta);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowmeta = bow.getItemMeta();
        bowmeta.setDisplayName(ChatColor.RED + "Combat Bow");
        ArrayList<String> lores = new ArrayList<>();
        lores.add("Knocks you back when fired, Shift to avoid Knockback!");
        bowmeta.setLore(lores);
        bow.setItemMeta(bowmeta);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        player.getInventory().addItem(bow);

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);

        ItemStack ability = new ItemStack(Material.ARROW);
        player.getInventory().addItem(ability);
        player.updateInventory();
    }

    public int taskID = 0;

    public void giveArrows(Player player) {
        Bukkit.getServer().getScheduler().cancelTask(taskID);
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (player.getInventory().contains(Material.ARROW, 3) || !KitManager.isPlayerInKit(player, RangerKit.this)) {
                    return;
                } else {
                    ItemStack ability = new ItemStack(Material.ARROW);
                    player.getInventory().addItem(ability);
                    player.updateInventory();
                }
            }
        }, 0L, 20L * 3L );
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!KitManager.isPlayerInKit(player, this)) {
            return;
        }

        if (player.isSneaking()) {
            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                @Override
                public void run() {
                    ItemStack ability = new ItemStack(Material.ARROW);
                    player.getInventory().addItem(ability);
                    player.updateInventory();
                }
            }, 10L);
            return;
        }

        double vel = event.getProjectile().getVelocity().length() * (0.1D + 0.1D * 2.25);
        // Knock player back
        velocity(player, player.getLocation().getDirection().multiply(-1), vel, false, 0.0D, 0.2D, 0.5D, true);

        AntiNub.alertsEnabled.put(player.getUniqueId(), false);
        ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                AntiNub.alertsEnabled.put(player.getUniqueId(), true);
            }
        }, 20L * 4);

        Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                ItemStack ability = new ItemStack(Material.ARROW);
                player.getInventory().addItem(ability);
                player.updateInventory();
            }
        }, 10L);
    }

    private void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost) {
        if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
            return;
        }

        if (ySet) {
            vec.setY(yBase);
        }

        vec.normalize();
        vec.multiply(str);

        vec.setY(vec.getY() + yAdd);

        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }

        if (groundBoost) {
            vec.setY(vec.getY() + 0.2D);
        }

        ent.setFallDistance(0.0F);
        ent.setVelocity(vec);
    }
}