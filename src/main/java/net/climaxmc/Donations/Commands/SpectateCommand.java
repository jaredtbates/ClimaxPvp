package net.climaxmc.Donations.Commands;

import net.climaxmc.ClimaxPvp;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class SpectateCommand implements CommandExecutor {
    private ClimaxPvp plugin;

    public SpectateCommand(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getPlayerData(player);

        if (!playerData.hasRank(Rank.NINJA)) {
            player.sendMessage(ChatColor.RED + "Please donate at https://donate.climaxmc.net for access to spectator mode!");
            return true;
        }

        if (!ClimaxPvp.isSpectating.contains(player.getUniqueId())) {
            plugin.respawn(player);
            if (plugin.getCurrentWarps().containsKey(player.getUniqueId())) {
                player.teleport(plugin.getCurrentWarps().get(player.getUniqueId()));
            }
            player.setGameMode(GameMode.CREATIVE);
            player.setHealth(20);
            for(Player players : Bukkit.getServer().getOnlinePlayers()){
                players.hidePlayer(player);
            }
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.setAllowFlight(true);
            player.setFlying(true);
            player.getInventory().setItem(4, new I(Material.INK_SACK).durability(8).name(ChatColor.AQUA + ""));
            player.setVelocity(player.getVelocity().setY(0.7));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.setFlySpeed(0.15F);

            ClimaxPvp.isSpectating.add(player.getUniqueId());

            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are now spectating");
        } else {
            plugin.respawn(player);
            ClimaxPvp.isSpectating.remove(player.getUniqueId());
            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "You are no longer spectating");
        }

        return true;
    }
}
