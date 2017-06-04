package net.climaxmc.KitPvp.Utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.packets.PacketClientKeepAliveEvent;
import net.climaxmc.KitPvp.packets.PacketServerKeepAliveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class PingUtils implements Listener {

    private ClimaxPvp plugin;
    public PingUtils(ClimaxPvp plugin) {
        this.plugin = plugin;

        new BukkitRunnable() {
            @Override
            public void run() {
                updatePings();
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public static int getPingOld(Player player) {

        String nmsVersion = ClimaxPvp.getInstance().getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);

        int ping = -1;

        try {
            Object nmsPlayer = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer").cast(player).getClass().getMethod("getHandle").invoke(player);
            ping = nmsPlayer.getClass().getField("ping").getInt(nmsPlayer);
        } catch (Exception e) {
            ClimaxPvp.getInstance().getLogger().severe("Could not get ping of player!");
            e.printStackTrace();
        }
        return ping;
    }

    public static int getPing(Player player) {
        int ping = getPingOld(player);

        ping = getCurrentPing(player);

        return ping;
    }

    private static HashMap<UUID, Long> time = new HashMap<>();
    private static HashMap<UUID, Long> pings = new HashMap<>();
    //This is to make sure the packet received is from the last packet sent and not a different one.
    private static HashMap<UUID, Boolean> hasReceivedPacket = new HashMap<>();

    @EventHandler
    public void onServerSendKeepAlive(PacketServerKeepAliveEvent event) {
        Player player = event.getPlayer();
        time.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onClientSendKeepAlive(PacketClientKeepAliveEvent event) {
        Player player = event.getPlayer();

        if (time.containsKey(player.getUniqueId())) {
            pings.put(player.getUniqueId(), System.currentTimeMillis() - time.get(player.getUniqueId()));
        }
        hasReceivedPacket.put(player.getUniqueId(), true);
    }

    private static int getCurrentPing(Player player) {

        if (pings.containsKey(player.getUniqueId())) {
            return pings.get(player.getUniqueId()).intValue();
        }
        return -1;
    }

    private static void updatePings() {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.KEEP_ALIVE);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hasReceivedPacket.containsKey(player.getUniqueId())) {
                if (hasReceivedPacket.get(player.getUniqueId())) {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                hasReceivedPacket.put(player.getUniqueId(), false);
            }
        }
    }

    /*private static long test(String hostAddress) {
        InetAddress inetAddress = null;
        Date start, stop;

        try {
            inetAddress = InetAddress.getByName(hostAddress);
        } catch (UnknownHostException e) {
            System.out.println("Problem, unknown host:");
            e.printStackTrace();
        }

        try {
            start = new Date();
            if (inetAddress.isReachable(5000)) {
                stop = new Date();
                return (stop.getTime() - start.getTime());
            }

        } catch (IOException e1) {
            System.out.println("Problem, a network error has occurred:");
            e1.printStackTrace();
        } catch (IllegalArgumentException e1) {
            System.out.println("Problem, timeout was invalid:");
            e1.printStackTrace();
        }

        return -1; // to indicate failure
    }*/
}
