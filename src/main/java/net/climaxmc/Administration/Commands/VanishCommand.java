package net.climaxmc.Administration.Commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class VanishCommand implements CommandExecutor, Listener {
    @Getter
    private static HashSet<UUID> vanished = new HashSet<>();
    private ClimaxPvp plugin;

    public VanishCommand(ClimaxPvp plugin) {
        this.plugin = plugin;

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL,
                Collections.singletonList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
            @Override
            public void onPacketSending(PacketEvent event) {
                handlePing(event.getPacket().getServerPings().read(0));
            }
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.MODERATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (!vanished.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.GREEN + "You are now vanished.");
            player.setAllowFlight(true);
            player.setFlying(true);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
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

    private void handlePing(WrappedServerPing ping) {
        Set<WrappedGameProfile> players = new HashSet<>();
        players.addAll(ping.getPlayers());
        players.removeIf(player -> vanished.contains(player.getUUID()));
        ping.setPlayers(players);
        ping.setPlayersOnline(players.size());
    }
}
