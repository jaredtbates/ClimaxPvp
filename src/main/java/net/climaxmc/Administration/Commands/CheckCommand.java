package net.climaxmc.Administration.Commands;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.UUID;

public class CheckCommand implements CommandExecutor, Listener {
    private ClimaxPvp plugin;
    @Getter
    private static HashSet<UUID> checking = new HashSet<>();

    public CheckCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        CachedPlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.MODERATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (!checking.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.GREEN + "You are now checking a player.");
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            checking.add(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.RED + "You are no longer checking a player.");
            plugin.respawn(player);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            checking.remove(player.getUniqueId());
        }

        return true;
    }

    @EventHandler
    public void onPlayerHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))) {
            return;
        }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (checking.contains(damaged.getUniqueId())) {
            damaged.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are being attacked by " + damager.getName() + "!");
            event.setCancelled(true);
        }
    }
}
