package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PvpKit extends Kit {
	public PvpKit() {
		super("Pvp", new ItemStack(Material.DIAMOND_SWORD), "A Standard Pvp Kit, Iron Armor and Diamond Sword.", 0);
	}

	public void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		player.getInventory().addItem(sword);
		addSoup(player.getInventory(), 1, 35);
		player.sendMessage("§6You have chosen §aPvp");
	}
}