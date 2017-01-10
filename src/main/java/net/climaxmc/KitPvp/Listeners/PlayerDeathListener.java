package net.climaxmc.KitPvp.Listeners;

import com.comphenix.protocol.PacketType;
import net.climaxmc.Administration.Commands.ChatCommands;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.FighterKit;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Utils.EntityHider;
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
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!(event.getEntity().getType().equals(EntityType.PLAYER))){
            return;
        }
        Player player = (Player) event.getEntity();
        Player killer = player.getKiller();
        if (ClimaxPvp.playerDeathEffect.containsKey(player.getUniqueId())) {
            Location location = player.getLocation();
            location.setY(player.getLocation().getY() + 1);
            BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () ->
                    new ParticleEffect(ClimaxPvp.playerDeathEffect.get(player.getUniqueId()).getData()).sendToLocation(location), 2, 1);
            plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);
        }

        SettingsFiles settingsFiles = new SettingsFiles();

        /*if (settingsFiles.getSpawnSoupValue(player)) {
            if (ClimaxPvp.inTag.contains(player)) {
                return;
            }
            //player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null) {
                    if (item.getType() == Material.MUSHROOM_SOUP || item.getType() == Material.BOWL || item.getType() == Material.FISHING_ROD) {
                        player.getInventory().removeItem(item);
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 2));
            player.removePotionEffect(PotionEffectType.REGENERATION);
        }*/

        if (!ClimaxPvp.inTag.contains(player)) {
            if (settingsFiles.getRespawnValue(player) || (ClimaxPvp.isTourneyHosted && ClimaxPvp.inTourney.contains(player))) {
                plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!ClimaxPvp.isTourneyHosted && !ClimaxPvp.inTourney.contains(player)) {
                        plugin.respawn(player);
                    }

                    if (player.getLocation().distance(plugin.getWarpLocation("Fair")) <= 50) {
                        new PvpKit().wearCheckLevel(player);
                    }
                    if (player.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50) {
                        player.getInventory().clear();
                        player.getInventory().addItem(new I(Material.DIAMOND_AXE).name(org.bukkit.ChatColor.WHITE + "Duel Axe " + org.bukkit.ChatColor.AQUA + "(Punch a player!)"));
                    }
                });
            } else {
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    player.setGameMode(GameMode.CREATIVE);
                    player.setHealth(20);
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        players.hidePlayer(player);
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

                    ClimaxPvp.deadPeoples.add(player);

                    player.getInventory().setItem(4, new I(Material.BOOK).name("\u00A76\u00A7lRespawn"));
                });
            }
        }

        if (ClimaxPvp.inDuel.contains(player)) {
            ClimaxPvp.getInstance().warp("Duel", player);
        }

        if (ClimaxPvp.inFighterKit.contains(player)) {
            ClimaxPvp.inFighterKit.remove(player);
        }

        PlayerData killerData = plugin.getPlayerData(killer);

        PlayerData playerData = plugin.getPlayerData(player);

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
                killerData.addKills(1);
                playerData.addDeaths(1);
                return;
            } else {
                killerData.addKills(1);
                playerData.addDeaths(1);
                //event.setDeathMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.AQUA + killer.getName());
                plugin.getServer().spigot().broadcast(baseComponent);
            }
        }

        if (killer.getHealth() % 2 == 0) {
            player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + " hearts" + ChatColor.GRAY + " left");
        } else {
            player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + (((int) killer.getHealth()) / 2) + ".5 hearts" + ChatColor.GRAY + " left");
        }
        if (killer.getInventory().contains(Material.MUSHROOM_SOUP)) {
            ItemStack[] inv = killer.getInventory().getContents();
            int soup = 0;
            for(int i = 0; i < inv.length; i++){
                if(inv[i] != null){
                    if(inv[i].getType() == Material.MUSHROOM_SOUP){
                        soup = soup + inv[i].getAmount();
                    }
                }
            }
            if (soup == 1) {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + soup + " soup" + ChatColor.GRAY + " left");
            } else {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.RED + soup + " soups" + ChatColor.GRAY + " left");
            }
        }
        if (killer.getInventory().contains(Material.POTION)) {
            ItemStack[] inv = killer.getInventory().getContents();
            int soup = 0;
            for(int i = 0; i < inv.length; i++){
                if(inv[i] != null){
                    if(inv[i].getType() == Material.POTION){
                        soup = soup + inv[i].getAmount();
                    }
                }
            }
            if (soup == 1) {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.AQUA + soup + " potion" + ChatColor.GRAY + " left");
            } else {
                player.sendMessage("" + ChatColor.RED + killer.getName() + ChatColor.GRAY + " had " + ChatColor.AQUA + soup + " potions" + ChatColor.GRAY + " left");
            }
        }

        if (killer.getName().equals(player.getName())) {
            return;
        }

        killer.sendMessage("\u00A7f» \u00A77You killed " + ChatColor.RED + player.getName());

        String rankTag = "";
        if (playerData.hasRank(Rank.NINJA)) {
            rankTag = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + playerData.getRank().getColor()
                    + ChatColor.BOLD + playerData.getRank().getPrefix() + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "] ";
        }
        if (killerData.getKills() == 150) {
            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + player.getName());
            }
        } else if (killerData.getKills() == 500) {
            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + player.getName());
            }
        } else if (killerData.getKills() == 1000) {
            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + player.getName());
            }
        } else if (killerData.getKills() == 1500) {
            if (playerData.hasRank(Rank.NINJA)) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
            }
            if (playerData.getRank().getColor() != null) {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
            } else {
                player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + player.getName());
            }
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
                killerAmount = killerAmount * 2 + 20;
                killerData.depositBalance(killerAmount);
                killer.sendMessage("   \u00A77You have gained \u00A7b$" + killerAmount + "!");
            } else {
                killerData.depositBalance(10);
                killer.sendMessage("   \u00A77You have gained \u00A7b$10!");
                killer.sendMessage("   \u00A77You have reached a killstreak of \u00A7b" + KitPvp.killStreak.get(killer.getUniqueId()));
            }
        } else {
            KitPvp.killStreak.put(killer.getUniqueId(), 1);
            killerData.depositBalance(10);
            killer.sendMessage("   \u00A77You have gained \u00A7b$10!");
            killer.sendMessage("   \u00A77You have reached a killstreak of \u00A7b" + KitPvp.killStreak.get(killer.getUniqueId()));
        }

        if (KitPvp.killStreak.containsKey(player.getUniqueId())) {
            if (KitPvp.killStreak.get(player.getUniqueId()) >= 10) {
                plugin.getServer().broadcastMessage("\u00A7f» \u00A7b" + killer.getName() + " \u00A77destroyed " + ChatColor.RED + player.getName() + "'s " + "\u00A77killstreak of \u00A7a" + KitPvp.killStreak.get(player.getUniqueId()) + "!");
            }
            KitPvp.killStreak.remove(player.getUniqueId());
        }

        EntityHider entityHider = new EntityHider(ClimaxPvp.getInstance(), EntityHider.Policy.BLACKLIST);

        if (ClimaxPvp.inDuel.contains(player)) {
            if (ClimaxPvp.isDueling.containsKey(player)) {
                Player opponent = ClimaxPvp.isDueling.get(player);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.YELLOW + "The match has ended! Winner: " + ChatColor.GOLD + opponent.getDisplayName());
                opponent.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.YELLOW + "The match has ended! Winner: " + ChatColor.GOLD + opponent.getDisplayName());

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (!(player.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50)) {
                            plugin.respawn(player, ClimaxPvp.getInstance().getWarpLocation("Duel"));
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                allPlayers.showPlayer(player);
                            }
                        }
                    }
                }, 20L * 3);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (!(opponent.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50)) {
                            plugin.respawn(opponent, ClimaxPvp.getInstance().getWarpLocation("Duel"));
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                allPlayers.showPlayer(opponent);
                            }
                        }
                    }
                }, 20L * 3);

                ClimaxPvp.inDuel.remove(player);
                ClimaxPvp.inDuel.remove(opponent);
            } else {
                Player opponent = ClimaxPvp.isDuelingReverse.get(player);
                player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.YELLOW + "The match has ended! Winner: " + ChatColor.GOLD + opponent.getDisplayName());
                opponent.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.YELLOW + "The match has ended! Winner: " + ChatColor.GOLD + opponent.getDisplayName());

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (!(player.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50)) {
                            plugin.respawn(player, ClimaxPvp.getInstance().getWarpLocation("Duel"));
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                allPlayers.showPlayer(player);
                            }
                        }
                    }
                }, 20L * 3);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (!(opponent.getLocation().distance(plugin.getWarpLocation("Duel")) <= 50)) {
                            plugin.respawn(opponent, ClimaxPvp.getInstance().getWarpLocation("Duel"));
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                allPlayers.showPlayer(opponent);
                            }
                        }
                    }
                }, 20L * 3);

                ClimaxPvp.inDuel.remove(player);
                ClimaxPvp.inDuel.remove(opponent);
            }
        }
    }
    @EventHandler
    public void onDeath (PlayerDeathEvent event) {
        event.getEntity().setHealth(20);
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
        }
    }
}
