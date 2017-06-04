package net.climaxmc.KitPvp.Utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.MySQL;
import net.climaxmc.common.database.Rank;
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

public class StatsHologram implements CommandExecutor, Listener {

    private ClimaxPvp plugin;
    public StatsHologram(ClimaxPvp plugin) {
        this.plugin = plugin;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateHolograms();
            }
        }, 0L, 20L * 60);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        updateHolograms();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if (!plugin.getPlayerData(player).hasRank(Rank.ADMINISTRATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length == 1 && args[0].equals("create")) {

            createHologram(player.getLocation());

        } else if (args.length == 1 && args[0].equals("removeall")) {

            for (Hologram holograms : HologramsAPI.getHolograms(plugin)) {
                holograms.delete();
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &7All holograms deleted!"));

        } else {
            player.sendMessage("/statshologram { create, removeall }");
        }

        return false;
    }

    private Map<Hologram, Integer> statsSwitchInt = new HashMap<>();

    private void updateHolograms() {
        for (Hologram hologram : HologramsAPI.getHolograms(plugin)) {

            if (!statsSwitchInt.containsKey(hologram)) {
                statsSwitchInt.put(hologram, 0);
            }

            MySQL mySQL = plugin.getMySQL();

            List<String> lines = new ArrayList<>();

            lines.add(ChatColor.translateAlternateColorCodes('&', "&7&m--&f &c&lLeaderboards &7&m--&f"));
            lines.add(null);
            if (statsSwitchInt.get(hologram) == 0) {

                /**
                 * Top 5 kills
                 */
                ResultSet killsSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `kills`+0 DESC LIMIT 5");
                Map<UUID, Integer> topKills = new LinkedHashMap<>();

                try {
                    while (killsSet.next()) {
                        topKills.put(UUID.fromString(killsSet.getString("uuid")), killsSet.getInt("kills"));
                    }
                } catch (SQLException e) {
                    //
                }

                lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 &e&lKills"));

                int colorChangerCount = 0;
                for (UUID uuids : topKills.keySet()) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', (colorChangerCount++ == 0 ? "&c" : "&f") + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                            + "&f" + topKills.get(uuids)));
                }

                statsSwitchInt.put(hologram, 1);

            } else if (statsSwitchInt.get(hologram) == 1) {

                /**
                 * Top 5 deaths
                 */
                ResultSet deathsSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `deaths`+0 DESC LIMIT 5");
                Map<UUID, Integer> topDeaths = new LinkedHashMap<>();

                try {
                    while (deathsSet.next()) {
                        topDeaths.put(UUID.fromString(deathsSet.getString("uuid")), deathsSet.getInt("deaths"));
                    }
                } catch (SQLException e) {
                    //
                }

                lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 &e&lDeaths"));

                int colorChangerCount = 0;
                for (UUID uuids : topDeaths.keySet()) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', (colorChangerCount++ == 0 ? "&c" : "&f") + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                            + "&f" + topDeaths.get(uuids)));
                }

                statsSwitchInt.put(hologram, 2);

            } else if (statsSwitchInt.get(hologram) == 2) {

                /**
                 * Top 5 kdr
                 */
                ResultSet kdrSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `kdr`+0 DESC LIMIT 5");
                Map<UUID, Double> topKDR = new LinkedHashMap<>();

                try {
                    while (kdrSet.next()) {
                        topKDR.put(UUID.fromString(kdrSet.getString("uuid")), kdrSet.getDouble("kdr"));
                    }
                } catch (SQLException e) {
                    //
                }

                lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 &e&lKDR's"));

                int colorChangerCount = 0;
                for (UUID uuids : topKDR.keySet()) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', (colorChangerCount++ == 0 ? "&c" : "&f") + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                            + "&f" + topKDR.get(uuids)));
                }

                statsSwitchInt.put(hologram, 3);

            } else if (statsSwitchInt.get(hologram) == 3) {

                /**
                 * Top 5 ks
                 */
                ResultSet topksSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `topks`+0 DESC LIMIT 5");
                Map<UUID, Integer> topKS = new LinkedHashMap<>();

                try {
                    while (topksSet.next()) {
                        topKS.put(UUID.fromString(topksSet.getString("uuid")), topksSet.getInt("topks"));
                    }
                } catch (SQLException e) {
                    //
                }

                lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 &e&lKS's"));

                int colorChangerCount = 0;
                for (UUID uuids : topKS.keySet()) {
                    lines.add(ChatColor.translateAlternateColorCodes('&', (colorChangerCount++ == 0 ? "&c" : "&f") + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                            + "&f" + topKS.get(uuids)));
                }

                statsSwitchInt.put(hologram, 0);

            }

            hologram.clearLines();

            for (String line : lines) {
                hologram.appendTextLine(line);
            }
        }
    }

    private void createHologram(Location location) {

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

        lines.add(ChatColor.translateAlternateColorCodes('&', "&7&m--&f &c&lLeaderboards &7&m--&f"));
        lines.add(null);
        lines.add(ChatColor.translateAlternateColorCodes('&', "&6&lTop 5 &e&lKills"));

        int colorChangerCount = 0;
        for (UUID uuids : topKills.keySet()) {
            lines.add(ChatColor.translateAlternateColorCodes('&', (colorChangerCount++ == 0 ? "&c" : "&f") + Bukkit.getOfflinePlayer(uuids).getName() + " &7- "
                    + "&f" + topKills.get(uuids)));
        }

        Hologram hologram = HologramsAPI.createHologram(plugin, location);

        for (String line : lines) {
            hologram.appendTextLine(line);
        }
    }
}
