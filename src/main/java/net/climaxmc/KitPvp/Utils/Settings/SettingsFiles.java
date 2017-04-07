package net.climaxmc.KitPvp.Utils.Settings;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Donations;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Joshua on 11/19/2016.
 */
public class SettingsFiles {

    private File file;
    private FileConfiguration config;

    public boolean passedTrailsChecks = false;

    public SettingsFiles() {
        this.file = new File(ClimaxPvp.getInstance().getDataFolder() + File.separator + "settings.yml");
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

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (config.get(player.getUniqueId() + ".instaRespawn") == null) {
            set(player.getUniqueId() + ".instaRespawn", false);
            saveConfig();
        } else {
            if (!(boolean) config.get(player.getUniqueId() + ".instaRespawn")) {
                ClimaxPvp.getInstance().respawnValue.put(player, true);
            } else {
                ClimaxPvp.getInstance().respawnValue.put(player, false);
            }
        }
    }

    public void toggleRespawnValue(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (config.get(player.getUniqueId() + ".instaRespawn") == null) {
                    set(player.getUniqueId() + ".instaRespawn", false);
                    saveConfig();
                } else {
                    if (!(boolean) config.get(player.getUniqueId() + ".instaRespawn")) {
                        set(player.getUniqueId() + ".instaRespawn", true);
                        ClimaxPvp.getInstance().respawnValue.put(player, true);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eInsta-Respawn \u00A77to: \u00A7aTrue");
                    } else {
                        set(player.getUniqueId() + ".instaRespawn", false);
                        ClimaxPvp.getInstance().respawnValue.put(player, false);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eInsta-Respawn \u00A77to: \u00A7cFalse");
                    }
                }
            }
        });
    }
    public boolean getRespawnValue(Player player) {
        if (config.get(player.getUniqueId() + ".instaRespawn") == null) {
            set(player.getUniqueId() + ".instaRespawn", false);
            saveConfig();
        } else {
            return (boolean) config.get(player.getUniqueId() + ".instaRespawn");
        }
        return false;
    }

    public void toggleReceiveMsg(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (config.get(player.getUniqueId() + ".receiveMsging") == null) {
                    set(player.getUniqueId() + ".receiveMsging", true);
                    saveConfig();
                } else {
                    if (!(boolean) config.get(player.getUniqueId() + ".receiveMsging")) {
                        set(player.getUniqueId() + ".receiveMsging", true);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eReceiving Msgs \u00A77to: \u00A7aTrue");
                    } else {
                        set(player.getUniqueId() + ".receiveMsging", false);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eReceiving Msgs \u00A77to: \u00A7cFalse");
                    }
                }
            }
        });
    }
    public boolean getReceiveMsgValue(Player player) {
        if (config.get(player.getUniqueId() + ".receiveMsging") == null) {
            set(player.getUniqueId() + ".receiveMsging", true);
            saveConfig();
        } else {
            return (boolean) config.get(player.getUniqueId() + ".receiveMsging");
        }
        return true;
    }

    public void toggleGlobalChat(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (config.get(player.getUniqueId() + ".globalChat") == null) {
                    set(player.getUniqueId() + ".globalChat", true);
                    saveConfig();
                    KitPvp.globalChatDisabled.remove(player.getUniqueId());
                } else {
                    if (!(boolean) config.get(player.getUniqueId() + ".globalChat")) {
                        set(player.getUniqueId() + ".globalChat", true);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eGlobal Chat \u00A77to: \u00A7aTrue");
                        KitPvp.globalChatDisabled.remove(player.getUniqueId());
                    } else {
                        set(player.getUniqueId() + ".globalChat", false);
                        saveConfig();
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
                        player.sendMessage("\u00A7f» \u00A77You have set \u00A7eGlobal Chat \u00A77to: \u00A7cFalse");
                        KitPvp.globalChatDisabled.add(player.getUniqueId());
                    }
                }
            }
        });
    }
    public boolean getGlobalChatValue(Player player) {
        if (config.get(player.getUniqueId() + ".globalChat") == null) {
            set(player.getUniqueId() + ".globalChat", true);
            saveConfig();
            KitPvp.globalChatDisabled.remove(player.getUniqueId());
        } else {
            return (boolean) config.get(player.getUniqueId() + ".globalChat");
        }
        return true;
    }

    public void toggleSpawnSoup(Player player) {
        if (config.get(player.getUniqueId() + ".respawnSoup") == null) {
            set(player.getUniqueId() + ".respawnSoup", false);
            saveConfig();
        } else {
            if (!(boolean) config.get(player.getUniqueId() + ".respawnSoup")) {
                set(player.getUniqueId() + ".respawnSoup", true);
                saveConfig();
                //player.closeInventory();
                player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 1);
                //player.sendMessage("\u00A7f» \u00A77Mode set \u00A77to: \u00A7eSoup");
            } else if ((boolean) config.get(player.getUniqueId() + ".respawnSoup")) {
                set(player.getUniqueId() + ".respawnSoup", false);
                saveConfig();
                //player.closeInventory();
                player.playSound(player.getLocation(), Sound.NOTE_BASS_GUITAR, 1, 2);
                //player.sendMessage("\u00A7f» \u00A77Mode set \u00A77to: \u00A7eRod/Regen");
            }
        }
    }
    public boolean getSpawnSoupValue(Player player) {
        if (config.get(player.getUniqueId() + ".respawnSoup") == null) {
            set(player.getUniqueId() + ".respawnSoup", false);
            saveConfig();
        } else {
            return (boolean) config.get(player.getUniqueId() + ".respawnSoup");
        }
        return false;
    }



    public void removeTrails(Player player) {
        if (KitPvp.inTrail.containsKey(player.getUniqueId())) {
            KitPvp.inTrail.remove(player.getUniqueId());
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have removed your trail!");
        } else {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You don't have any trails selected!");
        }
    }
    public void setTrail(Player player, String name, Trail trail) {
        Bukkit.getScheduler().runTaskAsynchronously(ClimaxPvp.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (config.get(player.getUniqueId() + ".unlockedTrails." + name) == null) {
                    if (name.equals("Snow")) {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Beta Member Exclusive! Purchase @ donate.climaxmc.net");
                        return;
                    }
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You must unlock this trail to use it!");
                } else {
                    KitPvp.inTrail.put(player.getUniqueId(), trail);
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You have selected the " + ChatColor.YELLOW + name + ChatColor.GRAY + " trail!");
                }
            }
        });
    }
    public void tryUnlockTrail(Player player, String name, Trail trail, int cost) {
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
        if (config.get(player.getUniqueId() + ".unlockedTrails." + name) == null) {
            if (name.equals("Snow")) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Beta Member Exclusive! Purchase @ donate.climaxmc.net");
                return;
            }
            if (cost <= playerData.getBalance()) {
                set(player.getUniqueId() + ".unlockedTrails." + name, true);
                saveConfig();
                playerData.withdrawBalance(cost);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You've unlocked the " + ChatColor.YELLOW + name + ChatColor.GRAY + " trail! For " + ChatColor.GREEN + "$" + cost);
            } else {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Insufficient funds!");
            }
        } else {
            setTrail(player, name, trail);
        }
    }
    public void forceUnlockTrail(Player player, String name) {
        set(player.getUniqueId() + ".unlockedTrails." + name, true);
        saveConfig();
    }
    public boolean isTrailUnlocked(Player player, String name) {
        if (config.get(player.getUniqueId() + ".unlockedTrails." + name) == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setWhitelistTokens(UUID uuid, Boolean x) {
        set(uuid + ".tokens", x);
        saveConfig();
    }
    public boolean getWhitelistTokens(UUID uuid) {
        if (config.get(uuid + ".tokens") == null) {
            set(uuid + ".tokens", false);
            return false;
        } else {
            return (boolean) config.get(uuid + ".tokens");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
    }

    /*public void setClouds(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Clouds")) {
            config.set(player.getUniqueId() + ".Clouds", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setFlame(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Flame")) {
            config.set(player.getUniqueId() + ".Flame", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setRain(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Rain")) {
            config.set(player.getUniqueId() + ".Rain", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setMystic(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Mystic")) {
            config.set(player.getUniqueId() + ".Mystic", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setEnder(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Ender")) {
            config.set(player.getUniqueId() + ".Ender", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setHypnotic(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Hypnotic")) {
            config.set(player.getUniqueId() + ".Hypnotic", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setLove(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Love")) {
            config.set(player.getUniqueId() + ".Love", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setNotes(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Notes")) {
            config.set(player.getUniqueId() + ".Notes", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }
    public void setSlime(Player player) {
        if (!(boolean) config.get(player.getUniqueId() + ".Slime")) {
            config.set(player.getUniqueId() + ".Slime", true);
            config.set(player.getUniqueId() + ".hasTrail", true);
        } else {
            player.sendMessage(ChatColor.WHITE + "\uBB00 " + ChatColor.GRAY + "You already have this trail selected!");
        }
    }*/
}
