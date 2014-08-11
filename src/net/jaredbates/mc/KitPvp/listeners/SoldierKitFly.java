package net.jaredbates.mc.KitPvp.listeners;

import net.jaredbates.mc.KitPvp.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class SoldierKitFly implements Listener{
	
Main plugin;
	
	public SoldierKitFly(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSoldierFly(PlayerInteractEvent e){
		Player p = (Player) e.getPlayer();
		if(plugin.soldierkit.contains(p.getName())){
			if(p.getInventory().getItemInHand().getType() == Material.IRON_SWORD){
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
					p.setVelocity(new Vector(0, 0.7, 0));
				}
			}
		}
	}
}
