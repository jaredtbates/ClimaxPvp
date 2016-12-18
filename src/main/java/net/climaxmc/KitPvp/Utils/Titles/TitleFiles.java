package net.climaxmc.KitPvp.Utils.Titles;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.donations.trails.Trail;
import net.climaxmc.common.titles.Title;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class TitleFiles {

    private File file;
    private FileConfiguration config;

    public TitleFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "titles.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void set(String path, Object object) {
        //Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), () -> {
        config.set(path, object);
        //});
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryUnlockTitle(Player player, Title title, int cost) {
        if (config.get(player.getUniqueId() + ".unlockedTitles." + title.getName()) == null) {
            PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
            if (cost <= playerData.getBalance()) {
                set(player.getUniqueId() + ".unlockedTitles." + title.getName(), true);
                saveConfig();
                playerData.withdrawBalance(cost);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You've unlocked the " + ChatColor.YELLOW + title.getTitle() + ChatColor.GRAY + " title! For " + ChatColor.GREEN + "$" + cost);
            } else {
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Insufficient funds!");
            }
        } else {
            setTitle(player, title);
        }
    }
    public boolean isTitleUnlocked(Player player, Title title) {
        if (config.get(player.getUniqueId() + ".unlockedTitles." + title.getName()) == null) {
            return false;
        } else {
            return true;
        }
    }
    public void setTitle(Player player, Title title) {
        if (config.get(player.getUniqueId() + ".unlockedTitles." + title.getName()) == null) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You must unlock this title to use it!");
        } else {
            ClimaxPvp.inTitle.put(player, title.getTitle());
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have selected " + ChatColor.YELLOW + title.getTitle());
        }
    }
    public void removeTitle(Player player) {
        if (ClimaxPvp.inTitle.containsKey(player)) {
            ClimaxPvp.inTitle.remove(player);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have removed your title!");
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You don't have any titles selected!");
        }
    }
}
