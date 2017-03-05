package net.climaxmc.KitPvp.Commands;

import lombok.Getter;
import lombok.Setter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.ReportGUI;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    @Getter
    private static HashMap<UUID, Integer> cooldown = new HashMap<>();
    @Getter
    private static HashMap<UUID, String> reportBuilders = new HashMap<>();
    @Getter
    private static HashMap<UUID, ArrayList<String>> reportArray = new HashMap<>();

    public ReportCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/report <player> <reason>");
            return true;
        }

        Player reported = plugin.getServer().getPlayerExact(args[0]);

        if (reported == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (reported == player) {
            player.sendMessage(ChatColor.RED + "You can't report yourself! Unless you have something to tell us... *gives suspicious look*");
            return true;
        }

        String message = StringUtils.join(args, ' ', 1, args.length);

        if (!cooldown.containsKey(player.getUniqueId())) {

            ReportCommand.getCooldown().put(player.getUniqueId(), 60);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (ReportCommand.getCooldown().get(player.getUniqueId()) >= 0) {
                        ReportCommand.getCooldown().replace(player.getUniqueId(), ReportCommand.getCooldown().get(player.getUniqueId()) - 1);
                    }

                    if (ReportCommand.getCooldown().get(player.getUniqueId()) == 0) {
                        ReportCommand.getCooldown().remove(player.getUniqueId());
                        this.cancel();
                    }
                }
            };
            runnable.runTaskTimer(plugin, 1L, 20L).getTaskId();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &cSuccessfully reported &e" + reported.getName() + "!"));

            for (Player players : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = plugin.getPlayerData(players);
                if (playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + player.getName() + " &chas reported &c&l" + reported.getName() + " &cfor &c&l" + message));
                    players.playSound(players.getLocation(), Sound.NOTE_PLING, 1, 1);
                }
            }
        } else {
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.YELLOW
                    + cooldown.get(player.getUniqueId()) + " seconds " + ChatColor.RED + "before you report another player!");
            return false;
        }
        return false;
    }
}
