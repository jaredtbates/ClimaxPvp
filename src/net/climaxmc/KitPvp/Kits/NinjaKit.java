package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NinjaKit extends Kit{

	public NinjaKit() {
		super("Ninja", new ItemStack(Material.IRON_SWORD), "Be Fast and Jump High with Kit Ninja!", 6);
	}

	public void wear(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		sword.addEnchantment(Enchantment.KNOCKBACK, 2);
		player.getInventory().addItem(sword);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
		addSoup(player.getInventory(), 1, 35);
	}
}
