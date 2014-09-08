package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class IronGolemKit extends Kit {
	public IronGolemKit() {
		super("Iron Golem", new ItemStack(Material.RED_ROSE));
	}

	public void wear(Player player) {
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
		addSoup(player.getInventory(), 2, 35);
		player.sendMessage("§6You have chosen §aIronGolem");
	}
}
