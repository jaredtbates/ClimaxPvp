package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.FighterKit;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.KitPvp.Utils.TextComponentMessages;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.donations.trails.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerDeathListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(EntityDamageEvent event) {
        if (!(event.getEntity().getType().equals(EntityType.PLAYER))){
            return;
        }
        Player player = (Player) event.getEntity();
        Player killer = player.getKiller();

        if (player.getHealth() - event.getDamage() <= 0) {

            Location location = player.getLocation();

            BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> new ParticleEffect(new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 1, 2, 1)).sendToLocation(location), 1, 1);
            plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);

            SettingsFiles settingsFiles = new SettingsFiles();
            if (settingsFiles.getRespawnValue(player) == true) {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    event.setCancelled(true);
                    player.setHealth(20);

                    plugin.respawn(player);

                    if (player.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {
                        new PvpKit().wearCheckLevel(player);
                    }
                });
            } else {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    event.setCancelled(true);
                    player.setHealth(20);
                    player.setGameMode(GameMode.CREATIVE);
                    for(Player players : Bukkit.getServer().getOnlinePlayers()){
                        players.hidePlayer(player);
                    }
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setVelocity(player.getVelocity().setY(1.5));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 21, 0));
                    player.getInventory().clear();
                    player.getInventory().setArmorContents(null);

                    ClimaxPvp.deadPeoples.add(player);

                    player.getInventory().setItem(4, new I(Material.BOOK).name("§6§lRespawn"));
                });
            }

            if (ClimaxPvp.inFighterKit.contains(player)) {
                ClimaxPvp.inFighterKit.remove(player);
            }

            PlayerData playerData = plugin.getPlayerData(player);
            playerData.addDeaths(1);

            ScoreboardListener scoreboardListener = new ScoreboardListener(plugin);
            scoreboardListener.updateScoreboards();

            if (killer == null) {
                if (plugin.getServer().getOnlinePlayers().size() >= 15) {
                    return;
                } else {
                    Bukkit.broadcastMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " died");
                }
                return;
            }

            TextComponentMessages tcm = new TextComponentMessages(plugin);
            TextComponent killerTCM = new TextComponent(killer.getName());
            killerTCM.setColor(ChatColor.AQUA);
            killerTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tcm.playerStats(killer)));
            killerTCM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/options " + killer.getName()));

            TextComponent killedTCM = new TextComponent(player.getName());
            killedTCM.setColor(ChatColor.RED);
            killedTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tcm.playerStats(player)));
            killedTCM.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/options " + player.getName()));

            TextComponent wkb = new TextComponent(" was killed by ");
            wkb.setColor(ChatColor.GRAY);
            wkb.setHoverEvent(null);

            TextComponent tc = new TextComponent("");

            BaseComponent baseComponent = tc;
            baseComponent.addExtra(killedTCM);
            baseComponent.addExtra(wkb);
            baseComponent.addExtra(killerTCM);

            if (ChatCommands.chatSilenced) {
                return;
            } else {
                if (plugin.getServer().getOnlinePlayers().size() >= 15) {
                    return;
                } else {
                    //event.setDeathMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.AQUA + killer.getName());
                    plugin.getServer().spigot().broadcast(baseComponent);
                }
            }

            if (killer.getHealth() % 2 == 0) {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + " hearts" + ChatColor.GRAY + " left");
            } else {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + ".5 hearts" + ChatColor.GRAY + " left");
            }

            if (killer.getName().equals(player.getName())) {
                return;
            }

            PlayerData killerData = plugin.getPlayerData(killer);
            killerData.addKills(1);
            killer.sendMessage("§f» §7You killed " + ChatColor.RED + player.getName());

            String rankTag = "";
            if (playerData.hasRank(Rank.NINJA)) {
                rankTag = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + playerData.getRank().getColor()
                        + ChatColor.BOLD + playerData.getRank().getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "] ";
            }
            if (killerData.getKills() == 150) {
                player.setPlayerListName(rankTag + playerData.getLevelColor() + player.getName());
            } else if (killerData.getKills() == 500) {
                player.setPlayerListName(rankTag + playerData.getLevelColor() + player.getName());
            } else if (killerData.getKills() == 1000) {
                player.setPlayerListName(rankTag + playerData.getLevelColor() + player.getName());
            } else if (killerData.getKills() == 1500) {
                player.setPlayerListName(rankTag + playerData.getLevelColor() + player.getName());
            }

        /*if(playerData.getKills() == 1) {
            playerData.addAchievement(Achievement.FIRST_KILL);
            killer.sendMessage("You got the achievement: Fresh from the Pile");
        }*/

        /*for (Challenge challenge : Challenge.values()) {
            if (plugin.getChallengesFiles().challengeIsStarted(killer, challenge)) {
                plugin.getChallengesFiles().addChallengeKill(killer, challenge);
                if (plugin.getChallengesFiles().getChallengeKills(killer, challenge) >= challenge.getKillRequirement()) {
                    plugin.getChallengesFiles().setCompleted(killer, challenge);
                    killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
                    killer.sendMessage(ChatColor.BOLD + "-------------------------------------------");
                    killer.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Challenge Complete: " + ChatColor.AQUA + challenge.getName());
                    killer.sendMessage(ChatColor.DARK_AQUA + "You've earned " + ChatColor.GREEN + "$" + challenge.getRewardMoney());
                    killer.sendMessage(ChatColor.DARK_AQUA + " for completing the challenge!");
                    killer.sendMessage(ChatColor.BOLD + "-------------------------------------------");
                    killerData.depositBalance(challenge.getRewardMoney());
                }
            }
        }*/

            if (killer.getLocation().distance(player.getWorld().getSpawnLocation()) <= 350) {
                killer.setHealth(20);
            }

            if (KitPvp.killStreak.containsKey(killer.getUniqueId())) {
                KitPvp.killStreak.put(killer.getUniqueId(), KitPvp.killStreak.get(killer.getUniqueId()) + 1);
                int killerAmount = KitPvp.killStreak.get(killer.getUniqueId());
                if (killerAmount % 5 == 0) {
                    plugin.getServer().broadcastMessage("" + ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " has reached a killstreak of " + ChatColor.RED + killerAmount + ChatColor.GRAY + "!");
                    killerAmount = killerAmount * 2 + 10;
                    killerData.depositBalance(killerAmount);
                    killer.sendMessage(" §7You have gained §b$" + killerAmount + "!");
                } else {
                    killerData.depositBalance(10);
                    killer.sendMessage(" §7You have gained §b$10!");
                    killer.sendMessage(" §7You have reached a killstreak of §b" + KitPvp.killStreak.get(killer.getUniqueId()) + "!");
                }
            } else {
                KitPvp.killStreak.put(killer.getUniqueId(), 1);
                killerData.depositBalance(10);
                killer.sendMessage(" §7You have gained §b$10!");
                killer.sendMessage(" §7You have reached a killstreak of §b" + KitPvp.killStreak.get(killer.getUniqueId()) + "!");
            }

            if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
                if (KitPvp.killStreak.get(player.getUniqueId()) >= 10) {
                    plugin.getServer().broadcastMessage("§f» §7" + killer.getName() + " destroyed " + ChatColor.RED + player.getName() + "'s " + ChatColor.GOLD + "killstreak of " + ChatColor.GREEN + KitPvp.killStreak.get(player.getUniqueId()) + "!");
                }
                KitPvp.killStreak.remove(player.getUniqueId());
            }
        }
    }
    @EventHandler
    public void onDeath (PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {
        Player player = event.getPlayer();
    }
    @EventHandler
    public void onEntityDamage (EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (damager.getGameMode().equals(GameMode.CREATIVE) && ClimaxPvp.deadPeoples.contains(damager)) {
                event.setCancelled(true);
            }
        }
    }
}
