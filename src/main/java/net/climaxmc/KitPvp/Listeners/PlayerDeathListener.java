package net.climaxmc.KitPvp.Listeners;

import com.comphenix.protocol.PacketType;
import net.climaxmc.Administration.Commands.ChatCommands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Kits.FighterKit;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.ServerScoreboard;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import net.climaxmc.KitPvp.Utils.TextComponentMessages;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.events.EventManager;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.climaxmc.common.donations.trails.ParticleEffect;
import org.bukkit.*;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerDeathListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer == null) {
            return;
        }

        /**
         * Getting killer and player data
         */
        PlayerData killerData = plugin.getPlayerData(killer);
        PlayerData playerData = plugin.getPlayerData(player);


        /**
         * TextComponent message stuff by GamerBah I believe, looks really good tbh
         */
        TextComponentMessages tcm = new TextComponentMessages(plugin);
        TextComponent killerTCM = new TextComponent(killer.getName());
        killerTCM.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        killerTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tcm.playerStats(killer)));

        TextComponent killedTCM = new TextComponent(player.getName());
        killedTCM.setColor(net.md_5.bungee.api.ChatColor.RED);
        killedTCM.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tcm.playerStats(player)));

        TextComponent wkb = new TextComponent(" was killed by ");
        wkb.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        wkb.setHoverEvent(null);

        TextComponent tc = new TextComponent("");

        BaseComponent baseComponent = tc;
        baseComponent.addExtra(killedTCM);
        baseComponent.addExtra(wkb);
        baseComponent.addExtra(killerTCM);


        /**
         * Messages sent to the player killed about hearts and soups their killer had left.
         */
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
            player.setPlayerListName(/*rankTag + */playerData.getLevelColor() + playerData.getRank().getColor() + ChatColor.BOLD + player.getName());
        }
        if (playerData.getRank().getColor() != null) {
            player.setPlayerListName(playerData.getLevelColor() + playerData.getRank().getColor() + player.getName());
        } else {
            player.setPlayerListName(playerData.getLevelColor() + player.getName());
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

        /**
         * A small, often overlooked bit of code, but this line is quite integral to the gameplay of the entire server.
         */
        killer.setHealth(20);

        /**
         * Updating killstreak stuff and handling giving money to players on higher killstreaks
         */
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

        killer.setLevel(KitPvp.killStreak.get(killer.getUniqueId()));
        player.setLevel(0);

        /**
         * Trying setting top killstreak for the player.
         */
        if (killerData != null) {
            killerData.trySetTopKS(KitPvp.killStreak.get(killer.getUniqueId()) + 1);
        }

        if (ChatCommands.chatSilenced) {
        } else {
            if (plugin.getServer().getOnlinePlayers().size() >= 15) {
                killerData.addKills(1);
                playerData.addDeaths(1);
            } else {
                killerData.addKills(1);
                playerData.addDeaths(1);
                //event.setDeathMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " was killed by " + ChatColor.AQUA + killer.getName());
                plugin.getServer().spigot().broadcast(baseComponent);
            }
        }

        killerData.setKDR();

        /**
         * Updating stats on scoreboard on player death
         */
        ServerScoreboard killerScoreboard = plugin.getScoreboard(killer);
        killerScoreboard.updateScoreboard();
        ServerScoreboard playerScoreboard = plugin.getScoreboard(player);
        playerScoreboard.updateScoreboard();
    }

    @EventHandler
    public void onDeath (PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();

        player.setHealth(20);
        event.setDeathMessage(null);
        event.getDrops().clear();
        if (ClimaxPvp.getInstance().getWarpLocation("regen") != null) {
            if (player.getLocation().distance(ClimaxPvp.getInstance().getWarpLocation("regen")) <= 350) {
                return;
            }
        }

        /**
         * Broadcasting death message when killer is null
         */
        if (killer == null) {
            if (plugin.getServer().getOnlinePlayers().size() < 15) {
                Bukkit.broadcastMessage("" + ChatColor.RED + player.getName() + ChatColor.GRAY + " died");
            }
        }

        if (ClimaxPvp.playerDeathEffect.containsKey(player.getUniqueId())) {
            Location location = player.getLocation();
            location.setY(player.getLocation().getY() + 1);
            BukkitTask task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () ->
                    new ParticleEffect(ClimaxPvp.playerDeathEffect.get(player.getUniqueId()).getData()).sendToLocation(location), 2, 1);
            plugin.getServer().getScheduler().runTaskLater(plugin, task::cancel, 10);
        }

        if (ClimaxPvp.inFighterKit.contains(player)) {
            ClimaxPvp.inFighterKit.remove(player);
        }



        /**
         * This is for soup
         */
        /*if (player.getKiller() != null) {
            for (ItemStack item : player.getKiller().getInventory().getContents()) {
                if (item == null || item.getType().equals(Material.BOWL)) {
                    player.getWorld().dropItem(player.getLocation(), new I(Material.MUSHROOM_SOUP));
                }
            }
            for (int i = 1; i <= 7; i++) {
                player.getWorld().dropItem(player.getLocation(), new I(Material.MUSHROOM_SOUP));
            }
        }*/
    }

    @EventHandler
    public void rankupDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player target = player.getKiller();
        if (target != null) {
            PlayerData targetData = plugin.getPlayerData(target);

            if (targetData.getKills() == 100) {
                Bukkit.broadcastMessage(ChatUtils.color("&f\u00BB &b" + target.getName() + " &7has reached &9Blue &7tier!"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
            } else if (targetData.getKills() == 300) {
                Bukkit.broadcastMessage(ChatUtils.color("&f\u00BB &b" + target.getName() + " &7has reached &aGreen &7tier!"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
            } else if (targetData.getKills() == 500) {
                Bukkit.broadcastMessage(ChatUtils.color("&f\u00BB &b" + target.getName() + " &7has reached &cRed &7tier!"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
            } else if (targetData.getKills() == 700) {
                Bukkit.broadcastMessage(ChatUtils.color("&f\u00BB &b" + target.getName() + " &7has reached &6Gold &7tier!"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
            } else if (targetData.getKills() == 1000) {
                Bukkit.broadcastMessage(ChatUtils.color("&f\u00BB &b" + target.getName() + " &7has reached &5Purple &7tier!"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.playSound(players.getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
            }
        }
    }

    @EventHandler
    public void deathThing(PlayerDeathEvent event) {

        Player player = event.getEntity();

        EventManager eventManager = plugin.eventManager;

        if (eventManager.isInEvent(player.getUniqueId())) {
            return;
        }

        player.setGameMode(GameMode.CREATIVE);
        player.setHealth(20);

        player.setAllowFlight(true);
        player.setFlying(true);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 21, 0));
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                player.setVelocity(new Vector(player.getVelocity().getX(), 2.1, player.getVelocity().getZ()));
            }
        }, 1L);

        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
            plugin.hideEntity(players, player);
        }

        ClimaxPvp.deadPeoples.add(player);

        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
            public void run() {
                plugin.respawn(player);
            }
        }, 20L * 2);
    }
}
