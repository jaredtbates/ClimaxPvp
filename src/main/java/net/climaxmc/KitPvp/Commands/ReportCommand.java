package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Slack.SlackApi;
import net.climaxmc.KitPvp.Utils.Slack.SlackMessage;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    private HashMap<UUID, Integer> cooldown = new HashMap<>();

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

        Player reported = plugin.getServer().getPlayer(args[0]);

        if (reported == null) {
            player.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (!cooldown.containsKey(player.getUniqueId())) {
            String message = StringUtils.join(args, ' ', 1, args.length);

            player.sendMessage(ChatColor.GREEN + "You have successfully reported "
                    + ChatColor.DARK_AQUA + reported.getName() + ChatColor.GREEN + "!");

            plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff)
                    .hasRank(Rank.HELPER)).forEach(staff -> staff.sendMessage(ChatColor.RED + player.getName()
                    + " has reported " + ChatColor.BOLD + reported.getName() + ChatColor.RED + " for " + message + "!"));

            SlackApi slack = new SlackApi("https://hooks.slack.com/services/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");
            slack.call(new SlackMessage("Player Reported!", ">>>*" + player.getName() + "* _has reported_ *" + reported.getName() + "* _for:_ " + message));

            cooldown.put(player.getUniqueId(), 60);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (cooldown.get(player.getUniqueId()) >= 0) {
                        cooldown.replace(player.getUniqueId(), cooldown.get(player.getUniqueId()) - 1);
                    }

                    if (cooldown.get(player.getUniqueId()) == 0) {
                        cooldown.remove(player.getUniqueId());
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1.5F);
                        player.sendMessage(ChatColor.GREEN + "You are now able to report another player!");
                        this.cancel();
                    }
                }
            };
            int id = runnable.runTaskTimer(plugin, 1L, 20L).getTaskId();

        } else {
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.YELLOW
                    + cooldown.get(player.getUniqueId()) + " seconds " + ChatColor.RED + "before you report another player!");
            return false;
        }
        return false;
    }
}
