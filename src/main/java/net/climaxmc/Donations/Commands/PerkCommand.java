package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Enums.Trail;
import net.climaxmc.database.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;

import java.util.UUID;

public class PerkCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public PerkCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(ChatColor.RED + "You must be console to execute that command!");
            return true;
        }

        if (args.length < 3) {
            return true;
        }

        OfflinePlayer player = plugin.getServer().getOfflinePlayer(UUID.fromString(args[1]));

        if (player == null) {
            return true;
        }

        PlayerData playerData = plugin.getPlayerData(player);

        switch (args[0]) {
            /*
             * /perk trail {UUID} {Trail Name}
             */
            case "trail":
                Trail trail = Trail.valueOf(args[2].toUpperCase());

                if (trail == null) {
                    return true;
                }

                playerData.addPerk(trail);
                break;
            /*
             * /perk spectate {UUID}
             */
            case "spectate":
                playerData.addPerk(new SpectateCommand(plugin));
                break;
            /*
             * /perk nickname {UUID}
             */
            case "nickname":
                playerData.addPerk(new NicknameCommand(plugin));
                break;
        }

        return true;
    }
}
