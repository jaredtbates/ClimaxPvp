package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PrecisionKit extends Kit {
    public PrecisionKit() {
        super("Precision", new ItemStack(Material.ARROW), "Op Sword, but damages you if you miss!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordmeta = sword.getItemMeta();
        swordmeta.setDisplayName(ChatColor.AQUA + "Precision Sword");
        sword.setItemMeta(swordmeta);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);
    }

    protected void wearNoSoup(Player player) {
    	for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    	player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
    	ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordmeta = sword.getItemMeta();
        swordmeta.setDisplayName(ChatColor.AQUA + "Precision Sword");
        sword.setItemMeta(swordmeta);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        player.getInventory().addItem(sword);
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        ItemStack boots = new ItemStack(Material.GOLD_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.DIAMOND_SWORD) {
                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    player.damage(4);
                }
                if(event.getAction() == Action.LEFT_CLICK_BLOCK){
                	player.damage(4);
                }
            }
        }
    }
}
