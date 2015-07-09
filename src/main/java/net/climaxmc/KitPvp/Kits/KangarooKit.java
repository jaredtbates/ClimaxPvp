package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class KangarooKit extends Kit {
    public KangarooKit() {
        super("Kangaroo", new ItemStack(Material.FIREWORK), "Use your Firework to Jump High! (Shift for higher)", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = (new ItemStack(Material.LEATHER_LEGGINGS));
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 2);
        player.getInventory().setBoots(boots);
        ItemStack firework = new ItemStack(Material.FIREWORK);
        ItemMeta fwmeta = firework.getItemMeta();
        fwmeta.setDisplayName(ChatColor.GOLD + "Kangaroo Ability");
        firework.setItemMeta(fwmeta);
        player.getInventory().addItem(firework);
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestmeta.setColor(Color.RED);
        chestplate.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = (new ItemStack(Material.LEATHER_LEGGINGS));
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta legmeta = (LeatherArmorMeta) leggings.getItemMeta();
        legmeta.setColor(Color.RED);
        leggings.setItemMeta(legmeta);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 2);
        player.getInventory().setBoots(boots);
        ItemStack firework = new ItemStack(Material.FIREWORK);
        ItemMeta fwmeta = firework.getItemMeta();
        fwmeta.setDisplayName(ChatColor.GOLD + "Kangaroo Ability");
        firework.setItemMeta(fwmeta);
        player.getInventory().addItem(firework);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!KitManager.isPlayerInKit(player, this)) {
        	return;
        }
        if (player.getItemInHand().getType() == Material.FIREWORK) {
            event.setCancelled(true);
            Block b = player.getLocation().getBlock();
            if (b.getType() != Material.AIR || b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                if(!(player.isSneaking())){
                    player.setFallDistance(-(4F + 1));
                    Vector vector = player.getEyeLocation().getDirection();
                    vector.multiply(0.7F);
                    vector.setY(0.6);
                    player.setVelocity(vector);
                    } else {
                        player.setFallDistance(-(4F + 1));
                        Vector vector = player.getEyeLocation().getDirection();
                        vector.multiply(1.2F);
                        vector.setY(0.5);
                        player.setVelocity(vector);
                    }
            }
        }
    }
}
