package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public PingCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String nmsVersion = plugin.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1);

        Player target = player;

        if (args.length == 1) {
            target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }
        } else if (args.length > 2) {
            player.sendMessage(ChatColor.RED + "/ping [player]");
        }

        int ping = -1;

        try {
            Object nmsPlayer = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".entity.CraftPlayer").cast(player).getClass().getMethod("getHandle").invoke(target);
            ping = nmsPlayer.getClass().getField("ping").getInt(nmsPlayer);
        } catch (Exception e) {
            plugin.getLogger().severe("Could not get ping of player!");
            e.printStackTrace();
        }

        if (player == target) {
            player.sendMessage(ChatColor.GREEN + "Your ping is " + ChatColor.GOLD + ping + "ms" + ChatColor.GREEN + ".");
        } else {
            player.sendMessage(ChatColor.GREEN + target.getName() + "'s ping is " + ChatColor.GOLD + ping + "ms" + ChatColor.GREEN + ".");
        }

        return true;
    }
}
