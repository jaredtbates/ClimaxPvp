package net.climaxmc.KitPvp.Commands;

import net.climaxmc.Administration.Listeners.CombatLogListeners;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Duels.DuelUtils;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Duels.DuelFiles;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DuelCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public DuelCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public Player player;
    public Player target;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        player = (Player) sender;

        /*if (args.length == 0 || args.length > 3) {
            player.sendMessage(ChatColor.RED + "/duel <player>");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;
        }

        target = plugin.getServer().getPlayerExact(args[0]);

        if (target == null) {
            if (args[0].contains("accept")) {
                /*DuelUtils duelUtils = new DuelUtils(plugin);
                duelUtils.acceptRequest(player);
                return true;*/
                /*if (args.length > 3) {
                    player.sendMessage(ChatColor.RED + "/duel accept <player> <kit>");
                    return true;
                }
                if (args.length == 3) {
                    target = plugin.getServer().getPlayerExact(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have not received a request from that player!");
                        return true;
                    } else {
                        DuelUtils duelUtils = new DuelUtils(plugin);
                        duelUtils.acceptRequest(target);
                        return true;
                    }
                }
            }*/
        if (args.length == 0 || args.length > 3 && !args[0].contains("admin")) {
            player.sendMessage(ChatColor.RED + "/duel spec (Username)");
            player.sendMessage(ChatColor.RED + "/duel spec leave");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        } else {
            if (args[0].contains("spec")) {
                if (ClimaxPvp.inDuel.contains(player)) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You can't spectate a duel if you're in one!");
                    return true;
                }
                if (args.length == 2) {
                    if (args[1].contains("leave")) {
                        if (!ClimaxPvp.duelSpectators.contains(player)) {
                            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You are not spectating a duel!");
                            return true;
                        }
                        plugin.respawn(player);
                        for (Player dueling : ClimaxPvp.inDuel) {
                            plugin.hideEntity(player, dueling);
                        }
                        ClimaxPvp.isSpectating.remove(player.getUniqueId());
                        ClimaxPvp.duelSpectators.remove(player);
                        return true;
                    }
                    Player target = ClimaxPvp.getInstance().getServer().getPlayer(args[1]);
                    if (ClimaxPvp.inDuel.contains(target) && target != null) {
                        if (CombatLogListeners.getTagged().containsKey(player.getUniqueId())) {
                            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Teleporting in 5 seconds, don't move...");

                            double x = player.getLocation().getBlockX();
                            double y = player.getLocation().getBlockY();
                            double z = player.getLocation().getBlockZ();

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ClimaxPvp.getInstance(), new Runnable() {
                                public void run() {
                                    if (player.getLocation().getBlockX() == x && player.getLocation().getBlockY() == y && player.getLocation().getBlockZ() == z) {
                                        spectateDuel(player, target);
                                    } else {
                                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You moved! Teleport cancelled.");
                                    }
                                }
                            }, 20L * 5);
                        } else {
                            spectateDuel(player, target);
                        }
                    } else {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "That player is not in a duel!");
                        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/duel spec (Username)");
                    player.sendMessage(ChatColor.RED + "/duel spec leave");
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                }
            }
        }

        PlayerData playerData = plugin.getPlayerData(player);
        if (playerData.hasRank(Rank.ADMINISTRATOR)) {
            if (args.length == 0 || args.length == 1 || args.length > 4) {
                player.sendMessage(ChatColor.RED + "/duel admin set <arena number> <1 or 2> (Must both be numbers)");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                return true;
            }
            if (args[1].contains("set")) {
                if (args.length == 2) {
                    player.sendMessage(ChatColor.RED + "/duel admin set <arena number> <1 or 2> (Must both be numbers)");
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    return true;
                } else if (args.length == 3) {
                    player.sendMessage(ChatColor.RED + "/duel admin set <arena number> <1 or 2> (Must both be numbers)");
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                    return true;
                }
                if (args.length == 4) {
                    String arenaNumber = args[2];
                    DuelFiles duelFiles = new DuelFiles();
                    if (args[3].equals("1")) {
                        duelFiles.setArenaPoint1(player, arenaNumber);
                        player.sendMessage(ChatColor.GRAY + "Set point 1 for arena " + arenaNumber);
                    } else if (args[4].equals("2")) {
                        duelFiles.setArenaPoint2(player, arenaNumber);
                        player.sendMessage(ChatColor.GRAY + "Set point 2 for arena " + arenaNumber);
                    } else {
                        player.sendMessage(ChatColor.RED + "The last argument must be either point 1 or 2!");
                    }
                }
                return true;
            }
        }
            /*player.sendMessage(org.bukkit.ChatColor.RED + "That player is not online!");
            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
            return true;*/

        /*if (ClimaxPvp.hasRequest.containsKey(player)) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You have already sent a duel request to this player!");
            return true;
        } else {
            if (ClimaxPvp.inDuel.contains(target)) {
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "That player is already in a duel!");
                return true;
            } else {
                if (target == player) {
                    player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You cannot duel yourself!");
                } else {
                    DuelUtils duelUtils = new DuelUtils(plugin);
                    duelUtils.openInventory(player);

                    ClimaxPvp.initialRequest.put(player, target);
                    ClimaxPvp.duelRequestReverse.put(target, player);

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            ClimaxPvp.initialRequest.remove(player);
                            ClimaxPvp.duelRequestReverse.remove(target);
                            ClimaxPvp.duelsKit.remove(player);
                        }
                    }, 20L * 15);
                }
            }
        }*/
        return false;
    }
    private void spectateDuel(Player player, Player target) {
        player.teleport(target.getLocation());
        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Now spectating: " + ChatColor.GOLD + target.getName()
                + ChatColor.GRAY + " vs. " + ChatColor.GOLD
                + (ClimaxPvp.isDueling.get(target) != null ? ClimaxPvp.isDueling.get(target).getName() : ClimaxPvp.isDuelingReverse.get(target).getName()));
        player.showPlayer(target);
        player.showPlayer(ClimaxPvp.isDueling.get(target) != null ? ClimaxPvp.isDueling.get(target) : ClimaxPvp.isDuelingReverse.get(target));
        ClimaxPvp.duelSpectators.add(player);
        for (Player players : Bukkit.getOnlinePlayers()) {
            plugin.hideEntity(players, player);
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setVelocity(player.getVelocity().setY(0.7));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.CREATIVE);
        player.setHealth(20);

        ClimaxPvp.isSpectating.add(player.getUniqueId());
    }
}
