package net.climaxmc.KitPvp;

import lombok.Getter;
import net.climaxmc.Administration.Commands.*;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.CosmeticsCommand;
import net.climaxmc.KitPvp.Commands.Economy.BalanceCommand;
import net.climaxmc.KitPvp.Commands.Economy.BalanceTopCommand;
import net.climaxmc.KitPvp.Commands.Economy.EconomyCommand;
import net.climaxmc.KitPvp.Commands.Economy.PayCommand;
import net.climaxmc.KitPvp.Commands.*;
import net.climaxmc.KitPvp.Commands.Messaging.MessageCommand;
import net.climaxmc.KitPvp.Commands.Messaging.ReplyCommand;
import net.climaxmc.KitPvp.Listeners.*;
import net.climaxmc.KitPvp.Menus.ChallengesMenu;
import net.climaxmc.KitPvp.Menus.ReportGUI;
import net.climaxmc.KitPvp.Utils.ChatColor.ChatColorEvents;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffectEvents;
import net.climaxmc.KitPvp.Utils.Tag.TagEvents;
import net.climaxmc.KitPvp.Utils.Tournaments.TournamentCommands;
import net.climaxmc.KitPvp.Utils.Tournaments.TournamentEvents;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KitPvp {
    public static Inventory moreKitsInventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + "" + ChatColor.BOLD + "More Kits");
    public static Inventory soupInventory = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Free Soup!");
    public static Map<UUID, Integer> killStreak = new HashMap<>();
    public static Map<String, String> pendingTeams = new HashMap<>();
    public static Map<String, String> currentTeams = new ConcurrentHashMap<>();
    public static HashMap<UUID, Trail> inTrail = new HashMap<>();
    public static ArrayList<UUID> globalChatDisabled = new ArrayList<>();
    @Getter
    private static HashSet<UUID> afk = new HashSet<>();
    @Getter
    private static HashSet<UUID> vanished = new HashSet<>();
    @Getter
    private static HashSet<UUID> checking = new HashSet<>();

    public KitPvp(ClimaxPvp plugin) {
        // Initialize kits
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
        //plugin.getServer().getPluginManager().registerEvents(new ScoreboardListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockPlaceListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PortalListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerItemConsumeListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChallengesMenu(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ReportGUI(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryCloseListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ProjectileLaunchListener(plugin), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new DuelEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TournamentEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DeathEffectEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new TagEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChatColorEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin), plugin);
        //plugin.getServer().getPluginManager().registerEvents(new HackListeners(plugin), plugin);

        // Register commands
        plugin.getCommand("repair").setExecutor(new RepairCommand(plugin));
        plugin.getCommand("spawn").setExecutor(new SpawnCommand(plugin));
        plugin.getCommand("balance").setExecutor(new BalanceCommand(plugin));
        plugin.getCommand("economy").setExecutor(new EconomyCommand(plugin));
        plugin.getCommand("pay").setExecutor(new PayCommand(plugin));
        plugin.getCommand("statistics").setExecutor(new StatisticsCommand(plugin));
        plugin.getCommand("warp").setExecutor(new WarpCommand(plugin));
        plugin.getCommand("suicide").setExecutor(new SuicideCommand(plugin));
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
        plugin.getCommand("staffalert").setExecutor(new StaffAlertCommand(plugin));
        //plugin.getCommand("duel").setExecutor(new DuelCommand(plugin));
        plugin.getCommand("unlocktrail").setExecutor(new UnlockTrailCommand(plugin));
        plugin.getCommand("usetoken").setExecutor(new UseTokenCommand(plugin));
        plugin.getCommand("settokens").setExecutor(new SetTokensCommand(plugin));
        plugin.getCommand("donatebroadcast").setExecutor(new DonateBroadcastCommand(plugin));
        plugin.getCommand("cosmetics").setExecutor(new CosmeticsCommand(plugin));
        plugin.getCommand("tournament").setExecutor(new TournamentCommands(plugin));
        plugin.getCommand("unlockdeatheffect").setExecutor(new UnlockDeathEffectCommand(plugin));
        plugin.getCommand("unbancombat").setExecutor(new UnbanCombatCommand(plugin));
        plugin.getCommand("top").setExecutor(new TopCommand(plugin));
        plugin.getCommand("rotatemaps").setExecutor(new RotateMapsCommand(plugin));
        //plugin.getCommand("tag").setExecutor(new TagCommands(plugin));
        /*plugin.getCommand("team").setExecutor(new TeamCommand(plugin));*/
        //plugin.getCommand("staffreq").setExecutor(new StaffReqCommand(plugin));
        //plugin.getCommand("afk").setExecutor(new AFKCommand(plugin));
        //plugin.getCommand("options").setExecutor(new OptionsCommand(plugin));

    }
}
