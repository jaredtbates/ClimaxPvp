package net.climaxmc.Administration.Commands;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class VanishCommand implements CommandExecutor, Listener {

    private ClimaxPvp plugin;

    public VanishCommand(ClimaxPvp plugin) {
        this.plugin = plugin;

        /*plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (plugin.getServer().getOnlinePlayers().size() <= 0) {
                return;
            }

            ByteArrayDataOutput bos = ByteStreams.newDataOutput();
            bos.writeUTF("ClimaxVanish");
            for (UUID vanishedUUID : vanished) {
                bos.writeUTF(vanishedUUID.toString());
            }
            Iterables.get(plugin.getServer().getOnlinePlayers(), 1).sendPluginMessage(plugin, "BungeeCord", bos.toByteArray());
        }, 200, 200);*/
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

        toggleVanish(player, playerData);

        return true;
    }

    @EventHandler
    public void onPlayerHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))) {
            return;
        }

        Player damaged = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (KitPvp.getVanished().contains(damaged.getUniqueId())) {
            damaged.sendMessage(ChatColor.RED + "You are being attacked while vanished.");
            damaged.sendMessage(ChatColor.RED + "The only reason why this would occur is if you used /spawn before turning off vanish with /v.");
            damaged.performCommand("/v");
        }

        if (KitPvp.getVanished().contains(damager.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        KitPvp.getVanished().stream().filter(vanishedPlayer ->
                plugin.getServer().getPlayer(vanishedPlayer) != null)
                .forEach(vanishedPlayer -> player.hidePlayer(plugin.getServer().getPlayer(vanishedPlayer)));

        PlayerData playerData = plugin.getPlayerData(player);
        if (playerData.hasRank(Rank.MODERATOR)) {
            toggleVanish(player, playerData);
            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (KitPvp.getVanished().contains(player.getUniqueId())) {
            KitPvp.getVanished().remove(player.getUniqueId());
        }
    }

    public void toggleVanish(Player player, PlayerData playerData) {
        if (!KitPvp.getVanished().contains(player.getUniqueId())) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are now vanished.");
            player.setAllowFlight(true);
            player.setFlying(true);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
            player.setPlayerListName(null);
            KitPvp.getVanished().add(player.getUniqueId());
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are no longer vanished.");
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));
            plugin.getServer().broadcastMessage(ChatColor.DARK_AQUA + "Join" + ChatColor.DARK_GRAY + "\u00bb " + player.getName());
            String rankTag = "";

            if (playerData.hasRank(Rank.NINJA)) {
                rankTag = org.bukkit.ChatColor.DARK_GRAY + "" + org.bukkit.ChatColor.BOLD + "[" + playerData.getRank().getColor()
                        + org.bukkit.ChatColor.BOLD + playerData.getRank().getPrefix() + org.bukkit.ChatColor.DARK_GRAY + "" + org.bukkit.ChatColor.BOLD + "] ";
            }
            player.setPlayerListName(rankTag + playerData.getLevelColor() + player.getName());

            KitPvp.getVanished().remove(player.getUniqueId());
            plugin.respawn(player);
        }
    }
}
