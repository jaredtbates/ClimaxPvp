package net.climaxmc;

import lombok.Getter;
import net.climaxmc.API.Events.UpdateEvent;
import net.climaxmc.API.Statistics;
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

import javax.persistence.PersistenceException;
import java.util.*;

public class ClimaxPvp extends JavaPlugin {
    @Getter
    private static ClimaxPvp instance;
    @Getter
    private String prefix = "§0§l[§cClimax§0§l] §r";
    @Getter
    private Economy economy = null;
    @Getter
    private Permission permission = null;
    @Getter
    private Chat chat = null;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupDatabase();
        setupEconomy();
        setupPermissions();
        setupChat();
        new KitPvp(this);
        new OneVsOne(this);
        new Donations(this);
        new Creative(this);
        new Administration(this);
        getCommand("repair").setExecutor(new RepairCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                getServer().getPluginManager().callEvent(new UpdateEvent());
            }
        }, 1, 1);
    }

    @Override
    public void onDisable() {
    }

    private void setupDatabase() {
        try {
            getDatabase().find(Statistics.class).findRowCount();
        } catch (PersistenceException ex) {
            System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Statistics.class);
        return list;
    }

    public Statistics getStatistics(OfflinePlayer player) {
        return getStatistics(player.getUniqueId());
    }

    public Statistics getStatistics(UUID uuid) {
        Statistics statistics = getDatabase().find(Statistics.class).where().ieq("uuid", uuid.toString()).findUnique();
        if (statistics == null) {
            statistics = new Statistics();
            statistics.setUuid(uuid);
            getDatabase().insert(statistics);
        }
        return statistics;
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
