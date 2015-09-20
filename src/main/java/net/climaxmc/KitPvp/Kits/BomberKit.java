package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.TNTParticle;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class BomberKit extends Kit {
    public BomberKit() {
        super("Bomber", new ItemStack(Material.TNT), "Throw your bombs at people to explode them!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.TNT));
        addSoup(player.getInventory(), 2, 35);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack.getType().equals(Material.TNT)) {
                        if (itemStack.getAmount() < 3) {
                            itemStack.setAmount(itemStack.getAmount() + 1);
                        }
                    }
                }
            }
        }.runTaskTimer(ClimaxPvp.getInstance(), 60, 60);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.TNT));
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack.getType().equals(Material.TNT)) {
                        if (itemStack.getAmount() < 3) {
                            itemStack.setAmount(itemStack.getAmount() + 1);
                        }
                    }
                }
            }
        }.runTaskTimer(ClimaxPvp.getInstance(), 60, 60);
    }
    
    /* For the ability - Players should receive 1 TNT every 5 seconds and be able to throw the tnt by right/left clicking on the tnt.
     *  Maximum of 3 tnt at a time, and could you add all the little particle effects like before? make them red.
     *  and of course throwing the bomb should use up 1 in your inventory. */

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDropTNT(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();

        if (!KitManager.isPlayerInKit(player, this) || !item.getItemStack().getType().equals(Material.TNT)) {
            return;
        }

        spawnTNT(player);
    }

    @EventHandler
    public void onPlayerClickDropTNT(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!KitManager.isPlayerInKit(player, this) || event.getItem() == null || !event.getItem().getType().equals(Material.TNT)) {
            return;
        }

        spawnTNT(player);
    }

    private void spawnTNT(Player player) {
        player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
        ItemStack tntInInv = player.getItemInHand();
        tntInInv.setAmount(tntInInv.getAmount() - 1);
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), tntInInv);
        TNTPrimed tnt = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), TNTPrimed.class);
        velocity(tnt, player.getLocation().getDirection().multiply(2), 0.5, false, 0.0, 0.1, 10.0, false);
        velocity(player, player.getLocation().getDirection().multiply(-1), tnt.getVelocity().length() + 0.02, false, 0.0, 0.2, 0.8, true);
        new TNTParticle(tnt);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        event.setCancelled(true);
        event.getLocation().getWorld().createExplosion(event.getLocation().getX(), event.getLocation().getY(), event.getLocation().getZ(), 6, false, false);
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