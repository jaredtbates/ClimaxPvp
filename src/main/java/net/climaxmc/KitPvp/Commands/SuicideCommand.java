package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Listeners.ScoreboardListener;
import net.climaxmc.KitPvp.Utils.I;
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

        SettingsFiles settingsFiles = new SettingsFiles();
        if (settingsFiles.getRespawnValue(player) == true) {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                player.setHealth(20);

                plugin.respawn(player);

                if (player.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {
                    new PvpKit().wearCheckLevel(player);
                }
            });
        } else {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                player.setHealth(20);
                player.setGameMode(GameMode.CREATIVE);
                for(Player players : Bukkit.getServer().getOnlinePlayers()){
                    players.hidePlayer(player);
                }
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setVelocity(player.getVelocity().setY(1.5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 21, 0));
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);

                ClimaxPvp.deadPeoples.add(player);

                player.getInventory().setItem(4, new I(Material.BOOK).name("§6§lRespawn"));
            });
        }

        PlayerData playerData = plugin.getPlayerData(player);
        playerData.addDeaths(1);

        ScoreboardListener scoreboardListener = new ScoreboardListener(plugin);
        scoreboardListener.updateScoreboards();

        Bukkit.broadcastMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " died");

        return true;
    }
}
