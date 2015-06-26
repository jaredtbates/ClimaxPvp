package net.climaxmc.KitPvp.Kits;

import java.util.concurrent.TimeUnit;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
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

public class AssassinKit extends Kit {
	private Ability cloak = new Ability(1, 20, TimeUnit.SECONDS);
	
    public AssassinKit() {
        super("Assassin", new ItemStack(Material.GHAST_TEAR), "Use your Cloak Ability to take out opponents with stealth!", ChatColor.RED);
    }

    protected void wear(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack cloak = new ItemStack(Material.GHAST_TEAR);
        ItemMeta cloakmeta = cloak.getItemMeta();
        cloakmeta.setDisplayName(ChatColor.AQUA + "Cloak Ability");
        cloak.setItemMeta(cloakmeta);
        player.getInventory().addItem(cloak);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLACK);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
    	ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        player.getInventory().addItem(sword);
        ItemStack cloak = new ItemStack(Material.GHAST_TEAR);
        ItemMeta cloakmeta = cloak.getItemMeta();
        cloakmeta.setDisplayName(ChatColor.AQUA + "Cloak Ability");
        cloak.setItemMeta(cloakmeta);
        player.getInventory().addItem(cloak);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.BLACK);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.GHAST_TEAR) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                	if (!cloak.tryUse(player)) {
                        return;
                    }
                	for (Entity entity : player.getNearbyEntities(7, 7, 7)) {
                		if (entity instanceof Player) {
                		    Player players = (Player) entity;
                		    players.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 2));
                		    players.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 1));
                		    players.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 140, 0));
                		    player.removePotionEffect(PotionEffectType.SPEED);
                		    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 1));
                		    player.getWorld().playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 3, 1);
                		    Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> player.removePotionEffect(PotionEffectType.SPEED), 140);
                		    Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), () -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)), 141);
                		}
                	}
                }
            }
        }
    }
}
