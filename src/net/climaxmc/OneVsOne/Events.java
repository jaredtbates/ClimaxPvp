package net.climaxmc.OneVsOne;

import java.util.ArrayList;
import java.util.UUID;

import net.climaxmc.Main;
import net.climaxmc.Utils.Cooldowns;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Events implements Listener {
	Main plugin;

	public Events(Main plugin) {
		this.plugin = plugin;
	}

	int taskid;
	ArrayList<Integer> a1 = new ArrayList<Integer>();
	ArrayList<Integer> a2 = new ArrayList<Integer>();
	ArrayList<Integer> a3 = new ArrayList<Integer>();
	ArrayList<UUID> inCountdown = new ArrayList<UUID>();

	@EventHandler
	public void onPlayerClick(PlayerInteractEntityEvent event){
		Player p = event.getPlayer();
		if (event.getRightClicked() instanceof Player) {
			Player target = (Player) event.getRightClicked();
			if(OneVsOne.in1v1.contains(target.getUniqueId())){
				if(p.getInventory().getItemInHand().getType() == Material.STICK){
					if(Cooldowns.tryCooldown(p, "Challenge", 5)){
						if(OneVsOne.challenged.containsKey(target.getUniqueId()) && OneVsOne.challenged.containsValue(p.getUniqueId())){
							if(a1.size() == a2.size() && a2.size() == a3.size()){
								a1.add(1);
								int x1 = plugin.getConfig().getInt("arena1.spawn1.x");
								int y1 = plugin.getConfig().getInt("arena1.spawn1.y");
								int z1 = plugin.getConfig().getInt("arena1.spawn1.z");
								p.teleport(new Location(p.getWorld(), x1, y1, z1));
								int x2 = plugin.getConfig().getInt("arena1.spawn2.x");
								int y2 = plugin.getConfig().getInt("arena1.spawn2.y");
								int z2 = plugin.getConfig().getInt("arena1.spawn2.z");
								target.teleport(new Location(p.getWorld(), x2, y2, z2));
								p.getInventory().clear();
								target.getInventory().clear();
								for (PotionEffect effect : p.getActivePotionEffects()){
									p.removePotionEffect(effect.getType());
									target.removePotionEffect(effect.getType());
								}
								RegularKit.wear(p);
								RegularKit.wear(target);
								p.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + target.getName());
								target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + p.getName());
							}else if(a1.size() > a2.size()){
								a2.add(1);
								int x1 = plugin.getConfig().getInt("arena2.spawn1.x");
								int y1 = plugin.getConfig().getInt("arena2.spawn1.y");
								int z1 = plugin.getConfig().getInt("arena2.spawn1.z");
								p.teleport(new Location(p.getWorld(), x1, y1, z1));
								int x2 = plugin.getConfig().getInt("arena2.spawn2.x");
								int y2 = plugin.getConfig().getInt("arena2.spawn2.y");
								int z2 = plugin.getConfig().getInt("arena2.spawn2.z");
								target.teleport(new Location(p.getWorld(), x2, y2, z2));
								p.getInventory().clear();
								target.getInventory().clear();
								for (PotionEffect effect : p.getActivePotionEffects()){
									p.removePotionEffect(effect.getType());
									target.removePotionEffect(effect.getType());
								}
								RegularKit.wear(p);
								RegularKit.wear(target);
								p.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + target.getName());
								target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + p.getName());
							}else if(a2.size() > a3.size()){
								a3.add(1);
								int x1 = plugin.getConfig().getInt("arena3.spawn1.x");
								int y1 = plugin.getConfig().getInt("arena3.spawn1.y");
								int z1 = plugin.getConfig().getInt("arena3.spawn1.z");
								p.teleport(new Location(p.getWorld(), x1, y1, z1));
								int x2 = plugin.getConfig().getInt("arena3.spawn2.x");
								int y2 = plugin.getConfig().getInt("arena3.spawn2.y");
								int z2 = plugin.getConfig().getInt("arena3.spawn2.z");
								target.teleport(new Location(p.getWorld(), x2, y2, z2));
								p.getInventory().clear();
								target.getInventory().clear();
								for (PotionEffect effect : p.getActivePotionEffects()){
									p.removePotionEffect(effect.getType());
									target.removePotionEffect(effect.getType());
								}
								RegularKit.wear(p);
								RegularKit.wear(target);
								p.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + target.getName());
								target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a Regular 1v1 with " + p.getName());
							}
						}else{
							OneVsOne.challenged.put(p.getUniqueId(), target.getUniqueId());
							p.sendMessage("§0§l[§6§l1v1§0§l] §aYou have challenged " + target.getName() + " to a Regular 1v1!");
							target.sendMessage("§0§l[§6§l1v1§0§l] §aYou have been challenged by " + p.getName() + " to a Regular 1v1! Right Click them to Accept!");
						}
					}else{
						p.sendMessage("§0§l[§6§l1v1§0§l] §cYou must wait " + Cooldowns.getCooldown(p, "Challenge") + " seconds to Challenge them again!");
					}
				}
			}
		}
	}
	/* @EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		final Player p = (Player) e.getWhoClicked();
		final Player clicked = plugin.getServer().getPlayer(plugin.challenged.get(p.getUniqueId()));
		ItemStack clickedItem = e.getCurrentItem();
		if(e.getInventory().getName() == "§6§lChallenge!"){
			e.setCancelled(true);
			if(clickedItem.getItemMeta().getDisplayName() == "§a§lChallenge"){
				plugin.challenged.put(p.getUniqueId(), clicked.getName());
				p.sendMessage("§0§l[§6§l1v1§0§l] §aYou have challenged " + clicked.getName());
				clicked.sendMessage("§aYou have been challenged by " + p.getUniqueId() + ", right click them to Accept!");
				p.closeInventory();
			} else if(clickedItem.getItemMeta().getDisplayName() == "§c§lCancel"){
				plugin.challenged.remove(p.getUniqueId());
				plugin.challenged.remove(clicked.getName());
				p.sendMessage("§0§l[§6§l1v1§0§l] §c§lYou have cancelled the challenge");
			    p.closeInventory();
			}
		}
		if(e.getInventory().getName() == "§6§lAccept!"){
			e.setCancelled(true);
			if(clickedItem.getItemMeta().getDisplayName() == "§a§lAccept"){
				if(a1.size() == a2.size() && a2.size() == a3.size()){
					a1.add(1);
					int x1 = plugin.getConfig().getInt("arena1.spawn1.x");
					int y1 = plugin.getConfig().getInt("arena1.spawn1.y");
					int z1 = plugin.getConfig().getInt("arena1.spawn1.z");
					p.teleport(new Location(p.getWorld(), x1, y1, z1));
					int x2 = plugin.getConfig().getInt("arena1.spawn2.x");
					int y2 = plugin.getConfig().getInt("arena1.spawn2.y");
					int z2 = plugin.getConfig().getInt("arena1.spawn2.z");
					clicked.teleport(new Location(p.getWorld(), x2, y2, z2));
					inCountdown.add(p.getUniqueId());
					inCountdown.add(clicked.getName());
					taskid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
						  int time = 3;
						  public void run() {
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    time--;
						    if(time == 0){
						    	inCountdown.remove(p.getUniqueId());
						    	inCountdown.remove(clicked.getName());
						    	plugin.getServer().getScheduler().cancelTask(taskid);
						    }
						  }
						}, 20L, 20L);
				}else if(a1.size() > a2.size()){
					a2.add(1);
					int x1 = plugin.getConfig().getInt("arena2.spawn1.x");
					int y1 = plugin.getConfig().getInt("arena2.spawn1.y");
					int z1 = plugin.getConfig().getInt("arena2.spawn1.z");
					p.teleport(new Location(p.getWorld(), x1, y1, z1));
					int x2 = plugin.getConfig().getInt("arena2.spawn2.x");
					int y2 = plugin.getConfig().getInt("arena2.spawn2.y");
					int z2 = plugin.getConfig().getInt("arena2.spawn2.z");
					clicked.teleport(new Location(p.getWorld(), x2, y2, z2));
					inCountdown.add(p.getUniqueId());
					inCountdown.add(clicked.getName());
					taskid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
						  int time = 3;
						  public void run() {
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    time--;
						    if(time == 0){
						    	inCountdown.remove(p.getUniqueId());
						    	inCountdown.remove(clicked.getName());
						    	plugin.getServer().getScheduler().cancelTask(taskid);
						    }
						  }
						}, 20L, 20L);
				}else if(a2.size() > a3.size()){
					a3.add(1);
					int x1 = plugin.getConfig().getInt("arena3.spawn1.x");
					int y1 = plugin.getConfig().getInt("arena3.spawn1.y");
					int z1 = plugin.getConfig().getInt("arena3.spawn1.z");
					p.teleport(new Location(p.getWorld(), x1, y1, z1));
					int x2 = plugin.getConfig().getInt("arena3.spawn2.x");
					int y2 = plugin.getConfig().getInt("arena3.spawn2.y");
					int z2 = plugin.getConfig().getInt("arena3.spawn2.z");
					clicked.teleport(new Location(p.getWorld(), x2, y2, z2));
					inCountdown.add(p.getUniqueId());
					inCountdown.add(clicked.getName());
					taskid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
						  int time = 3;
						  public void run() {
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + "seconds!");
						    time--;
						    if(time == 0){
						    	inCountdown.remove(p.getUniqueId());
						    	inCountdown.remove(clicked.getName());
						    	plugin.getServer().getScheduler().cancelTask(taskid);
						    }
						  }
						}, 20L, 20L);
				}
			}
			if(clickedItem.getItemMeta().getDisplayName() == "§c§lDecline"){
				p.sendMessage("§0§l[§6§l1v1§0§l] §cPlayer " + clicked.getName() + " declined your 1v1 request!");
				clicked.sendMessage("§0§l[§6§l1v1§0§l] §cYou declined " + p.getUniqueId() + "'s 1v1 request!");
				clicked.closeInventory();
			}
		}
	} */

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		final Player player = event.getEntity();
		if (player instanceof Player) {
			plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
				public void run() {
					if(OneVsOne.in1v1.contains(player.getUniqueId())){
						OneVsOne.in1v1.remove(player.getUniqueId());
						int x = plugin.getConfig().getInt("lobbyspawn.x");
						int y = plugin.getConfig().getInt("lobbyspawn.y");
						int z = plugin.getConfig().getInt("lobbyspawn.z");
						player.teleport(new Location(player.getWorld(), x, y, z));
					} else if(OneVsOne.challenged.containsKey(player.getUniqueId())) {
						int x = plugin.getConfig().getInt("lobbyspawn.x");
						int y = plugin.getConfig().getInt("lobbyspawn.y");
						int z = plugin.getConfig().getInt("lobbyspawn.z");
						player.teleport(new Location(player.getWorld(), x, y, z));
						ItemStack stick = new ItemStack(Material.STICK);
						ItemMeta stickmeta = stick.getItemMeta();
						stickmeta.setDisplayName("§6§lRegular 1v1");
						stick.setItemMeta(stickmeta);
						OneVsOne.in1v1.add(player.getUniqueId());
						player.getInventory().clear();
						player.getInventory().addItem(stick);
						player.sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 Lobby!");
						OneVsOne.in1v1.add(player.getKiller().getUniqueId());
						player.getKiller().getInventory().clear();
						player.getKiller().getInventory().addItem(stick);
						player.getKiller().sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 Lobby!");
						player.getKiller().teleport(new Location(player.getWorld(), x, y, z));
					}
				}
			});
		}
	}
}