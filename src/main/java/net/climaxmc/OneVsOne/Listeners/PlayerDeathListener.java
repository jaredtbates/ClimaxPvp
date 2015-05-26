package net.climaxmc.OneVsOne.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerDeathListener implements Listener {
    private ClimaxPvp plugin;
    private OneVsOne instance;

    public PlayerDeathListener(ClimaxPvp plugin, OneVsOne instance) {
        this.plugin = plugin;
        this.instance = instance;
    }

    //@EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Location lobbySpawn = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("Lobby.Spawn.World")), plugin.getConfig().getInt("Lobby.Spawn.X"), plugin.getConfig().getInt("Lobby.Spawn.Y"), plugin.getConfig().getInt("Lobby.Spawn.Z"));
        final Player player = event.getEntity();
        if (instance.getChallenged().containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                public void run() {
                    player.teleport(lobbySpawn);
                    ItemStack stick = new ItemStack(Material.STICK);
                    ItemMeta stickmeta = stick.getItemMeta();
                    stickmeta.setDisplayName("§6§lRegular 1v1");
                    stick.setItemMeta(stickmeta);
                    player.getInventory().clear();
                    player.getInventory().addItem(stick);
                    player.sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 lobby!");
                    player.getKiller().teleport(lobbySpawn);
                    player.getKiller().getInventory().clear();
                    player.getKiller().getInventory().addItem(stick);
                    player.getKiller().sendMessage("§0§l[§6§l1v1§0§l] §7Teleported to the 1v1 lobby!");
                    instance.getChallenged().remove(player.getUniqueId());
                    instance.getChallenged().remove(player.getKiller().getUniqueId());
                }
            });
        }
    }
}
