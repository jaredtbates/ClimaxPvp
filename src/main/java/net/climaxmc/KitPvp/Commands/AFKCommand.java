package net.climaxmc.KitPvp.Commands;
/* Created by GamerBah on 3/30/2016 */

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private ClimaxPvp plugin;

    public AFKCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            player.sendMessage(ChatColor.RED + "For future reference, you don't need arguments for this command!");
        }

        if (CombatLogListeners.getTagged().containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You cannot change your AFK status while in combat!");
            return true;
        }

        if (KitPvp.getAfk().contains(player.getUniqueId())) {
            KitPvp.getAfk().remove(player.getUniqueId());
            plugin.respawn(player);
            player.sendMessage(ChatColor.AQUA + "You are no longer AFK");
        } else {
            KitPvp.getAfk().add(player.getUniqueId());
            plugin.respawn(player);
            player.sendMessage(ChatColor.AQUA + "You are now AFK");
        }
        return false;
    }

}