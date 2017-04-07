package net.climaxmc.Administration.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.*;

public class FreezeCommand implements CommandExecutor, Listener {
    private static boolean frozen = false;
    public static List<UUID> frozenPlayers = new ArrayList<>();
    private Map<Player, Location> portalLocation = new HashMap<>();
    private ClimaxPvp plugin;

    @SuppressWarnings("deprecation")
    public FreezeCommand(ClimaxPvp plugin) {
        this.plugin = plugin;


        Map<Player, Integer> count = new HashMap<>();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (UUID uuids : frozenPlayers) {
                    Player player = Bukkit.getPlayer(uuids);
                    if (count.containsKey(player)) {
                        player.getInventory().setHeldItemSlot(count.get(player));
                        for (int i = 0; i <= 9; i++) {
                            if (i % 2 == 0) {
                                player.getInventory().setItem(i, new I(Material.COAL)
                                        .name(ChatColor.translateAlternateColorCodes('&', "&cYou have been frozen! Join &ediscord.climaxmc.net! &c(Hit ALT+TAB to escape)")));
                            } else {
                                player.getInventory().setItem(i, new I(Material.COAL)
                                        .name(ChatColor.translateAlternateColorCodes('&', "&cYou have been frozen! Join &cdiscord.climaxmc.net! &c(Hit ALT+TAB to escape)")));
                            }
                        }

                        count.put(player, count.get(player) + 1);
                        if (count.get(player) >= 9) {
                            count.put(player, 0);
                        }
                    } else {
                        count.put(player, 0);
                    }

                    if (player.isOnGround()) {
                        plugin.lastValidLocation.put(player, player.getLocation());
                    }

                }
            }
        }, 0L, 5L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (player != null) {
            PlayerData playerData = plugin.getPlayerData(player);
            if (!playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
                return true;
            }
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/freeze [username]");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (frozenPlayers.contains(target.getUniqueId())) {
            frozenPlayers.remove(target.getUniqueId());

            if (player != null) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &c" + target.getName() + " &fwas un-frozen by &c" + player.getName()));
                }
            } else {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &c" + target.getName() + " &fwas un-frozen by &cConsole"));
                }
            }

            plugin.respawn(target);

            target.setWalkSpeed(0.2F);
            target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
            target.closeInventory();

            if (portalLocation.containsKey(target)) {
                portalLocation.get(target).getBlock().setType(Material.AIR);
            }

            if (player != null) {
                player.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
            }
        } else {

            frozenPlayers.add(target.getUniqueId());

            if (player != null) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &c" + target.getName() + " &fwas frozen by &c" + player.getName()));
                }
            } else {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f\u00BB &c" + target.getName() + " &fwas frozen by &cConsole"));
                }
            }

            target.setWalkSpeed(0F);

            if (plugin.lastValidLocation.get(target) != null) {
                target.teleport(plugin.lastValidLocation.get(target));
            } else {
                target.teleport(target.getLocation());
            }

            Location headLocation = target.getLocation().add(0L, 1L, 0L);
            portalLocation.put(target, headLocation);
            headLocation.getBlock().setType(Material.PORTAL);

            Location location = target.getLocation();
            plugin.freezeLocation.put(target, location);

            if (player != null) {
                target.sendMessage(ChatColor.RED + "You have been frozen by " + player.getName() + "!");
            } else {
                target.sendMessage(ChatColor.RED + "You have been frozen by " + "Console" + "!");
            }

            target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);

            if (player != null) {
                player.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
            }

            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    if (frozenPlayers.contains(target.getUniqueId())) {
                        frozenPlayers.remove(target.getUniqueId());
                        target.setWalkSpeed(0.2F);
                        target.sendMessage(ChatColor.RED + "Your movement has been automatically re-enabled!");
                        target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                    }
                }
            }, (20L * 60) * 30);
        }
        return false;
    }

    /*private void openFreezeInventory(Player player) {
        Inventory inventory = plugin.getServer().createInventory(null, 54, ChatColor.RED + "Frozen!");
        inventory.setItem(22, new I(Material.STAINED_CLAY).durability(14).name(ChatColor.translateAlternateColorCodes('&', "&cYou are frozen!"))
                .clearLore()
                .lore(ChatColor.YELLOW + "Join our discord at the link: discord.climaxmc.net!"));
        player.openInventory(inventory);
    }*/

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER))) {
            return;
        }

        if (frozen || frozenPlayers.contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event){
        if (event.isCancelled()) {
            return;
        }
        if (event.getChangedType().equals(Material.PORTAL)) {
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

        if (frozenPlayers.contains(damaged.getUniqueId())) {
            damager.sendMessage(ChatColor.RED + "That player was frozen by a Staff Member. You are unable to attack them.");
            event.setCancelled(true);
        } else if (frozenPlayers.contains(damager.getUniqueId())) {
            damager.sendMessage(ChatColor.RED + "You are unable to attack since you are frozen.");
            event.setCancelled(true);
        }
    }

    private int moveDelay = 0;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {
            if (frozenPlayers.contains(player.getUniqueId())) {
                if (plugin.freezeLocation.containsKey(player)) {
                    player.teleport(plugin.freezeLocation.get(player));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (frozenPlayers.contains(player.getUniqueId())) {
            frozenPlayers.remove(player.getUniqueId());
            if (portalLocation.containsKey(player)) {
                portalLocation.get(player).getBlock().setType(Material.AIR);
            }
            player.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
            for (Player players : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = plugin.getPlayerData(players);
                if (playerData.hasRank(Rank.TRIAL_MODERATOR)) {
                    players.sendMessage("");
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + player.getName() + " &chas logged out while frozen!"));
                    players.sendMessage("");
                    players.playSound(players.getLocation(), Sound.SUCCESSFUL_HIT, 1, 2);
                }
            }
        }
    }

    /*@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (frozenPlayers.contains(player.getUniqueId())) {
            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    openFreezeInventory(player);
                }
            }, 1L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (frozenPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }*/
}
