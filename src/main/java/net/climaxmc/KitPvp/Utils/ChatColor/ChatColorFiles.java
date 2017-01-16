package net.climaxmc.KitPvp.Utils.ChatColor;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffect;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ChatColorFiles {

    private File file;
    private FileConfiguration config;

    public ChatColorFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "chatcolor.yml");
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

    public void setCurrentColor(Player player, DChatColor color) {
        config.set(player.getUniqueId() + ".currentColor", color.getName());
        ClimaxPvp.currentChatColor.put(player.getUniqueId(), color);
        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have selected " + ChatColor.YELLOW + color.getName());
        saveConfig();
    }
    public void removeCurrentColor(Player player) {
        config.set(player.getUniqueId() + ".currentColor", null);
        ClimaxPvp.currentChatColor.remove(player.getUniqueId());
        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have removed your color!");
        saveConfig();
    }
    public String getCurrentColor(Player player) {
        return (String) config.get(player.getUniqueId() + ".currentColor");
    }
    public void toggleItalics(Player player) {
        if (config.get(player.getUniqueId() + ".italics") == null) {
            config.set(player.getUniqueId() + ".italics", true);
            ClimaxPvp.chatColorItalics = true;
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have set italics to: " + ChatColor.GREEN + "True");
        } else {
            config.set(player.getUniqueId() + ".italics", null);
            ClimaxPvp.chatColorItalics = false;
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have set italics to: " + ChatColor.RED + "False");
        }
        saveConfig();
    }
    public boolean getItalics(Player player) {
        if (config.get(player.getUniqueId() + ".italics") == null) {
            return false;
        } else {
            return (boolean) config.get(player.getUniqueId() + ".italics");
        }
    }
}
