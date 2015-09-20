package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerRespawnListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerRespawnListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getRegainReason() == RegainReason.SATIATED)
            event.setCancelled(true);
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
        player.setFoodLevel(20);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1F);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.getInventory().setItem(0, new I(Material.NETHER_STAR)
                .name(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit Selector")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Select from a variety")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "of different kits!"));

        if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
            Kit kit = KitManager.getPreviousKit().get(player.getUniqueId());
            player.getInventory().setItem(1, new I(Material.REDSTONE)
                    .name(ChatColor.GREEN + "Previous Kit: " + kit.getColor() + kit.getName())
                    .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Equip your previous kit."));
        }

        player.getInventory().setItem(7, new I(Material.DIAMOND)
                .name(ChatColor.YELLOW + "" + ChatColor.BOLD + "Challenges")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "View your challenges,")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "start new ones, and receive rewards!"));

        player.getInventory().setItem(8, new I(Material.SEEDS)
                .name(ChatColor.GREEN + "" + ChatColor.BOLD + "Trail Selector")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Select a cool looking trail!"));

    }
}
