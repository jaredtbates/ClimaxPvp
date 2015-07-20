package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Kits.RangerKit;
import net.climaxmc.KitPvp.Kits.SoldierKit;
import net.climaxmc.KitPvp.Utils.HackUtils;
import net.climaxmc.common.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class HackListeners implements Listener {
    private ClimaxPvp plugin;
    private HashMap<Player, Map.Entry<Integer, Long>> speedTicks = new HashMap<>();
    private HashMap<Player, Map.Entry<Integer, Double>> floatTicks = new HashMap<>();
    private HashMap<Player, Map.Entry<Integer, Double>> hoverTicks = new HashMap<>();
    private HashMap<Player, Map.Entry<Integer, Double>> riseTicks = new HashMap<>();

    public HackListeners(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private void sendWarning(Player hacker, String hack) {
        plugin.getServer().getOnlinePlayers().stream().filter(player -> plugin.getPlayerData(player).hasRank(Rank.HELPER)).forEach(staff -> {
            if (HackUtils.lastNotification(hacker) != 0L) {
                if (TimeUnit.SECONDS.convert(System.nanoTime() - HackUtils.lastNotification(hacker), TimeUnit.NANOSECONDS) > 1L) {
                    TextComponent message = new TextComponent(ChatColor.DARK_PURPLE + hacker.getName() + ChatColor.DARK_GRAY + " tried to " + ChatColor.DARK_PURPLE + hack);
                    /*message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hd tp " + hacker.getName()));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to teleport to " + hacker.getName()).create()));*/
                    staff.spigot().sendMessage(message);
                    HackUtils.updateLastNotification(hacker);
                }
            } else {
                HackUtils.updateLastNotification(hacker);
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HackUtils.setLastHealth(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().distance(event.getTo()) != 0.0D) {
            HackUtils.setLastLoc(player);
            HackUtils.getLastLocTime(player);


            HackUtils.setLastLocTime(player);
            if ((event.getTo().getBlock().getRelative(0, -1, 0).getType() == Material.AIR) || (event.getTo().getBlock().getRelative(0, -1, 0).isLiquid())) {
                Location f = event.getFrom();
                Location t = event.getTo();

                double d = t.getY() - f.getY();
                int distance = t.getBlockY() - f.getBlockY();
                if (d > 0.0D) {
                    HackUtils.setLastY(player, distance + HackUtils.getLastY(player));
                } else if (d < 0.0D) {
                    if (HackUtils.getLastY(player) > 0.0D) {
                        HackUtils.setLastY(player, distance);
                    } else {
                        HackUtils.setLastY(player, HackUtils.getLastY(player) + distance);
                    }
                }
            } else {
                HackUtils.setLastY(player, 0.0D);
            }
        }
    }

    @EventHandler
    public void onPlayerAnimate(PlayerAnimationEvent event) {
        HackUtils.setLastAnimated(event.getPlayer(), System.nanoTime());
    }

    @EventHandler
    public void onPlayerWaterWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if ((player.getLocation() != HackUtils.getLastLoc(player)) && (HackUtils.getLastLoc(player).getBlock().getType() != Material.AIR) &&
                (!player.getLocation().getBlock().getType().name().contains("WATER")) && (!player.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType().name().contains("WATER"))) {
            if (player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().name().contains("WATER")) {
                if (HackUtils.getLastY(player) == 0.0D) {
                    sendWarning(player, "WATER WALKING");
                } else if (HackUtils.getLastY(player) == 0.4199999868869782D) {
                    sendWarning(player, "WATER WALKING");
                }
            } else if ((player.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock().getType().name().contains("WATER")) && (player.getLocation().getBlock().getType() == Material.AIR) && (
                    (HackUtils.getLastY(player) == 0.0D) || (HackUtils.getLastY(player) == 0.4199999868869782D))) {
                sendWarning(player, "WATER WALKING");
            }
        }
    }

    @EventHandler
    public void onPlayerReachTooFar(EntityDamageByEntityEvent event) {
        if ((event.getDamager() instanceof Player)) {
            Player player = (Player) event.getDamager();
            if (!player.getNearbyEntities(3.5D, 3.0D, 3.5D).contains(event.getEntity())) {
                //event.setCancelled(true);
                sendWarning(player, "REACH");
            }
        }
    }

    // Copied from Mineplex Anti-Cheat
    @EventHandler
    public void onPlayerRunTooFast(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
            return;
        }

        int count = 0;
        if (speedTicks.containsKey(player)) {
            double offset;
            if (event.getFrom().getY() > event.getTo().getY()) {
                offset = offset2d(event.getFrom(), event.getTo());
            } else {
                offset = offset(event.getFrom(), event.getTo());
            }
            double limit = 0.5;
            if (isGrounded(player)) {
                limit = 0.32;
            }
            for (final PotionEffect effect : player.getActivePotionEffects()) {
                if (effect.getType().equals(PotionEffectType.SPEED)) {
                    if (isGrounded(player)) {
                        limit += 0.08 * (effect.getAmplifier() + 1);
                    } else {
                        limit += 0.04 * (effect.getAmplifier() + 1);
                    }
                }
            }
            if (offset > limit && !elapsed(speedTicks.get(player).getValue(), 150L)) {
                count = speedTicks.get(player).getKey() + 1;
            } else {
                count = 0;
            }
        }
        if (count > 6) {
            sendWarning(player, "SPEED");
            player.damage(1000);
            count = 0;
        }
        speedTicks.put(player, new AbstractMap.SimpleEntry<>(count, System.currentTimeMillis()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void updateFlyhack(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (isValid(player, true)) {
            return;
        }

        if (offset(event.getFrom(), event.getTo()) <= 0.0) {
            this.updateFloat(player);
            return;
        }

        this.updateHover(player);

        if (!(KitManager.isPlayerInKit(player, SoldierKit.class) || KitManager.isPlayerInKit(player, RangerKit.class))) {
            this.updateRise(player);
        }
    }

    private void updateFloat(final Player player) {
        int count = 0;
        if (this.floatTicks.containsKey(player)) {
            if (player.getLocation().getY() == this.floatTicks.get(player).getValue()) {
                count = this.floatTicks.get(player).getKey() + 1;
            } else {
                count = 0;
            }
        }
        if (count > 6) {
            sendWarning(player, "Fly (Float)");
            /*player.damage(1000);*/
            count = 0;
        }
        this.floatTicks.put(player, new AbstractMap.SimpleEntry<>(count, player.getLocation().getY()));
    }

    private void updateHover(final Player player) {
        int count = 0;
        if (this.hoverTicks.containsKey(player)) {
            if (player.getLocation().getY() == this.hoverTicks.get(player).getValue()) {
                count = this.hoverTicks.get(player).getKey() + 1;
            } else {
                count = 0;
            }
        }
        if (count > 3) {
            sendWarning(player, "Fly (Hover)");
            /*player.damage(1000);*/
            count = 0;
        }
        this.hoverTicks.put(player, new AbstractMap.SimpleEntry<>(count, player.getLocation().getY()));
    }

    private void updateRise(Player player) {
        int count = 0;
        if (this.riseTicks.containsKey(player)) {
            if (player.getLocation().getY() > this.riseTicks.get(player).getValue()) {
                boolean nearBlocks = false;
                for (final Block block : getSurrounding(player.getLocation().getBlock(), true)) {
                    if (block.getType() != Material.AIR) {
                        nearBlocks = true;
                        break;
                    }
                }
                if (nearBlocks) {
                    count = 0;
                } else {
                    count = this.riseTicks.get(player).getKey() + 1;
                }
            } else {
                count = 0;
            }
        }
        if (count > 6) {
            sendWarning(player, "Fly (Rise)");
            /*player.damage(1000);*/
            count = 0;
        }
        this.riseTicks.put(player, new AbstractMap.SimpleEntry<>(count, player.getLocation().getY()));
    }

    private boolean isGrounded(Entity ent) {
        boolean onGround = false;

        try {
            Object nmsEntity = ent.getClass().cast(Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf(".") + 1) + ".entity.CraftEntity")).getClass().getMethod("getInstance").invoke(ent);
            onGround = nmsEntity.getClass().getField("onGround").getBoolean(nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return onGround;
    }

    private double offset2d(Location a, Location b) {
        return offset2d(a.toVector(), b.toVector());
    }

    private double offset2d(org.bukkit.util.Vector a, org.bukkit.util.Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    private double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    private double offset(org.bukkit.util.Vector a, org.bukkit.util.Vector b) {
        return a.subtract(b).length();
    }

    private boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    private boolean isValid(Player player, boolean groundValid) {
        return player.isFlying() || player.isInsideVehicle() || player.getGameMode() != GameMode.SURVIVAL || (groundValid && (onBlock(player) || player.getLocation().getBlock().getType() != Material.AIR));
    }

    private boolean onBlock(Player player) {
        double xMod = player.getLocation().getX() % 1.0;
        if (player.getLocation().getX() < 0.0) {
            ++xMod;
        }
        double zMod = player.getLocation().getZ() % 1.0;
        if (player.getLocation().getZ() < 0.0) {
            ++zMod;
        }
        int xMin = 0;
        int xMax = 0;
        int zMin = 0;
        int zMax = 0;
        if (xMod < 0.3) {
            xMin = -1;
        }
        if (xMod > 0.7) {
            xMax = 1;
        }
        if (zMod < 0.3) {
            zMin = -1;
        }
        if (zMod > 0.7) {
            zMax = 1;
        }
        for (int x = xMin; x <= xMax; ++x) {
            for (int z = zMin; z <= zMax; ++z) {
                if (player.getLocation().add((double) x, -0.5, (double) z).getBlock().getType() != Material.AIR && !player.getLocation().add((double) x, -0.5, (double) z).getBlock().isLiquid()) {
                    return true;
                }
                final Material beneath = player.getLocation().add((double) x, -1.5, (double) z).getBlock().getType();
                if (player.getLocation().getY() % 0.5 == 0.0 && (beneath == Material.FENCE || beneath == Material.NETHER_FENCE || beneath == Material.COBBLE_WALL)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<Block> getSurrounding(Block block, boolean diagonals) {
        final ArrayList<Block> blocks = new ArrayList<>();
        if (diagonals) {
            for (int x = -1; x <= 1; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if (x != 0 || y != 0 || z != 0) {
                            blocks.add(block.getRelative(x, y, z));
                        }
                    }
                }
            }
        } else {
            blocks.add(block.getRelative(BlockFace.UP));
            blocks.add(block.getRelative(BlockFace.DOWN));
            blocks.add(block.getRelative(BlockFace.NORTH));
            blocks.add(block.getRelative(BlockFace.SOUTH));
            blocks.add(block.getRelative(BlockFace.EAST));
            blocks.add(block.getRelative(BlockFace.WEST));
        }
        return blocks;
    }
}
