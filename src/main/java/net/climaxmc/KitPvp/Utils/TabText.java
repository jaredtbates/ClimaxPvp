package net.climaxmc.KitPvp.Utils;
/* Created by GamerBah on 2/9/2016 */


import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TabText {
    private ClimaxPvp plugin;

    public TabText(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void setTabHeader(Player player, String tabHeader, String tabFooter) {
        String nmsVersion = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);
        try {
            Class<?> obcPlayer = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer");
            Object p = obcPlayer.cast(player);
            Object packet;
            Class<?> tabHeaderPacket = Class.forName("net.minecraft.server." + nmsVersion + ".PacketTabHeader");
            Class<?> nmsPacket = Class.forName("net.minecraft.server." + nmsVersion + ".Packet");
            if (nmsVersion.equalsIgnoreCase("v1_8_R1") || !nmsVersion.startsWith("v1_8_")) {
                Class<?> serializer = Class.forName("net.minecraft.server." + nmsVersion + ".ChatSerializer");
                Class<?> baseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Method a = serializer.getDeclaredMethod("a", String.class);
                Object header = baseComponent.cast(a.invoke(serializer, "{\"text\": \"" + tabHeader + "\"}"));
                Object footer = baseComponent.cast(a.invoke(serializer, "{\"text\": \"" + tabFooter + "\"}"));
                packet = tabHeaderPacket.getConstructor(baseComponent, byte.class).newInstance(header, footer, (byte) 2);
            } else {
                Class<?> chatComponent = Class.forName("net.minecraft.server." + nmsVersion + ".ChatComponentText");
                Class<?> baseComponent = Class.forName("net.minecraft.server." + nmsVersion + ".IChatBaseComponent");
                Object header = chatComponent.getConstructor(String.class).newInstance(tabHeader);
                Object footer = chatComponent.getConstructor(String.class).newInstance(tabFooter);
                packet = tabHeaderPacket.getConstructor(baseComponent, byte.class).newInstance(tabHeader, tabFooter, (byte) 2);
            }
            Method getHandle = obcPlayer.getDeclaredMethod("getHandle");
            Object handle = getHandle.invoke(p);
            Field playerConnection = handle.getClass().getDeclaredField("playerConnection");
            Object playerConnectionHandle = playerConnection.get(handle);
            Method sendPacket = playerConnectionHandle.getClass().getDeclaredMethod("sendPacket", nmsPacket);
            sendPacket.invoke(playerConnectionHandle, packet);
        } catch (Exception e) {
            plugin.getLogger().severe("Could not send Tab Text packet!");
            e.printStackTrace();
        }
    }
}
