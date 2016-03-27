package net.climaxmc.KitPvp.Utils.Teams;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;

public class TeamUtils {
    private ClimaxPvp plugin;

    public TeamUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public static void createPendingRequest(Player target, Player sender) {
        KitPvp.pendingTeams.put(target.getName(), sender.getName());
    }

    public static void removePendingRequest(Player target) {
        if (KitPvp.pendingTeams.isEmpty() || !KitPvp.pendingTeams.containsKey(target.getName())) {
            return;
        } else {
            KitPvp.pendingTeams.remove(target.getName());
        }
    }

    public boolean hasPendingRequest(Player target) {
        return KitPvp.pendingTeams.containsKey(target.getName());
    }

    public static void createTeam(Player target, Player sender) {
        KitPvp.currentTeams.put(target.getName(), sender.getName());
        removePendingRequest(target);
    }

    public static void removeTeam(Player player, Player target) {
        if (KitPvp.currentTeams.isEmpty() || !KitPvp.currentTeams.containsKey(player.getName()) || KitPvp.currentTeams == null) {
            return;
        } else {
            KitPvp.currentTeams.remove(target.getName());
        }
    }

    public boolean isTeaming(Player target) {
        for (Player sender : plugin.getServer().getOnlinePlayers()) {
            if (KitPvp.currentTeams.containsKey(target.getName()) || KitPvp.currentTeams.containsValue(target.getName())) {
                return true;
            }
        }
        return false;
    }

    public Player getRequester(Player target) {
        return plugin.getServer().getPlayer(KitPvp.pendingTeams.get(target.getName()));
    }

}
