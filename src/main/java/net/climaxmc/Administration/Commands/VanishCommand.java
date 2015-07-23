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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.UUID;

public class VanishCommand implements CommandExecutor, Listener {
    private ClimaxPvp plugin;
    @Getter
    private static HashSet<UUID> vanished = new HashSet<>();

    public VanishCommand(ClimaxPvp plugin) {
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

        if (!vanished.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.GREEN + "You are now vanished.");
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0.18F);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
            plugin.getServer().broadcastMessage(ChatColor.RED + "Quit" + ChatColor.DARK_GRAY + "\u00bb " + player.getName());
            player.setPlayerListName(null);
            vanished.add(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.RED + "You are no longer vanished.");
            plugin.respawn(player);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));
            plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA + "Join" + ChatColor.DARK_GRAY + "\u00bb " + player.getName());
            player.setPlayerListName(playerData.getLevelColor() + player.getName());
            vanished.remove(player.getUniqueId());
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

        if (vanished.contains(damaged.getUniqueId())) {
            damaged.sendMessage(ChatColor.RED + "You are being attacked while vanished.");
            damaged.sendMessage(ChatColor.RED + "The only reason why this would occur is if you used /spawn before turning off vanish with /v.");
            damaged.performCommand("/v");
        }

        if (vanished.contains(damager.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        vanished.stream().filter(vanishedPlayer -> plugin.getServer().getPlayer(vanishedPlayer) != null).forEach(vanishedPlayer -> player.hidePlayer(plugin.getServer().getPlayer(vanishedPlayer)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (vanished.contains(player.getUniqueId())) {
            vanished.remove(player.getUniqueId());
        }
    }
}
