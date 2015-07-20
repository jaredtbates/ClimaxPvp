package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LazerKit extends Kit {
    public LazerKit() {
        super("Lazer", new ItemStack(Material.BEACON), "Use the LAZER to attack players!", ChatColor.GOLD);
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

        //new Line(player.getLocation(), player.getTargetBlock((Set<Material>) null, 10).getLocation()).forEach(block -> new ParticleEffect(new ParticleEffect.ParticleData(ParticleEffect.ParticleType.FLAME, 1, 1, 1)).sendToLocation(block.getLocation()));
    }
}
