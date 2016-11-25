package net.climaxmc.Administration.Commands;
/* Created by GamerBah on 3/6/2016 */

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class FreezeCommand implements CommandExecutor, Listener {
    public static boolean frozen = false;
    public static List<Player> frozenPlayers = new ArrayList<>();
    private ClimaxPvp plugin;

    public FreezeCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.MODERATOR)) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/freeze <all/[username]>");
            return true;
        }

        if (args[0].equalsIgnoreCase("all")) {
            if (!frozen) {
                frozen = true;
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "All players have been frozen by " + player.getName() + "!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData pData = plugin.getPlayerData(p);
                    p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                    if (!pData.hasRank(Rank.MODERATOR)) {
                        p.setWalkSpeed(0F);
                    }
                }
            } else {
                frozen = false;
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Movement has been re-enabled!");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData pData = plugin.getPlayerData(p);
                    if (!pData.hasRank(Rank.MODERATOR)) {
                        plugin.respawn(p);
                        p.setWalkSpeed(0.2F);
                    }
                    p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                }
                return true;
            }
        } else {
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null || KitPvp.getVanished().contains(target.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            if (frozenPlayers.contains(target)) {
                frozenPlayers.remove(target);
                target.setWalkSpeed(0.2F);
                plugin.respawn(target);
                target.sendMessage(ChatColor.RED + "Your movement has been re-enabled!");
                target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);

                player.sendMessage(ChatColor.GREEN + "You unfroze " + target.getName());
                player.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
            } else {
                frozenPlayers.add(target);
                target.setWalkSpeed(0F);
                target.sendMessage(ChatColor.RED + "You have been frozen by " + player.getName() + "!");
                target.sendMessage(ChatColor.RED + "You will automatically be unfrozen in 5 minutes.");
                target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);

                player.sendMessage(ChatColor.GREEN + "You have successfully frozen " + target.getName() + "!");
                player.sendMessage(ChatColor.RED + "They will automatically be unfrozen in 5 minutes.");
                player.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);


                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (frozenPlayers.contains(target)) {
                            frozenPlayers.remove(target);
                            target.setWalkSpeed(0.2F);
                            plugin.respawn(target);
                            target.sendMessage(ChatColor.RED + "Your movement has been automatically re-enabled!");
                            target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                        }
                    }
                }, 6000L);
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER))) {
            return;
        }

        if (frozen || frozenPlayers.contains(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        if (frozen) {
            damager.sendMessage(ChatColor.RED + "All players are frozen. You are unable to attack!");
            event.setCancelled(true);
        } else if (frozenPlayers.contains(damaged)) {
            damager.sendMessage(ChatColor.RED + "That player was frozen by a Staff Member. You are unable to attack them.");
            event.setCancelled(true);
        } else if (frozenPlayers.contains(damager)) {
            damager.sendMessage(ChatColor.RED + "You are unable to attack since you are frozen.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (frozen || frozenPlayers.contains(event.getPlayer())) {
            if (event.getFrom().getY() < event.getTo().getY()) {
                PlayerData pData = plugin.getPlayerData(event.getPlayer());
                if (!pData.hasRank(Rank.MODERATOR)) {
                    event.getPlayer().teleport(event.getFrom());
                }
            }
        }
    }
}
