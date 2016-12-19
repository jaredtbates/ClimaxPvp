package net.climaxmc.KitPvp.Utils.DeathEffects;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffect;
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

public class DeathEffectFiles {

    private File file;
    private FileConfiguration config;

    public DeathEffectFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "deatheffects.yml");
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
        config.set(path, object);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryUnlockEffect(Player player, DeathEffect effect, int cost) {
        if (config.get(player.getUniqueId() + ".unlockedEffects." + effect.getName()) == null) {
            if (effect.getName().equals("Explosion")) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Beta Member Exclusive! Purchase @ donate.climaxmc.net");
                return;
            }
            PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
            if (cost <= playerData.getBalance()) {
                set(player.getUniqueId() + ".unlockedEffects." + effect.getName(), true);
                saveConfig();
                playerData.withdrawBalance(cost);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You've unlocked the " + ChatColor.YELLOW + effect.getName() + ChatColor.GRAY + " effect! For " + ChatColor.GREEN + "$" + cost);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            } else {
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Insufficient funds!");
            }
        } else {
            setEffect(player, effect);
        }
    }
    public boolean isEffectUnlocked(Player player, DeathEffect effect) {
        if (config.get(player.getUniqueId() + ".unlockedEffects." + effect.getName()) == null) {
            return false;
        } else {
            return true;
        }
    }
    public void setEffect(Player player, DeathEffect effect) {
        if (config.get(player.getUniqueId() + ".unlockedEffects." + effect.getName()) == null) {
            if (effect.getName().equals("Explosion")) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Beta Member Exclusive! Purchase @ donate.climaxmc.net");
                return;
            }
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You must unlock this effect to use it!");
        } else {
            ClimaxPvp.inEffect.put(player, effect.getName());
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have selected " + ChatColor.YELLOW + effect.getName());
        }
    }
    public void removeEffect(Player player) {
        if (ClimaxPvp.inEffect.containsKey(player)) {
            ClimaxPvp.inEffect.remove(player);
            ClimaxPvp.playerDeathEffect.remove(player.getUniqueId());
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have removed your effect!");
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You don't have any effect selected!");
        }
    }
    public void setCurrentEffect(Player player, DeathEffect effect) {
        config.set(player.getUniqueId() + ".currentEffect", effect.getName());
        saveConfig();
    }
    public void removeCurrentEffect(Player player) {
        config.set(player.getUniqueId() + ".currentEffect", null);
        saveConfig();
    }
    public String getCurrentEffect(Player player) {
        return (String) config.get(player.getUniqueId() + ".currentEffect");
    }
    public void forceUnlockEffect(Player player, String name) {
        set(player.getUniqueId() + ".unlockedEffects." + name, true);
        saveConfig();
    }
}
