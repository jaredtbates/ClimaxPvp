package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.Utils.Cooldowns;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronGolemKit extends Kit {
	
	public IronGolemKit() {
		super("Iron Golem", new ItemStack(Material.RED_ROSE), "Launch people in the air with Iron Golem!", KitType.DEFAULT);
	}

	public void wear(Player player) {
		ItemStack sword = new ItemStack(Material.GOLD_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		sword.addEnchantment(Enchantment.DURABILITY, 2);
		player.getInventory().addItem(sword);
		player.getInventory().addItem(new ItemStack(Material.RED_ROSE));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		player.getInventory().setBoots(boots);
		addSoup(player.getInventory(), 2, 35);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (event.getEntity() instanceof Player) {
				Player target = (Player) event.getEntity();
				if (player.getItemInHand().getType().equals(Material.RED_ROSE)) {
					event.setCancelled(true);
					if (Cooldowns.tryCooldown(player, "GolemRose", 4)) {
						target.setVelocity(new Vector(0, 1.2, 0));
					} else {
						player.sendMessage("§7Wait §c" + Cooldowns.getCooldown(player, "Rose") + " §7seconds before using launch!");
					}
				}
			}
		}
	}
}
