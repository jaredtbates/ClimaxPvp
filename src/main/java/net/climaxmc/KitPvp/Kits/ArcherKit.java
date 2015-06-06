package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class ArcherKit extends Kit {
	ArrayList<UUID> archer = new ArrayList<UUID>();
	
    public ArcherKit() {
        super("Archer", new ItemStack(Material.BOW), "Snipe them up!", ChatColor.GRAY);
    }

    public void wear(Player player) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.WHITE);
        helmet.setItemMeta(helmetMeta);
        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        helmet.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setHelmet(helmet);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.WHITE);
        chestplate.setItemMeta(chestplateMeta);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.WHITE);
        leggings.setItemMeta(leggingsMeta);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        player.getInventory().setBoots(boots);
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 4);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
        player.getInventory().addItem(bow);
        addSoup(player.getInventory(), 2, 34);
        player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
        archer.add(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (archer.contains(player.getUniqueId())) {
            archer.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (archer.contains(player.getUniqueId())) {
            archer.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (archer.contains(player.getUniqueId())) {
            archer.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        
        if (!(archer.contains(player.getUniqueId()))){
        	return;
        }

        if (player.isSneaking()) {
            return;
        }

        double vel = event.getProjectile().getVelocity().length() * (0.1D + 0.1D * 5);
        // Knock player back
        velocity(player, player.getLocation().getDirection().multiply(-1), vel,
                false, 0.0D, 0.2D, 0.8D, true);
    }

    private void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost)
    {
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