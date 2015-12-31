package net.climaxmc.KitPvp;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Commands.Economy.BalanceCommand;
import net.climaxmc.KitPvp.Commands.Economy.BalanceTopCommand;
import net.climaxmc.KitPvp.Commands.Economy.EconomyCommand;
import net.climaxmc.KitPvp.Commands.Economy.PayCommand;
import net.climaxmc.KitPvp.Commands.*;
import net.climaxmc.KitPvp.Commands.Messaging.MessageCommand;
import net.climaxmc.KitPvp.Commands.Messaging.ReplyCommand;
import net.climaxmc.KitPvp.Listeners.*;
import net.climaxmc.KitPvp.Menus.ChallengesMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KitPvp {
    public static Inventory moreKitsInventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + "" + ChatColor.BOLD + "More Kits");
    public static Inventory soupInventory = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Free Soup!");
    public static Map<UUID, Integer> killStreak = new HashMap<>();
    //public static Set<Duel> duels = new HashSet<>();
    public static Map<String, String> pendingTeams = new HashMap<>();
    public static Map<String, String> currentTeams = new HashMap<>();
    //public static Map<UUID, String> trails = new HashMap<>();

    public KitPvp(ClimaxPvp plugin) {
        // Initalize kits
        plugin.getServer().getPluginManager().registerEvents(new KitManager(plugin), plugin);

        // Register listeners
        plugin.getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ItemSpawnListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeathListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDropItemListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerInteractListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerRespawnListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WeatherChangeListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryOpenListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerQuitListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ScoreboardListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PortalListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChallengesMenu(), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new PlayerProfileMenu(plugin), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new CurrencyMenu(plugin), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new AchievementMenu(plugin), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new HackListeners(plugin), plugin);

        // Register commands
        plugin.getCommand("repair").setExecutor(new RepairCommand(plugin));
        plugin.getCommand("spawn").setExecutor(new SpawnCommand(plugin));
        plugin.getCommand("balance").setExecutor(new BalanceCommand(plugin));
        plugin.getCommand("economy").setExecutor(new EconomyCommand(plugin));
        plugin.getCommand("pay").setExecutor(new PayCommand(plugin));
        plugin.getCommand("statistics").setExecutor(new StatisticsCommand(plugin));
        plugin.getCommand("warp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("suicide").setExecutor(new SuicideCommand());
        plugin.getCommand("help").setExecutor(new HelpCommand(plugin));
        plugin.getCommand("rules").setExecutor(new RulesCommand(plugin));
        plugin.getCommand("message").setExecutor(new MessageCommand(plugin));
        plugin.getCommand("reply").setExecutor(new ReplyCommand(plugin));
        plugin.getCommand("ping").setExecutor(new PingCommand(plugin));
        plugin.getCommand("soup").setExecutor(new SoupCommand(plugin));
        plugin.getCommand("report").setExecutor(new ReportCommand(plugin));
        plugin.getCommand("list").setExecutor(new ListCommand(plugin));
        plugin.getCommand("realname").setExecutor(new RealNameCommand(plugin));
        plugin.getCommand("baltop").setExecutor(new BalanceTopCommand(plugin));
        plugin.getCommand("youtube").setExecutor(new YoutubeCommand(plugin));
        plugin.getCommand("pei").setExecutor(new PeiCommand());
        //plugin.getCommand("duel").setExecutor(new DuelCommand(plugin));
        plugin.getCommand("team").setExecutor(new TeamCommand(plugin));
    }
}
