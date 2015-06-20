package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PlayerRespawnListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerRespawnListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20F);
        player.setMaxHealth(20F);
        player.setFlySpeed(0.1F);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        kitSelectorMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit Selector");
        List<String> kitSelectorLores = new ArrayList<String>();
        kitSelectorLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "(Right Click) to select a kit!");
        kitSelectorMeta.setLore(kitSelectorLores);
        kitSelector.setItemMeta(kitSelectorMeta);

        ItemStack particles = new ItemStack(Material.SEEDS);
        ItemMeta particlesMeta = particles.getItemMeta();
        particlesMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Trail Selector");
        List<String> particlesLores = new ArrayList<String>();
        particlesLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "(Right Click) to select a trail!");
        particlesMeta.setLore(particlesLores);
        particles.setItemMeta(particlesMeta);

        player.getInventory().setItem(0, kitSelector);
        player.getInventory().setItem(8, particles);
    }
}
