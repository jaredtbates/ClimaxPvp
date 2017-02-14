package net.climaxmc.KitPvp.Utils.Tag;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.donations.trails.ParticleEffect;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.yaml.snakeyaml.nodes.Tag;

public class TagEvents implements Listener {
    private ClimaxPvp plugin;

    public TagEvents(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    TagFiles tagFiles = new TagFiles();
    TagUtils tagUtils = new TagUtils(plugin);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        if (ClimaxPvp.inTag.contains(player)) {

            killer.setHealth(20);
            killer.setFireTicks(0);
            killer.removePotionEffect(PotionEffectType.REGENERATION);
            ClimaxPvp.isIt = null;

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                player.setGameMode(GameMode.CREATIVE);
                player.setHealth(20);
                for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                    plugin.hideEntity(players, player);
                }
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setVelocity(player.getVelocity().setY(1.2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 21, 0));
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
            });

            if (!ClimaxPvp.tagPlayerDeaths.containsKey(player)) {
                ClimaxPvp.tagPlayerDeaths.put(player, 0);
            }

            ClimaxPvp.tagPlayerDeaths.put(player, ClimaxPvp.tagPlayerDeaths.get(player) + 1);

            int randomPoint = (int)(Math.random() * 10 + 1);

            Bukkit.getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                public void run() {
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.showPlayer(player);
                    }
                    player.getInventory().clear();
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    player.getInventory().setBoots(new I(Material.DIAMOND_BOOTS).enchantment(Enchantment.PROTECTION_FALL, 1));
                    player.getInventory().addItem(new I(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 100));
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.teleport(tagFiles.getArenaPoint(randomPoint));
                    tagUtils.setIt(player);
                }
            }, 20L * 3);

            tagUtils.updateInGameScoreboards();

            if (ClimaxPvp.inTag.contains(killer)) {
                if (ClimaxPvp.isIt == killer) {
                    for (Player players : ClimaxPvp.inTag) {
                        players.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has been tagged by " + killer.getName());
                        players.playSound(players.getLocation(), Sound.EXPLODE, 1, 1);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (ClimaxPvp.inTag.contains(player) || ClimaxPvp.tagSpectators.contains(player)) {
            ClimaxPvp.inTag.remove(player);
            ClimaxPvp.tagSpectators.remove(player);
            ClimaxPvp.tagWinners.remove(player);
            ClimaxPvp.inTagLobby.remove(player);
        }
    }
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        /*if (event.getMessage().toLowerCase().startsWith("/tag")) {
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "Tag is currently disabled!");
            event.setCancelled(true);
        }*/
        if (ClimaxPvp.inTag.contains(player)) {
            if (!event.getMessage().toLowerCase().startsWith("/tag")) {
                player.sendMessage(ChatColor.RED + "You can only do /tag while in tag! Do /tag leave to leave!");
                player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER) || !event.getEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        if (ClimaxPvp.inTag.contains(player) && ClimaxPvp.inTag.contains(target)) {
            if (player.equals(ClimaxPvp.isIt)) {
                return;
            }
            if (target.equals(ClimaxPvp.isIt)) {
                return;
            }
            event.setCancelled(true);
        }
    }
    int moveCounter = 20;
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ClimaxPvp.inTag.contains(player)) {
            if (ClimaxPvp.isIt == player) {
                moveCounter--;
                if (moveCounter <= 0) {
                    moveCounter = 20;
                    Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();
                    fireworkMeta.setPower(0);
                    FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(true).withColor(Color.AQUA).withFade(Color.BLUE).build();
                    fireworkMeta.addEffect(fireworkEffect);
                    firework.setFireworkMeta(fireworkMeta);
                }
            }
        }
    }
}