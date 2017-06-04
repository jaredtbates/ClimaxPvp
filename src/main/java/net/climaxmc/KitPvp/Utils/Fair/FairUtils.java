package net.climaxmc.KitPvp.Utils.Fair;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kits.FairKit;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import java.util.*;

public class FairUtils implements Listener {

    private ClimaxPvp plugin;
    public FairUtils(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private Map<Player, Player> fairRequest = new HashMap<>();
    public static List<Match> runningMatches = new ArrayList<>();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        if (plugin.getWarpLocation("Fair") != null) {
            if (target.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50 && damager.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {

                if (MatchManager.getOpponent(damager.getUniqueId()) != null) {
                    if (MatchManager.getOpponent(target.getUniqueId()) != null) {
                        if (!MatchManager.getOpponent(damager.getUniqueId()).equals(target.getUniqueId())
                                || !MatchManager.getOpponent(target.getUniqueId()).equals(damager.getUniqueId())) {
                            event.setCancelled(true);
                        }
                    }
                }

                if (!MatchManager.isInMatch(damager.getUniqueId()) && MatchManager.isInMatch(target.getUniqueId())) {
                    event.setCancelled(true);
                }

                if (MatchManager.isInMatch(damager.getUniqueId()) && !MatchManager.isInMatch(target.getUniqueId())) {
                    event.setCancelled(true);
                }

                if (plugin.isWithinProtectedRegion(target.getLocation())) {
                    event.setCancelled(true);
                    return;
                }

                if (!MatchManager.isInMatch(damager.getUniqueId())) {
                    event.setCancelled(true);
                }

                /**
                 * Accepting requests
                 */
                //Check to see if the target has a request from the damager.
                if (fairRequest.containsKey(target) && fairRequest.get(target).equals(damager)) {

                    if (MatchManager.isInMatch(damager.getUniqueId()) || MatchManager.isInMatch(target.getUniqueId())) {
                        return;
                    }

                    fairRequest.remove(target);
                    fairRequest.remove(damager);

                    FairKit.wear(damager);
                    FairKit.wear(target);

                    knockback(target, damager);
                    knockback(damager, target);

                    Match match1 = new Match(damager.getUniqueId(), target.getUniqueId());
                    Match match2 = new Match(target.getUniqueId(), damager.getUniqueId());
                    runningMatches.add(match1);
                    runningMatches.add(match2);

                    target.sendMessage(ChatUtils.color("&f\u00BB &7Fight starting!"));
                    damager.sendMessage(ChatUtils.color("&f\u00BB &7Fight starting!"));

                    return;

                }

                if (damager.getItemInHand() != null && damager.getItemInHand().getItemMeta() != null && damager.getItemInHand().getItemMeta().getDisplayName() != null) {
                    if (damager.getItemInHand().getItemMeta().getDisplayName().contains("duel")) {

                        if (MatchManager.isInMatch(damager.getUniqueId()) || MatchManager.isInMatch(target.getUniqueId())) {
                            return;
                        }

                        /**
                         * Check to see if the damager has already sent a request to the target.
                         */
                        if (fairRequest.containsKey(damager) && fairRequest.get(damager).equals(target)) {
                            damager.sendMessage(ChatUtils.color("&cYou've already sent a request to this player!"));
                            return;
                        }

                        /**
                         * Inform the target and damager that a request has been made.
                         */
                        target.sendMessage(ChatUtils.color("&f\u00BB &b" + damager.getName() + " &7has requested to fight!"));
                        damager.sendMessage(ChatUtils.color("&f\u00BB &7Sent a request to &b" + target.getName()));

                        /**
                         * Put the damager and target in the hashmap, thus creating a request from damager to target.
                         */
                        fairRequest.put(damager, target);

                        /**
                         * 10 seconds later it will remove the request so that another one can be made.
                         */
                        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                fairRequest.remove(damager, target);
                            }
                        }, 20L * 10);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Player killer = player.getKiller();
        if (killer != null) {
            if (plugin.getWarpLocation("Fair") != null) {
                if (killer.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {

                    killer.getInventory().clear();
                    killer.getInventory().setArmorContents(null);
                    killer.getInventory().setItem(0, new I(Material.DIAMOND_SWORD).name(ChatUtils.color("&bPunch to duel")));

                }
            }
        }

        for (Match match : runningMatches) {
            if (match.player.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
            if (match.opponent.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
        }
        for (Match match : runningMatches) {
            if (killer != null) {
                if (match.player.equals(killer.getUniqueId())) {
                    runningMatches.remove(match);
                }
                if (match.opponent.equals(killer.getUniqueId())) {
                    runningMatches.remove(match);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        for (Match match : runningMatches) {
            if (match.player.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
            if (match.opponent.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        for (Match match : runningMatches) {
            if (match.player.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
            if (match.opponent.equals(player.getUniqueId())) {
                runningMatches.remove(match);
            }
        }
    }

    private void knockback(Player player, Player target)
    {
        Location l = target.getLocation().subtract(player.getLocation());
        double distance = target.getLocation().distance(player.getLocation());
        Vector v = l.toVector().multiply(2/distance).setY(0.15);
        target.setVelocity(v);
    }
}
