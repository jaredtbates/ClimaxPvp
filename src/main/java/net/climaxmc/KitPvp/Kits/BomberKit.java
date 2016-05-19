package net.climaxmc.KitPvp.Kits;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Particles.TNTParticle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class BomberKit extends Kit {
    private Ability explode = new Ability(1, 13, TimeUnit.SECONDS);

    public BomberKit() {
        super("Bomber", new ItemStack(Material.TNT), "Explode in people's faces :D!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.ORANGE);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.ORANGE);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack ability = new ItemStack(Material.TNT);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Explode Ability");
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
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.ORANGE);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.ORANGE);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);
        ItemStack ability = new ItemStack(Material.TNT);
        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Explode Ability");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.TNT) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!explode.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Explode" + ChatColor.GOLD + " Ability!");
                    DisguiseAbilities.activateAbility(player, DisguiseAbilities.Ability.DETONATE);
                }
            }
        }
    }


    /*@EventHandler
    public void onPlayerClickDropTNT(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!KitManager.isPlayerInKit(player, this) || event.getItem() == null || !event.getItem().getType().equals(Material.TNT)) {
            return;
        }

        spawnTNT(player);
    }

    private void spawnTNT(Player player) {
        player.playSound(player.getLocation(), Sound.FUSE, 1, 1);
        ItemStack tntInInv = player.getItemInHand();
        tntInInv.setAmount(tntInInv.getAmount() - 1);
        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), tntInInv);
        TNTPrimed tnt = player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), TNTPrimed.class);
        tnt.setFuseTicks(17);
        velocity(tnt, player.getLocation().getDirection().multiply(2), 0.5, false, 0.0, 0.1, 10.0, false);
        velocity(player, player.getLocation().getDirection().multiply(-1), tnt.getVelocity().length() + 0.02, false, 0.0, 0.2, 0.8, true);
        new TNTParticle(tnt);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        event.setCancelled(true); // This should fix the bomber issue! It should also enable damage from tnt!
        event.getLocation().getWorld().createExplosion(event.getLocation().getX(), event.getLocation().getY(), event.getLocation().getZ(), 4, false, false);
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
    }*/
    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event){
        final Player player = event.getPlayer();
        if(KitManager.isPlayerInKit(player, this)){
            if(player.isSneaking()){
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
            }else{
                player.removePotionEffect(PotionEffectType.REGENERATION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 3));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
            }
        }
    }
}