package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Commands.ReportCommand;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerQuitListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
            KitPvp.killStreak.remove(player.getUniqueId());
        }

        if (ReportCommand.reportBuilders.containsKey(player.getUniqueId())) {
            ReportCommand.reportBuilders.remove(player.getUniqueId());
        }

        if (ReportCommand.reportArray.containsKey(player.getUniqueId())) {
            ReportCommand.reportArray.remove(player.getUniqueId());
        }

        if (plugin.getServer().getOnlinePlayers().size() < 7) {
            plugin.getServer().getOnlinePlayers().stream().filter(players -> KitPvp.currentTeams.containsKey(players.getName())
                    || KitPvp.currentTeams.containsValue(players.getName())).forEach(players -> {
                players.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 1);
                players.sendMessage(ChatColor.RED + "Since there are less than 7 players online, your team has been suspended!");
                players.sendMessage(ChatColor.GRAY + "It will resume once there are 7 players online again.");
            });
        }

        /*if (KitPvp.currentTeams.containsKey(player.getName())) {
            Player teammate = Bukkit.getServer().getPlayer(KitPvp.currentTeams.get(player.getName()));
            teammate.playSound(teammate.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
            teammate.sendMessage(ChatColor.RED + " " + player.getName() + " has left the server. Therefore, the team has been disbanded!");
            KitPvp.currentTeams.remove(player.getName());
        } else if (KitPvp.currentTeams.values().contains(player.getName())) {
            int i = 0;
            for (String key : KitPvp.currentTeams.keySet()) {
                i++;
                if (KitPvp.currentTeams.get(key).equalsIgnoreCase(player.getName())) {
                    Player teammate = Bukkit.getServer().getPlayer(KitPvp.currentTeams.get(key));
                    teammate.playSound(teammate.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1F);
                    teammate.sendMessage(ChatColor.RED + " " + player.getName() + " has left the server. Therefore, the team has been disbanded!");
                    KitPvp.currentTeams.remove(KitPvp.currentTeams.get(key));
                }
            }
        }*/

        event.setQuitMessage(null/*ChatColor.RED + "Quit" + ChatColor.DARK_GRAY + "\u00bb " + player.getName()*/);

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> plugin.savePlayerData(plugin.getPlayerData(player)));
    }
}
