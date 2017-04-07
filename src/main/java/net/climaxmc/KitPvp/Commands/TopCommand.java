package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.common.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class TopCommand implements CommandExecutor {

    private ClimaxPvp plugin;
    public TopCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &c/top (&fkills&7, &fdeaths&7, &fkdr&7, &fks&c)"));
        } else if (args.length == 1) {

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

            ResultSet deathsSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `deaths`+0 DESC LIMIT 5");
            Map<UUID, Integer> topDeaths = new LinkedHashMap<>();

            try {
                while (deathsSet.next()) {
                    topDeaths.put(UUID.fromString(deathsSet.getString("uuid")), deathsSet.getInt("deaths"));
                }
            } catch (SQLException e) {
                //
            }

            ResultSet kdrSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `kdr`+0 DESC LIMIT 5");
            Map<UUID, Double> topKDR = new LinkedHashMap<>();

            try {
                while (kdrSet.next()) {
                    topKDR.put(UUID.fromString(kdrSet.getString("uuid")), kdrSet.getDouble("kdr"));
                }
            } catch (SQLException e) {
                //
            }

            ResultSet topksSet = mySQL.executeQuery("SELECT * FROM climax_playerdata ORDER BY `topks`+0 DESC LIMIT 5");
            Map<UUID, Integer> topKS = new LinkedHashMap<>();

            try {
                while (topksSet.next()) {
                    topKS.put(UUID.fromString(topksSet.getString("uuid")), topksSet.getInt("topks"));
                }
            } catch (SQLException e) {
                //
            }

            if (args[0].equals("kills")) {
                player.sendMessage(ChatUtils.color("&f\u00BB &6Top 5 Kills:"));
                int counter = 1;
                for (UUID uuids : topKills.keySet()) {
                    player.sendMessage(ChatUtils.color((counter == 1 ? "&4" : "&c") + counter + ". &f" + Bukkit.getOfflinePlayer(uuids).getName() + " &7- &f" + topKills.get(uuids)));
                    counter++;
                }
            } else if (args[0].equals("deaths")) {
                player.sendMessage(ChatUtils.color("&f\u00BB &6Top 5 Deaths:"));
                int counter = 1;
                for (UUID uuids : topDeaths.keySet()) {
                    player.sendMessage(ChatUtils.color((counter == 1 ? "&4" : "&c") + counter + ". &f" + Bukkit.getOfflinePlayer(uuids).getName() + " &7- &f" + topDeaths.get(uuids)));
                    counter++;
                }
            } else if (args[0].equals("kdr")) {
                player.sendMessage(ChatUtils.color("&f\u00BB &6Top 5 KDR's:"));
                int counter = 1;
                for (UUID uuids : topKDR.keySet()) {
                    player.sendMessage(ChatUtils.color((counter == 1 ? "&4" : "&c") + counter + ". &f" + Bukkit.getOfflinePlayer(uuids).getName() + " &7- &f" + topKDR.get(uuids)));
                    counter++;
                }
            } else if (args[0].equals("ks")) {
                player.sendMessage(ChatUtils.color("&f\u00BB &6Top 5 Killstreaks:"));
                int counter = 1;
                for (UUID uuids : topKS.keySet()) {
                    player.sendMessage(ChatUtils.color((counter == 1 ? "&4" : "&c") + counter + ". &f" + Bukkit.getOfflinePlayer(uuids).getName() + " &7- &f" + topKS.get(uuids)));
                    counter++;
                }
            }
        }

        return false;
    }
}
