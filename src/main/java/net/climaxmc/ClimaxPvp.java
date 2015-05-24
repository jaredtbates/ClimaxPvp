package net.climaxmc;

import lombok.Getter;
import me.confuser.barapi.BarAPI;
import net.climaxmc.API.Events.UpdateEvent;
import net.climaxmc.API.MySQL;
import net.climaxmc.API.PlayerData;
import net.climaxmc.Administration.Administration;
import net.climaxmc.Creative.Creative;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.Commands.SpawnCommand;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.OneVsOne.OneVsOne;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    @Getter
    private MySQL mySQL = null;
    @Getter
    private String prefix = "§0§l[§cClimax§0§l] §r";
    @Getter
    private Economy economy = null;
    @Getter
    private Permission permission = null;
    @Getter
    private Chat chat = null;

    BarAPI barAPI = new BarAPI(this);

    public void onEnable() {
        // Initialize Instance
        instance = this;

        // Enable BarAPI
        barAPI.onEnable();

        // Save Configuration
        saveDefaultConfig();

        // Connect to MySQL
        mySQL = new MySQL(
                getConfig().getString("Database.Host"),
                getConfig().getInt("Database.Port"),
                getConfig().getString("Database.Database"),
                getConfig().getString("Database.Username"),
                getConfig().getString("Database.Password")
        );

        // Configure Vault
        setupEconomy();
        setupPermissions();
        setupChat();

        // Load Modules
        new KitPvp(this);
        new OneVsOne(this);
        new Donations(this);
        new Creative(this);
        new Administration(this);

        // Global Commands
        getCommand("repair").setExecutor(new RepairCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        // Update Event
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                getServer().getPluginManager().callEvent(new UpdateEvent());
            }
        }, 20, 0);
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

        // Disable BarAPI
        barAPI.onDisable();
    }

    /**
     * Get player data from MySQL
     *
     * @param player Player to get data of
     * @return Data of player
     */
    public PlayerData getPlayerData(OfflinePlayer player) {
        return mySQL.getPlayerData(player);
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private boolean setupPermissions() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        permission = rsp.getProvider();
        return permission != null;
    }

    private boolean setupChat() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
        }
        return chat != null;
    }

    /**
     * Resets a player and sends them to spawn
     *
     * @param player Player to send to spawn
     */
    public void sendToSpawn(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20F);
        player.setMaxHealth(20F);
        player.setFlySpeed(0.1F);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        kitSelectorMeta.setDisplayName("§a§lKit Selector");
        List<String> kitSelectorLores = new ArrayList<String>();
        kitSelectorLores.add("§5§o(Right Click) to select a kit!");
        kitSelectorMeta.setLore(kitSelectorLores);
        kitSelector.setItemMeta(kitSelectorMeta);

        ItemStack particles = new ItemStack(Material.SEEDS);
        ItemMeta particlesMeta = particles.getItemMeta();
        particlesMeta.setDisplayName("§a§lTrail Selector");
        List<String> particlesLores = new ArrayList<String>();
        particlesLores.add("§5§o(Right Click) to select a trail!");
        particlesMeta.setLore(particlesLores);
        particles.setItemMeta(particlesMeta);

        player.getInventory().setItem(0, kitSelector);
        player.getInventory().setItem(8, particles);
    }
}
