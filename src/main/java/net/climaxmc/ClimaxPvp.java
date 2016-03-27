package net.climaxmc;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Administration.Runnables.UpdateRunnable;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Utils.Challenges.ChallengesFiles;
import net.climaxmc.common.database.MySQL;
import net.climaxmc.common.database.PlayerData;
import net.gpedro.integrations.slack.SlackApi;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance = null;
    @Getter
    public HashMap<UUID, Location> currentWarps = new HashMap<>();
    @Getter
    private MySQL mySQL = null;
    @Getter
    private String prefix = ChatColor.BLACK + "" + ChatColor.BOLD + "[" + ChatColor.RED + "Climax" + ChatColor.BLACK + "" + ChatColor.BOLD + "] " + ChatColor.RESET;
    @Getter
    private List<String> rules = new ArrayList<>();
    @Getter
    private List<String> help = new ArrayList<>();
    @Getter
    private List<String> filteredWords = new ArrayList<>();
    @Getter
    private FileConfiguration warpsConfig = null;
    private File warpsConfigFile = null;
    @Getter
    private ChallengesFiles challengesFiles = null;
    @Getter
    private HashMap<UUID, PlayerData> playerDataList = new HashMap<>();
    @Getter
    private HashMap<UUID, UUID> messagers = new HashMap<>();
    private Donations donations = null;
    @Getter
    private SlackApi slackReports = null;
    @Getter
    private SlackApi slackBans = null;
    @Getter
    private SlackApi slackStaffHelp = null;
    @Getter
    private SlackApi slackDonations = null;

    @Override
    public void onEnable() {
        // Initialize Instance
        instance = this;

        // Save Default Configuration
        saveDefaultConfig();

        // Save Default Warps Storage File
        saveDefaultWarpsConfig();

        // Save Help File
        File helpFile = new File(getDataFolder(), "help.txt");
        if (!helpFile.exists()) {
            saveResource("help.txt", false);
        }
        try {
            Files.lines(FileSystems.getDefault().getPath(helpFile.getPath())).forEach(helpLine -> help.add(ChatColor.translateAlternateColorCodes('&', helpLine)));
        } catch (IOException e) {
            getLogger().severe("Could not get help!");
        }

        // Save Rules File
        File rulesFile = new File(getDataFolder(), "rules.txt");
        if (!rulesFile.exists()) {
            saveResource("rules.txt", false);
        }
        try {
            Files.lines(FileSystems.getDefault().getPath(rulesFile.getPath())).forEach(ruleLine -> rules.add(ChatColor.translateAlternateColorCodes('&', ruleLine)));
        } catch (IOException e) {
            getLogger().severe("Could not get rules!");
        }

        // Save Filter File
        File filterFile = new File(getDataFolder(), "filter.txt");
        if (!filterFile.exists()) {
            saveResource("filter.txt", false);
        }
        try {
            Files.lines(FileSystems.getDefault().getPath(filterFile.getPath())).forEach(filterLine -> filteredWords.add(ChatColor.translateAlternateColorCodes('&', filterLine)));
        } catch (IOException e) {
            getLogger().severe("Could not get filtered words!");
        }

        // Connect to MySQL
        mySQL = new MySQL(
                this,
                getConfig().getString("Database.Host"),
                getConfig().getInt("Database.Port"),
                getConfig().getString("Database.Database"),
                getConfig().getString("Database.Username"),
                getConfig().getString("Database.Password")
        );

        // Create temporary player data
        getServer().getOnlinePlayers().forEach(player -> mySQL.getTemporaryPlayerData().put(player.getUniqueId(), new HashMap<>()));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Load Modules
        new KitPvp(this);
        donations = new Donations(this);
        new Administration(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new UpdateRunnable(this), 120, 120);
        challengesFiles = new ChallengesFiles();
        KitManager.setAllKitsEnabled(getConfig().getBoolean("AllKitsEnabled"));

        // Initialize SlackApi
        slackReports = new SlackApi("https://hooks.slack.com/services/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");
        slackBans = new SlackApi("https://hooks.slack.com/services/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");
        slackStaffHelp = new SlackApi("https://hooks.slack.com/services/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");
        slackDonations = new SlackApi("https://hooks.slack.com/services/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");

    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(player -> savePlayerData(getPlayerData(player)));

        if (donations.getVoteReceiver() != null) {
            donations.getVoteReceiver().shutdown();
        }

        //trailsRunnable.stopTrails();

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
        if (!playerDataList.containsKey(player.getUniqueId()) || playerDataList.get(player.getUniqueId()) == null) {
            playerDataList.put(player.getUniqueId(), mySQL.getPlayerData(player.getUniqueId()));
        }
        return playerDataList.get(player.getUniqueId());
    }

    /**
     * Saves player data
     *
     * @param playerData Player data to save
     */
    public void savePlayerData(PlayerData playerData) {
        if (playerDataList.containsKey(playerData.getUuid())) {
            mySQL.savePlayerData(playerData);
        }
    }

    /**
     * Respawns a player at a location
     *
     * @param player Player to respawn
     * @param location Location for player to respawn at
     */
    public void respawn(Player player, Location location) {
        player.spigot().respawn();
        player.teleport(location);
        getServer().getPluginManager().callEvent(new PlayerRespawnEvent(player, location, false));
    }

    /**
     * Respawns a player at the world spawn
     *
     * @param player Player to respawn
     */
    public void respawn(Player player) {
        respawn(player, player.getWorld().getSpawnLocation());
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
            return;
        }

        respawn(player, location);
        currentWarps.put(player.getUniqueId(), getWarpLocation(warp));

        if (warp.equalsIgnoreCase("Fair")) {
            new PvpKit().wearCheckLevel(player);
        }
    }
}
