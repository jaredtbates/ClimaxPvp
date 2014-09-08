package net.climaxmc.KitPvp.KitPvp.Kits;

import net.climaxmc.KitPvp.Main;
import net.climaxmc.KitPvp.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp.Soup;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class SoldierKit extends Kit {
	Main plugin;
	
	public SoldierKit() {
		super("Soldier", new ItemStack(Material.FEATHER));
	}
	
	public void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
		player.getInventory().setBoots(boots);
		Soup.add(player.getInventory(), 1, 35);
		player.sendMessage("§6You have chosen §aSoldier");
	}
}
