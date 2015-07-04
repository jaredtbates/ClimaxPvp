package net.climaxmc.KitPvp.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class HackUtils {
    static ArrayList<UUID> cantBreak = new ArrayList<>();
    static ArrayList<UUID> waterWalking = new ArrayList<>();
    static ArrayList<UUID> cantClick = new ArrayList<>();
    static HashMap<UUID, Double> largestY = new HashMap<>();
    static HashMap<UUID, Long> lastPlaced = new HashMap<>();
    static HashMap<UUID, Long> lastClickedBow = new HashMap<>();
    static HashMap<UUID, Long> lastRegen = new HashMap<>();
    static HashMap<UUID, Long> animated = new HashMap<>();
    static HashMap<UUID, Double> Y = new HashMap<>();
    static HashMap<UUID, Location> lastBlockLoc = new HashMap<>();
    static HashMap<UUID, Long> lastBlockTime = new HashMap<>();
    static HashMap<UUID, Double> lastHealth = new HashMap<>();
    static HashMap<UUID, Long> lastForcefield = new HashMap<>();
    static HashMap<UUID, Long> lastNotification = new HashMap<>();
    static HashMap<UUID, Location> lastLoc = new HashMap<>();
    static HashMap<UUID, Block> lastBlockTarget = new HashMap<>();
    public static ArrayList<Location> instaBreak = new ArrayList<>();

    public static void clearPlayer(Player player) {
        if (cantBreak.contains(player.getUniqueId())) {
            cantBreak.remove(player.getUniqueId());
        }
        if (waterWalking.contains(player.getUniqueId())) {
            waterWalking.remove(player.getUniqueId());
        }
        if (largestY.containsKey(player.getUniqueId())) {
            largestY.remove(player.getUniqueId());
        }
        if (lastPlaced.containsKey(player.getUniqueId())) {
            lastPlaced.remove(player.getUniqueId());
        }
        if (lastClickedBow.containsKey(player.getUniqueId())) {
            lastClickedBow.remove(player.getUniqueId());
        }
        if (lastRegen.containsKey(player.getUniqueId())) {
            lastRegen.remove(player.getUniqueId());
        }
        if (animated.containsKey(player.getUniqueId())) {
            animated.remove(player.getUniqueId());
        }
        if (Y.containsKey(player.getUniqueId())) {
            Y.remove(player.getUniqueId());
        }
        if (lastBlockLoc.containsKey(player.getUniqueId())) {
            lastBlockLoc.remove(player.getUniqueId());
        }
        if (lastHealth.containsKey(player.getUniqueId())) {
            lastHealth.remove(player.getUniqueId());
        }
    }

    public static boolean cantBreak(Player player) {
        return cantBreak.contains(player.getUniqueId());
    }

    public static void setCantBreak(Player player) {
        if (!cantBreak.contains(player.getUniqueId())) {
            cantBreak.add(player.getUniqueId());
        }
    }

    public static boolean isWaterWalking(Player player) {
        return waterWalking.contains(player.getUniqueId());
    }

    public static void setCantMove(Player player) {
        if (!waterWalking.contains(player.getUniqueId())) {
            waterWalking.add(player.getUniqueId());
        }
    }

    public static void isLargestY(Player player) {
        if (!largestY.containsKey(player.getUniqueId())) {
            largestY.put(player.getUniqueId(), player.getVelocity().getY());
        } else if (player.getVelocity().getY() > largestY.get(player.getUniqueId())) {
            largestY.put(player.getUniqueId(), player.getVelocity().getY());
        }
    }

    public static Double getLargestY(Player player) {
        return largestY.get(player.getUniqueId());
    }

    public static long getLastPlaced(Player player) {
        if (lastPlaced.containsKey(player.getUniqueId())) {
            return lastPlaced.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void setLastPlaced(Player player, long time) {
        lastPlaced.put(player.getUniqueId(), time);
    }

    public static Long getLastBowClick(Player player) {
        if (lastClickedBow.containsKey(player.getUniqueId())) {
            return lastClickedBow.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void setLastBowClick(Player player, long time) {
        lastClickedBow.put(player.getUniqueId(), time);
    }

    public static boolean cantClick(Player player) {
        return cantClick.contains(player.getUniqueId());
    }

    public static void setCantClick(Player player) {
        if (!cantClick.contains(player.getUniqueId())) {
            cantClick.add(player.getUniqueId());
        }
    }

    public static long getLastRegen(Player player) {
        if (lastRegen.containsKey(player.getUniqueId())) {
            return lastRegen.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void setLastRegen(Player player, long time) {
        lastRegen.put(player.getUniqueId(), time);
    }

    public static long getLastAnimated(Player player) {
        if (animated.containsKey(player.getUniqueId())) {
            return animated.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void setLastAnimated(Player player, long time) {
        animated.put(player.getUniqueId(), time);
    }

    public static double getLastY(Player player) {
        if (Y.containsKey(player.getUniqueId())) {
            return Y.get(player.getUniqueId());
        }
        return 0.0D;
    }

    public static void setLastY(Player player, double y) {
        Y.put(player.getUniqueId(), y);
    }

    public static Location getLastLoc(Player player) {
        if (lastBlockLoc.containsKey(player.getUniqueId())) {
            return lastBlockLoc.get(player.getUniqueId());
        }
        return player.getLocation();
    }

    public static void setLastLoc(Player player) {
        lastBlockLoc.put(player.getUniqueId(), player.getLocation());
    }

    public static long getLastLocTime(Player player) {
        if (lastBlockTime.containsKey(player.getUniqueId())) {
            return lastBlockTime.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void setLastLocTime(Player player) {
        lastBlockTime.put(player.getUniqueId(), System.nanoTime());
    }

    public static Double getLastHealth(Player player) {
        if (lastHealth.containsKey(player.getUniqueId())) {
            return lastHealth.get(player.getUniqueId());
        }
        return 0.0D;
    }

    public static void setLastHealth(Player player) {
        lastHealth.put(player.getUniqueId(), player.getHealth());
    }

    public static long lastForcefield(Player player) {
        if (lastForcefield.containsKey(player.getUniqueId())) {
            return lastForcefield.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void updateForcefield(Player player) {
        lastForcefield.put(player.getUniqueId(), System.nanoTime());
    }

    public static long lastNotification(Player player) {
        if (lastNotification.containsKey(player.getUniqueId())) {
            return lastNotification.get(player.getUniqueId());
        }
        return 0L;
    }

    public static void updateLastNotification(Player player) {
        lastNotification.put(player.getUniqueId(), System.nanoTime());
    }

    public static boolean isInstaBreak(Location location) {
        for (Location loc : instaBreak) {
            if (loc == location) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Location> instaBreak() {
        return instaBreak;
    }

    public static void addInstaBreak(Location location) {
        instaBreak.add(location);
    }

    public static Location lastLoc(Player player) {
        if (lastLoc.containsKey(player.getUniqueId())) {
            return lastLoc.get(player.getUniqueId());
        }
        return null;
    }

    public static void updateLastLoc(Player player) {
        lastLoc.put(player.getUniqueId(), player.getLocation());
    }

    public static Block getLastTarget(Player player) {
        if (lastBlockTarget.containsKey(player.getUniqueId())) {
            return lastBlockTarget.get(player.getUniqueId());
        }
        return null;
    }

    public static void updateLastTarget(Player player) {
        HashSet<Material> a = new HashSet<>();
        lastBlockTarget.put(player.getUniqueId(), player.getTargetBlock(a, 5));
    }
}
