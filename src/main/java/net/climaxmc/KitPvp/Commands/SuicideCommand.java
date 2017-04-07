package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Listeners.ScoreboardListener;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.ServerScoreboard;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.common.database.PlayerData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuicideCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SuicideCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        player.damage(1000);

        PlayerData playerData = plugin.getPlayerData(player);
        playerData.addDeaths(1);
        playerData.setKDR();

        ServerScoreboard serverScoreboard = plugin.getScoreboard(player);
        serverScoreboard.updateScoreboard();

        return true;
    }
}
