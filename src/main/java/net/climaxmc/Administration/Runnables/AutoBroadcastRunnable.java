package net.climaxmc.Administration.Runnables;

import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutoBroadcastRunnable implements Runnable {
    private ClimaxPvp plugin;
    private int amount = 0;

    public AutoBroadcastRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (amount >= plugin.getConfig().getStringList("AutoBroadcast.Messages").size()) {
            amount = 0;
        }

        plugin.getServer().getOnlinePlayers().stream().filter(player -> player.getLocation().distance(player.getWorld().getSpawnLocation()) <= 12).forEach(player ->
                sendActionBar(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getStringList("AutoBroadcast.Messages").get(amount))));

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            plugin.getServer().getOnlinePlayers().stream().filter(player -> player.getLocation().distance(player.getWorld().getSpawnLocation()) <= 12).forEach(player ->
                    sendActionBar(player, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getStringList("AutoBroadcast.Messages").get(amount))));
            amount++;
        }, 10 * plugin.getConfig().getInt("AutoBroadcast.Time"));
    }

    private void sendActionBar(Player player, String message){
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
            plugin.getLogger().severe("Could not send Action Bar packet!");
            e.printStackTrace();
        }
    }
}
