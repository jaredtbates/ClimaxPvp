package net.climaxmc;

import lombok.Getter;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.MySQL;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    @Getter
    public HashMap<UUID, Location> currentWarps = new HashMap<>();
    @Getter
    private MySQL mySQL = null;
    @Getter
    private String prefix = ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.RED + "Climax" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.RESET;
    // Warps Configuration
    @Getter
    private FileConfiguration warpsConfig = null;
    private File warpsConfigFile = null;

    @Override
    public void onEnable() {
        // Initialize Instance
        instance = this;

        // Save Default Configuration
        saveDefaultConfig();

        // Save Default Warps Storage File
        saveDefaultWarpsConfig();

        // Connect to MySQL
        mySQL = new MySQL(
                getConfig().getString("Database.Host"),
                getConfig().getInt("Database.Port"),
                getConfig().getString("Database.Database"),
                getConfig().getString("Database.Username"),
                getConfig().getString("Database.Password")
        );

        // Create temporary player data
        getServer().getOnlinePlayers().forEach(player -> mySQL.getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>()));

        // Load Modules
        new KitPvp(this);
        new Donations(this);
        new Administration(this);
    }

    @Override
    public void onDisable() {
        // Close MySQL Connection
        if (mySQL.getConnection() != null) {
            try {
                mySQL.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get player data from MySQL
     *
     * @param player Player to get data of
     * @return Data of player
     */
    public PlayerData getPlayerData(OfflinePlayer player) {
        return mySQL.getPlayerData(player.getUniqueId());
    }

    /**
     * Respawns a player
     *
     * @param player Player to respawn
     */
    public void respawn(Player player) {
        player.spigot().respawn();
        player.teleport(player.getWorld().getSpawnLocation());
        getServer().getPluginManager().callEvent(new PlayerRespawnEvent(player, player.getWorld().getSpawnLocation(), false));
    }

    /**
     * Gets temporary data of a player (clears on player join)
     *
     * @param player Player to get data of
     * @return Temporary data of player
     */
    public Map<String, Object> getTemporaryPlayerData(OfflinePlayer player) {
        return mySQL.getTemporaryPlayerData().get(player.getUniqueId());
    }

    /**
     * Saves the default currentWarps configuration file
     */
    private void saveDefaultWarpsConfig() {
        if (warpsConfigFile == null) {
            warpsConfigFile = new File(getDataFolder(), "warps.yml");
        }

        if (!warpsConfigFile.exists()) {
            saveResource("warps.yml", false);
        }

        warpsConfig = YamlConfiguration.loadConfiguration(warpsConfigFile);
    }

    /**
     * Saves the currentWarps configuration file
     */
    public void saveWarpsConfig() {
        try {
            warpsConfig.save(warpsConfigFile);
        } catch (IOException e) {
            getLogger().severe("Could not save warps configuration!");
        }
    }

    /**
     * Gets the location of a warp
     *
     * @param warp Name of warp to get location of
     * @return Location of warp
     */
    public Location getWarpLocation(String warp) {
        ConfigurationSection noSoupSection;

        try {
            noSoupSection = warpsConfig.getConfigurationSection(warpsConfig.getKeys(false).stream().filter(key -> key.equalsIgnoreCase(warp)).findFirst().get());
        } catch (NoSuchElementException ignored) {
            return null;
        }

        return new Location(
                getServer().getWorld(noSoupSection.getString("World")),
                noSoupSection.getDouble("X"),
                noSoupSection.getDouble("Y"),
                noSoupSection.getDouble("Z"),
                (float) noSoupSection.getDouble("Yaw"),
                (float) noSoupSection.getDouble("Pitch")
        );
    }

    /**
     * Warps a player to specified warp
     *
     * @param player Player to warp
     */
    public void warp(String warp, Player player) {
        Location location = getWarpLocation(warp);

        if (location == null) {
            player.sendMessage(ChatColor.RED + "That warp does not exist!");
        }

        respawn(player);
        player.teleport(getWarpLocation(warp));
        currentWarps.put(player.getUniqueId(), getWarpLocation(warp));
    }

    public void sendActionBar(Player player, String message){
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);
        try {
            Class<?> obcPlayer = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object p = obcPlayer.cast(player);
            Object packet;
            Class<?> chatPacket = Class.forName("net.minecraft.server." + nmsVersion + ".PacketPlayOutChat");
            Class<?> nmsPacket = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            if (nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_")) {
                Class<?> serializer = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
                Class<?> baseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Method a = serializer.getDeclaredMethod("a", String.class);
                Object cbc = baseComponent.cast(a.invoke(serializer, "{\"text\": \"" + message + "\"}"));
                packet = chatPacket.getConstructor(baseComponent, byte.class).newInstance(cbc, (byte) 2);
            } else {
                Class<?> chatComponent = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
                Class<?> baseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Object component = chatComponent.getConstructor(String.class).newInstance(message);
                packet = chatPacket.getConstructor(baseComponent, byte.class).newInstance(component, (byte) 2);
            }
            Method getHandle = obcPlayer.getDeclaredMethod("getHandle");
            Object handle = getHandle.invoke(p);
            Field playerConnection = handle.getClass().getDeclaredField("playerConnection");
            Object playerConnectionHandle = playerConnection.get(handle);
            Method sendPacket = playerConnectionHandle.getClass().getDeclaredMethod("sendPacket", nmsPacket);
            sendPacket.invoke(playerConnectionHandle, packet);
        } catch (Exception e) {
            getInstance().getLogger().severe("Could not send Action Bar packet!");
            e.printStackTrace();
        }
    }
}
