package net.climaxmc.KitPvp.Utils;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.ArctisKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.TimeUnit;

/**
 * Created by Joshua on 11/15/2016.
 */
public class CooldownFormat {

    private int i;

    public CooldownFormat (Player player, Ability ability, String abilityName) {
        Ability.Status status = ability.getStatus(player);
        for (i = 1; i <= 15; ++i) {
            Bukkit.getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (KitManager.isPlayerInKit(player, )) {
                        ItemStack ability = new ItemStack(Material.DIAMOND);
                        ItemMeta abilitymeta = ability.getItemMeta();
                        abilitymeta.setDisplayName(ChatColor.AQUA + "Absolute Zero §f» §8[§6" + status.getRemainingTime(TimeUnit.SECONDS) + "§8]");
                        ability.setItemMeta(abilitymeta);
                        player.getInventory().setItem(1, ability);
                    }
                }
            }, i * 20);
        }
    }
}
