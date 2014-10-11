package net.climaxmc.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.climaxmc.Main;
import net.climaxmc.Utils.Title;

import org.bukkit.BanList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AsyncPlayerChatListener implements Listener {
	Main plugin;
	Title incorrect2Tries = new Title("§4§lINCORRECT PASSWORD", "§42 tries remaining", 0, 12000, 0);
	Title incorrect1Try = new Title("§4§lINCORRECT PASSWORD", "§41 try remaining", 0, 12000, 0);
	HashMap<UUID, Integer> incorrectPassword = new HashMap<UUID, Integer>();
	
	public AsyncPlayerChatListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (plugin.inPassword.contains(player.getUniqueId())) {
			event.setCancelled(true);
			if (event.getMessage().equals("test")) {
				plugin.inPassword.remove(player.getUniqueId());
				incorrect2Tries.clearTitle(player);
				ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
				ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
				kitSelectorMeta.setDisplayName("§a§lKit Selector");
				List<String> kitSelectorLores = new ArrayList<String>();
				kitSelectorLores.add("§5§o(Right Click) to select a kit!");
				kitSelectorMeta.setLore(kitSelectorLores);
				kitSelector.setItemMeta(kitSelectorMeta);
				player.getInventory().setItem(0, kitSelector);
			} else {
				if (incorrectPassword.get(player.getUniqueId()) == 3) {
					incorrectPassword.put(player.getUniqueId(), 2);
					incorrect2Tries.send(player);
				} else if (incorrectPassword.get(player.getUniqueId()) == 2) {
					incorrectPassword.put(player.getUniqueId(), 1);
					incorrect1Try.send(player);
				} else if (incorrectPassword.get(player.getUniqueId()) == 1) {
					incorrectPassword.remove(player.getUniqueId());
					plugin.getServer().getBanList(BanList.Type.IP).addBan(player.getAddress().getAddress().getHostAddress().toString(), "§c§lINCORRECT PASSWORD!\n§cContact computerwizjared or joshua.cornett3 on Skype if you think this is a mistake!", null, player.getName());
				} else {
					incorrectPassword.put(player.getUniqueId(), 3);
				}
			}
		}
	}
}
