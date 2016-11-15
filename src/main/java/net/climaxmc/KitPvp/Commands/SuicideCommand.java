package net.climaxmc.KitPvp.Commands;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        ClimaxPvp.getInstance().getServer().getScheduler().runTask(ClimaxPvp.getInstance(), () -> {
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

        return true;
    }
}
