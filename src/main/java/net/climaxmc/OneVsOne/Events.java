package net.climaxmc.OneVsOne;

import net.climaxmc.ClimaxPvp;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

/**
 * DO NOT USE - ARCHIVED FOR FUTURE REFERENCE
 *
 * @deprecated
 */
@Deprecated
public class Events implements Listener {
    ClimaxPvp plugin;
    int taskid;
    ArrayList<UUID> inCountdown = new ArrayList<UUID>();
    public Events(ClimaxPvp plugin) {
        this.plugin = plugin;
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
				clicked.sendMessage("§aYou have been challenged by " + p.getUniqueId() + ", right click them to accept!");
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
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
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
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
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
						    p.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
						    clicked.sendMessage("§0§l[§6§l1v1§0§l] §7Starting in " + time + " seconds!");
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
				clicked.sendMessage("§0§l[§6§l1v1§0§l] §cYou declined " + p.getName() + "'s 1v1 request!");
				clicked.closeInventory();
			}
		}
	} */
}