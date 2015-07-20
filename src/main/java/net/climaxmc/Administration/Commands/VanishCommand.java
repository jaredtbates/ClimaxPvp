package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.Rank;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class VanishCommand implements CommandExecutor {
    private ClimaxPvp plugin;
    private HashSet<UUID> vanished = new HashSet<>();

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
            player.setFlySpeed(0.3F);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
            vanished.add(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.RED + "You are no longer vanished.");
            plugin.respawn(player);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));
            vanished.remove(player.getUniqueId());
        }

        return true;
    }
}
