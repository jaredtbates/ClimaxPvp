package net.climaxmc.KitPvp.Utils;

import lombok.Data;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author PaulBGD
 * @author computerwizjared
 */
@Builder
@Data
public class Title {
    private String title;
    private String subtitle;
    private int fadeIn = 20;
    private int fadeOut = 20;
    private int stay = 60;

    @SuppressWarnings("unchecked")
    public void send(Player... players) {
        if (fadeIn == 0) {
            fadeIn = 20;
        }
        if (fadeOut == 0) {
            fadeOut = 20;
        }
        if (stay == 0) {
            stay = 60;
        }

        /*try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
            Class<?> titlePacketClass = Class.forName(version + "PacketPlayOutTitle");
            Class<? extends Enum<?>> titleActionEnum = (Class<? extends Enum<?>>) Class.forName(version + "PacketPlayOutTitle$EnumTitleAction");
            Class<?> chatMessageClass = Class.forName(version + "util.CraftChatMessage");
            Class<?> baseComponentClass = Class.forName(version + "IChatBaseComponent");
            Class<?> packetClass = Class.forName(version + "Packet");
            Constructor<?> titlePacketConstructor = titlePacketClass.getConstructor(titleActionEnum, Array.newInstance(baseComponentClass, 0).getClass());
            Constructor<?> titleTimesPacketConstructor = titlePacketClass.getConstructor(int.class, int.class, int.class);
            Method chatMessageMethod = chatMessageClass.getMethod("fromString", String.class);
            Object titlePacket = titlePacketConstructor.newInstance(Enum.valueOf(titleActionEnum, "TITLE"), Array.get(chatMessageMethod.invoke(null, title), 0));
            Object subtitlePacket = titlePacketConstructor.newInstance(Enum.valueOf(titleActionEnum, "SUBTITLE"), Array.get(chatMessageMethod.invoke(null, subtitle), 0));
            Object timesPacket = titleTimesPacketConstructor.newInstance(fadeIn, stay, fadeOut);
            for (Player player : players) {
                Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
                Method sendPacket = playerConnection.getClass().getMethod("sendPacket", packetClass);
                sendPacket.invoke(playerConnection, timesPacket);
                sendPacket.invoke(playerConnection, titlePacket);
                sendPacket.invoke(playerConnection, subtitlePacket);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }*/
    }
}

