package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GuardianKit extends Kit {
    public GuardianKit() {
        super("Guardian", new ItemStack(Material.BEACON), "Use the guardian's LAZER to attack players!", ChatColor.GOLD);
    }

    @Override
    public void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "LAZER");
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
        addSoup(player.getInventory(), 1, 35);
    }

    @Override
    public void wearNoSoup(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "LAZER");
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.STICK));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!KitManager.isPlayerInKit(player, this)) {
            return;
        }

        if (!event.getItem().getType().equals(Material.DIAMOND_SWORD)) {
            return;
        }

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
            return;
        }

        Guardian guardian = (Guardian) player.getWorld().spawnEntity(player.getLocation(), EntityType.GUARDIAN);
        guardian.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
        guardian.setTarget(player);
        Bukkit.getScheduler().runTaskLater(ClimaxPvp.getInstance(), guardian::remove, 10);
    }
}
