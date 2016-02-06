package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpectateCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.NINJA)) {
            player.sendMessage(ChatColor.RED + " Please donate for Titan at https://donate.climaxmc.net for access to spectator mode!");
            return true;
        }

        if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
            plugin.respawn(player);
            if (plugin.getCurrentWarps().containsKey(player.getUniqueId())) {
                player.teleport(plugin.getCurrentWarps().get(player.getUniqueId()));
            }
            player.setGameMode(GameMode.SPECTATOR);
            player.setFlySpeed(0.15F);
            player.sendMessage(ChatColor.GREEN + " You are now spectating");
        } else {
            plugin.respawn(player);
            player.sendMessage(ChatColor.GREEN + " You are no longer spectating");
        }

        return true;
    }
}
