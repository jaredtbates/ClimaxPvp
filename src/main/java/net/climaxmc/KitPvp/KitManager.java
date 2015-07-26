package net.climaxmc.KitPvp;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kits.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class KitManager implements Listener {
    @Getter
    private static List<Kit> kits = new ArrayList<>();
    @Getter
    private static Map<UUID, Kit> playersInKits = new HashMap<>();
    @Getter
    private static Map<UUID, Kit> previousKit = new HashMap<>();

    public KitManager(ClimaxPvp plugin) {
        // Gray Kits
        kits.add(new PvpKit());
        kits.add(new HeavyKit());
        kits.add(new ArcherKit());
        kits.add(new FishermanKit());
        kits.add(new StrafeKit());

        // Blue Kits
        kits.add(new EndermanKit());
        kits.add(new SanicKit());
        kits.add(new RabbitKit());
        kits.add(new SoldierKit());
        kits.add(new PrecisionKit());

        // Green Kits
        kits.add(new ThorKit());
        kits.add(new AnvilKit());
        kits.add(new AnchorKit());
        kits.add(new GhastKit());
        kits.add(new ChemistKit());

        // Red Kits
        kits.add(new BlazeKit());
        kits.add(new WitherKit());
        kits.add(new RageKit());
        kits.add(new ViperKit());
        kits.add(new IronGolemKit());

        // Gold Kits
        kits.add(new RangerKit());
        kits.add(new AssassinKit());
        kits.add(new VikingKit());
        //kits.add(new LazerKit());
        kits.add(new KangarooKit());

        for (Kit kit : kits) {
            plugin.getServer().getPluginManager().registerEvents(kit, plugin);
            getCommandMap().register(plugin.getConfig().getName(), getCommand(kit.getName().replaceAll("\\s+", ""), plugin));
            plugin.getCommand(kit.getName().replaceAll("\\s+", "")).setExecutor(kit);
        }
    }

    public static boolean isPlayerInKit(Player player) {
        return playersInKits.containsKey(player.getUniqueId());
    }

    public static boolean isPlayerInKit(Player player, Kit kit) {
        return playersInKits.containsKey(player.getUniqueId()) && playersInKits.get(player.getUniqueId()).equals(kit);
    }

    public static boolean isPlayerInKit(Player player, Class<? extends Kit> kit) {
        return playersInKits.containsKey(player.getUniqueId()) && playersInKits.get(player.getUniqueId()).getClass().equals(kit);
    }

    @EventHandler
    public final void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            previousKit.put(player.getUniqueId(), playersInKits.get(player.getUniqueId()));
            playersInKits.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public final void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (isPlayerInKit(player)) {
            previousKit.put(player.getUniqueId(), playersInKits.get(player.getUniqueId()));
            playersInKits.remove(player.getUniqueId());
        }
    }

    public final void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            playersInKits.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public final void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isPlayerInKit(player)) {
            playersInKits.remove(player.getUniqueId());
        }
    }

    private PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

    private CommandMap getCommandMap() {
        CommandMap commandMap = null;

        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commandMap;
    }
}
