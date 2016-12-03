package net.climaxmc.Administration.Commands;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.UUID;

public class CheckCommand implements CommandExecutor, Listener {

    private ClimaxPvp plugin;

    public CheckCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (!KitPvp.getChecking().contains(player.getUniqueId())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are now checking a player.");
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            KitPvp.getChecking().add(player.getUniqueId());
            if (args.length == 1) {
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target != null) {
                    player.teleport(target);
                } else {
                    player.sendMessage(ChatColor.RED + "That player is not online!");
                }
            }
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are no longer checking a player.");
            plugin.respawn(player);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            KitPvp.getChecking().remove(player.getUniqueId());
        }

        return true;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER))) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (KitPvp.getChecking().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    int count = 0;

    @EventHandler
    public void onPlayerHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))) {
            return;
        }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (KitPvp.getChecking().contains(damaged.getUniqueId())) {
            if (count == 0) {
                clicksPerSecond(damager, damaged);
            }
            count++;
            event.setCancelled(true);
        }

        if (KitPvp.getChecking().contains(damager.getUniqueId())) {
            event.setCancelled(true);
        }
    }
    public void clicksPerSecond(Player damager, Player damaged) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                damaged.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are being attacked by " + damager.getName() + " at " + count + " CPS!");
                count = 0;
            }
        }, 20L);
    }
}
