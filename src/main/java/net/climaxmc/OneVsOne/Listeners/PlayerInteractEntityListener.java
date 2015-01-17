package net.climaxmc.OneVsOne.Listeners;

import java.util.ArrayList;
import java.util.Random;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import net.climaxmc.OneVsOne.RegularKit;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerInteractEntityListener implements Listener {
    private ClimaxPvp plugin;
    private OneVsOne instance;

    public PlayerInteractEntityListener(ClimaxPvp plugin, OneVsOne instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Player) {
            Player target = (Player) event.getRightClicked();
            if (player.getInventory().getItemInHand().getType() == Material.STICK) {
                if (instance.getChallenged().containsKey(target.getUniqueId()) && instance.getChallenged().containsValue(player.getUniqueId())) {
                    int random = new Random().nextInt(3);
                    if (random == 0) {
                        int x1 = plugin.getConfig().getInt("arena1.spawn1.x");
                        int y1 = plugin.getConfig().getInt("arena1.spawn1.y");
                        int z1 = plugin.getConfig().getInt("arena1.spawn1.z");
                        player.teleport(new Location(player.getWorld(), x1, y1, z1));
                        int x2 = plugin.getConfig().getInt("arena1.spawn2.x");
                        int y2 = plugin.getConfig().getInt("arena1.spawn2.y");
                        int z2 = plugin.getConfig().getInt("arena1.spawn2.z");
                        target.teleport(new Location(player.getWorld(), x2, y2, z2));
                        player.getInventory().clear();
                        target.getInventory().clear();
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                            target.removePotionEffect(effect.getType());
                        }
                        RegularKit.wear(player);
                        RegularKit.wear(target);
                        player.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + target.getName());
                        target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + player.getName());
                    } else if (random == 1) {
                        int x1 = plugin.getConfig().getInt("arena2.spawn1.x");
                        int y1 = plugin.getConfig().getInt("arena2.spawn1.y");
                        int z1 = plugin.getConfig().getInt("arena2.spawn1.z");
                        player.teleport(new Location(player.getWorld(), x1, y1, z1));
                        int x2 = plugin.getConfig().getInt("arena2.spawn2.x");
                        int y2 = plugin.getConfig().getInt("arena2.spawn2.y");
                        int z2 = plugin.getConfig().getInt("arena2.spawn2.z");
                        target.teleport(new Location(player.getWorld(), x2, y2, z2));
                        player.getInventory().clear();
                        target.getInventory().clear();
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                            target.removePotionEffect(effect.getType());
                        }
                        RegularKit.wear(player);
                        RegularKit.wear(target);
                        player.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + target.getName());
                        target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + player.getName());
                    } else if (random == 2) {
                        int x1 = plugin.getConfig().getInt("arena3.spawn1.x");
                        int y1 = plugin.getConfig().getInt("arena3.spawn1.y");
                        int z1 = plugin.getConfig().getInt("arena3.spawn1.z");
                        player.teleport(new Location(player.getWorld(), x1, y1, z1));
                        int x2 = plugin.getConfig().getInt("arena3.spawn2.x");
                        int y2 = plugin.getConfig().getInt("arena3.spawn2.y");
                        int z2 = plugin.getConfig().getInt("arena3.spawn2.z");
                        target.teleport(new Location(player.getWorld(), x2, y2, z2));
                        player.getInventory().clear();
                        target.getInventory().clear();
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                            target.removePotionEffect(effect.getType());
                        }
                        RegularKit.wear(player);
                        RegularKit.wear(target);
                        player.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + target.getName());
                        target.sendMessage("§0§l[§6§l1v1§0§l] §7You have entered a regular 1v1 with " + player.getName());
                    }
                } else {
                    instance.getChallenged().put(player.getUniqueId(), target.getUniqueId());
                    player.sendMessage("§0§l[§6§l1v1§0§l] §aYou have challenged " + target.getName() + " to a regular 1v1!");
                    target.sendMessage("§0§l[§6§l1v1§0§l] §aYou have been challenged by " + player.getName() + " to a Regular 1v1! Right click them to accept!");
                }
            }
        }
    }
}
