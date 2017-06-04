package net.climaxmc.Administration.Commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

import java.util.*;

public class StaffMode implements CommandExecutor, Listener {

    private ClimaxPvp plugin;

    public StaffMode(ClimaxPvp plugin) {
        this.plugin = plugin;

        /*plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (plugin.getServer().getOnlinePlayers().size() <= 0) {
                return;
            }

            ByteArrayDataOutput bos = ByteStreams.newDataOutput();
            bos.writeUTF("ClimaxVanish");
            for (UUID vanishedUUID : vanished) {
                bos.writeUTF(vanishedUUID.toString());
            }
            Iterables.get(plugin.getServer().getOnlinePlayers(), 1).sendPluginMessage(plugin, "BungeeCord", bos.toByteArray());
        }, 200, 200);*/
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!(playerData.hasRank(Rank.TRIAL_MODERATOR))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute that command!");
            return true;
        }

        toggleStaffMode(player, playerData);

        return false;
    }

    @EventHandler
    public void onPlayerHurtEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntityType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))) {
            return;
        }
        Player damager = (Player) event.getDamager();

        if (KitPvp.getVanished().contains(damager.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        KitPvp.getVanished().stream().filter(vanishedPlayer ->
                plugin.getServer().getPlayer(vanishedPlayer) != null)
                .forEach(vanishedPlayer -> player.hidePlayer(plugin.getServer().getPlayer(vanishedPlayer)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (KitPvp.getVanished().contains(player.getUniqueId())) {
            KitPvp.getVanished().remove(player.getUniqueId());
        }
    }

    private void toggleStaffMode(Player player, PlayerData playerData) {
        if (!KitPvp.getVanished().contains(player.getUniqueId())) {
            player.setPlayerListName(null);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Staff mode enabled.");
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are now vanished.");
            player.setAllowFlight(true);
            player.setFlying(true);
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));

            staffModeInventory(player);

            KitPvp.getVanished().add(player.getUniqueId());
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Staff mode disabled.");
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are no longer vanished.");
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));

            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(playerData.getLevelColor() + player.getName());
            }

            KitPvp.getVanished().remove(player.getUniqueId());
            KitPvp.getChecking().remove(player.getUniqueId());
            plugin.respawn(player);
        }
    }

    private void toggleVanish(Player player) {
        if (!KitPvp.getVanished().contains(player.getUniqueId())) {
            player.setPlayerListName(null);
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are now vanished.");
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.hidePlayer(player));
            KitPvp.getVanished().add(player.getUniqueId());
            staffModeInventory(player);
        } else {

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setItem(8, new I(Material.INK_SACK).durability(8).name(ChatColor.AQUA + "Activate Vanish"));

            PlayerData playerData = plugin.getPlayerData(player);
            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(playerData.getLevelColor() + player.getName());
            }

            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are no longer vanished.");
            plugin.getServer().getOnlinePlayers().stream().forEach(target -> target.showPlayer(player));
            KitPvp.getVanished().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null) {
            ItemStack item = event.getItem();
            if (event.getItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (item.getItemMeta().getDisplayName().contains("Activate Vanish")) {
                toggleVanish(player);
            }
            if (KitPvp.getVanished().contains(player.getUniqueId())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (item.getItemMeta().getDisplayName().contains("Leave Staff Mode")) {
                        toggleStaffMode(player, ClimaxPvp.getInstance().getPlayerData(player));
                    }
                    if (item.getItemMeta().getDisplayName().contains("Leave Vanish")) {
                        toggleVanish(player);
                    }
                    if (item.getItemMeta().getDisplayName().contains("Random TP")) {
                        List<Player> players = new ArrayList<>();
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (!player.equals(online)) {
                                players.add(online);
                            }
                        }
                        if (!players.isEmpty()) {
                            Player randomPlayer = players.get((int) (Math.random() * players.size()));
                            player.teleport(randomPlayer.getLocation());
                            player.setVelocity(player.getVelocity().setY(1.5));
                        } else {
                            player.sendMessage(ChatUtils.color("&cNo one to teleport to!"));
                        }
                    }
                }
            }
        }
    }

    private void staffModeInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(0, new I(Material.REDSTONE_TORCH_ON).name(ChatUtils.color("&cLeave Staff Mode")));
        player.getInventory().setItem(2, new I(Material.BLAZE_ROD).name(ChatUtils.color("&6Random TP")));
        player.getInventory().setItem(4, new I(Material.ICE).name(ChatUtils.color("&bFreeze Player")));
        player.getInventory().setItem(6, new I(Material.OBSIDIAN).name(ChatUtils.color("&cCheck CPS")));
        player.getInventory().setItem(8, new I(Material.INK_SACK).durability(8).name(ChatColor.AQUA + "Leave Vanish"));
    }

    private Map<UUID, UUID> checkingCPS = new HashMap<>();

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player target = (Player) event.getRightClicked();

            if (KitPvp.getVanished().contains(player.getUniqueId())) {
                if (player.getItemInHand() != null) {
                    ItemStack item = player.getItemInHand();
                    if (item.getItemMeta().getDisplayName().contains("Check CPS")) {
                        player.getInventory().clear();
                        player.getInventory().setItem(0, new I(Material.INK_SACK).durability(1).name(ChatUtils.color("&cStop CPS Check")));
                        player.getInventory().setItem(6, new I(Material.OBSIDIAN).name(ChatUtils.color(target.getName() + " &f\u00BB &c0")));
                        checkingCPS.put(player.getUniqueId(), target.getUniqueId());
                    }
                    if (item.getItemMeta().getDisplayName().contains("Freeze Player")) {
                        FreezeCommand freezeCommand = new FreezeCommand(plugin);
                        freezeCommand.tryFreezePlayer(player, target);
                    }
                }
            }
        }
    }

    private Map<UUID, Integer> playerCPS = new HashMap<>();
    private Map<UUID, Integer> cpsCounter = new HashMap<>();

    @EventHandler
    public void cpsCheckInteract(PlayerInteractEvent event) {

        if (event.getItem() != null && event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().contains("Stop CPS Check")) {
            staffModeInventory(event.getPlayer());
            checkingCPS.remove(event.getPlayer().getUniqueId());
        }

        Player player = event.getPlayer();
        if (!cpsCounter.containsKey(player.getUniqueId())) {
            cpsCounter.put(player.getUniqueId(), 0);
        }
        if (cpsCounter.get(player.getUniqueId()) == 0) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    playerCPS.put(player.getUniqueId(), cpsCounter.get(player.getUniqueId()));
                    cpsCounter.put(player.getUniqueId(), 0);
                }
            }, 20L);
        }

        cpsCounter.put(player.getUniqueId(), cpsCounter.get(player.getUniqueId()) + 1);


        for (UUID uuids : checkingCPS.keySet()) {
            Player staff = Bukkit.getPlayer(uuids);
            Player target = Bukkit.getPlayer(checkingCPS.get(uuids));

            if (event.getPlayer().getUniqueId() == target.getUniqueId()) {
                if (!playerCPS.containsKey(target.getUniqueId())) {
                    playerCPS.put(target.getUniqueId(), 0);
                }

                staff.getInventory().setItem(6, new I(Material.OBSIDIAN).name(ChatUtils.color(target.getName() + " &f\u00BB &c" + playerCPS.get(target.getUniqueId()))));
            }
        }
    }
}
