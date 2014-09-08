package net.climaxmc.KitPvp.KitPvp.Kits;

import net.jaredbates.mc.ClimaxKits.Main;
import net.jaredbates.mc.ClimaxKits.Utils.Soup;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class IronGolemKit {
	Main plugin;

	public IronGolemKit(Main plugin) {
		this.plugin = plugin;
	}

	public static void wear(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.getInventory().addItem(new ItemStack(Material.GOLD_SWORD));
		player.getInventory().addItem(new ItemStack(Material.RED_ROSE));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		player.getInventory().setBoots(boots);
		Soup.add(player.getInventory(), 2, 35);
		player.sendMessage("§6You have chosen §aIronGolem");
	}
}
