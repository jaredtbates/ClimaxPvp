package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
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

        for(Player players : Bukkit.getServer().getOnlinePlayers()){
            players.showPlayer(player);
        }

        ClimaxPvp.deadPeoples.remove(player);

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

        if (VanishCommand.getVanished().contains(player.getUniqueId())) {
            player.setAllowFlight(true);
            player.setFlying(true);
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

        player.getInventory().setItem(7, new I(Material.WATCH)
                .name(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Settings")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Customize your experience here at Climax!"));

        player.getInventory().setItem(8, new I(Material.SEEDS)
                .name(ChatColor.AQUA + "" + ChatColor.BOLD + "Trail Selector")
                .lore(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Select a cool looking trail!"));

        /*ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(player.getName());
        meta.setDisplayName(ChatColor.GREEN + "Profile Menu");
        head.setItemMeta(meta);

        player.getInventory().setItem(2, head);*/
    }
}
