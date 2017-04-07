package net.climaxmc.KitPvp.Commands;

import com.google.common.collect.Iterables;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.BlockUtils;
import net.climaxmc.KitPvp.Utils.HologramFile;
import net.climaxmc.common.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatsHologramCommand implements CommandExecutor, Listener {

    private ClimaxPvp plugin;
    public StatsHologramCommand(ClimaxPvp plugin) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateHolograms();
            }
        }, 0L, 20L * 10);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        showHologram(event.getPlayer());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 2 && args[0].equals("create")) {

            HologramFile hologramFile = new HologramFile(plugin);
            hologramFile.saveLocation(args[1], player.getLocation());

            showHologram(player);

        } else if (args.length == 1 && args[0].equals("removeall")) {

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &7All holograms deleted!"));

        } else if (args.length == 1 && args[0].equals("list")) {
            HologramFile hologramFile = new HologramFile(plugin);
            player.sendMessage(hologramFile.getNames().toString());
        } else {
            player.sendMessage("/statshologram { create [name], removeall, list }");
        }

        return false;
    }

    private void showHologram(Player player) {

        MySQL mySQL = plugin.getMySQL();

        ResultSet killsSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `kills`+0 DESC LIMIT 5");
        Map<UUID, Integer> topKills = new LinkedHashMap<>();

        try {
            while (killsSet.next()) {
                topKills.put(UUID.fromString(killsSet.getString("uuid")), killsSet.getInt("kills"));
            }
        } catch (SQLException e) {
            //
        }

        List<String> lines = new ArrayList<>();

        lines.add(ChatColor.translateAlternateColorCodes('&', "&7&m--&f &b&lLeaderboards &7&m--&f"));
        lines.add(null);
        lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 Kills:"));

        int colorChangerCount = 0;
        for (UUID uuids : topKills.keySet()) {
            lines.add(ChatColor.translateAlternateColorCodes('&', "&b" + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                    + (colorChangerCount++ == 0 ? "&c" : "&f") + topKills.get(uuids)));
        }

        HologramFile hologramFile = new HologramFile(plugin);

        for (String hologramNames : hologramFile.getNames()) {
            /*HoloAPI holoAPI = HoloAPI.newInstance(hologramFile.getLocation(hologramNames), lines);
            holoAPI.display(player);*/
        }
    }

    private int statsSwitchInt = 0;

    private void updateHolograms() {

    }
}
