package net.climaxmc.KitPvp.Menus;// AUTHOR: gamer_000 (8/10/2015)

import net.climaxmc.KitPvp.Utils.ChallengesFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CratesMenu implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null) {
            if (item.getType().equals(Material.BEDROCK)) {
                p.sendMessage(ChatColor.RED + "Coming soon!");
                p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1, 1);
            }
        }
    }
}
