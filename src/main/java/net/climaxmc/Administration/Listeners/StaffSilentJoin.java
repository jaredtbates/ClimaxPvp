package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StaffSilentJoin implements Listener {

    private ClimaxPvp plugin;
    public StaffSilentJoin(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);

        if (playerData.hasRank(Rank.TRIAL_MODERATOR)) {

            player.setPlayerListName(null);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Joined silently.");
            player.setAllowFlight(true);
            player.setFlying(true);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
            player.getInventory().clear();
            player.getInventory().setItem(0, new I(Material.INK_SACK).durability(8).name(ChatColor.AQUA + "Join Server"));
            KitPvp.getVanished().add(player.getUniqueId());

            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);
        if (event.getItem() != null) {
            if (event.getItem().getItemMeta().getDisplayName() != null) {
                if (event.getItem().getItemMeta().getDisplayName().contains("Join Server")) {

                    player.setGameMode(GameMode.SURVIVAL);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));
                    String rankTag = "";
                    if (playerData.hasRank(Rank.NINJA)) {
                        player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
                    }
                    if (playerData.getRank().getColor() != null) {
                        player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
                    } else {
                        player.setPlayerListName(playerData.getLevelColor() + player.getName());
                    }

                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Join" + ChatColor.DARK_GRAY + "\u00BB " + player.getName());

                    KitPvp.getVanished().remove(player.getUniqueId());
                    plugin.respawn(player);
                }
            }
        }
    }
}
