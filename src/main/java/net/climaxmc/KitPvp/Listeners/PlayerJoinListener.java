package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoinListener implements Listener {
    ClimaxPvp plugin;

    public PlayerJoinListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getMySQL().createPlayerData(player);
        PlayerData playerData = plugin.getPlayerData(player);

        event.setJoinMessage("§3Join§8» " + player.getName());

        plugin.respawn(player);

        ClimaxPvp.getInstance().getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>());

        player.setDisplayName(playerData.getNickname());
    }
}
