package net.climaxmc.KitPvp.Kits;

import java.util.concurrent.TimeUnit;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Utils.Ability;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronGolemKit extends Kit {
	Ability launch = new Ability(4);
	
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
					if (launch.tryUse(player)) {
						target.setVelocity(new Vector(0, 1.2, 0));
					} else {
						player.sendMessage("§7Wait §c" + launch.getStatus(player).getRemainingTime(TimeUnit.SECONDS) + " §7seconds before using launch!");
					}
				}
			}
		}
	}
}
