package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class InventoryOpenListener implements Listener {
    private ClimaxPvp plugin;

    public InventoryOpenListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    Ability getSoup = new Ability("get le soup", 1, 18, TimeUnit.SECONDS);

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getHolder() instanceof Chest /*|| event.getInventory().getHolder() instanceof DoubleChest*/) {
            if (ClimaxPvp.getInstance().getWarpLocation("regen") != null) {
                if (player.getLocation().distance(ClimaxPvp.getInstance().getWarpLocation("regen")) <= 350) {
                    event.setCancelled(true);
                    if (!ClimaxPvp.getInstance().spawnSoupTrue.containsKey(player)) {
                        ClimaxPvp.getInstance().spawnSoupTrue.put(player, false);
                    }
                    if (ClimaxPvp.getInstance().spawnSoupTrue.get(player)) {
                        //player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
                        if (!getSoup.tryUse(player)) {
                            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Cooldown time remaining: " + ChatColor.YELLOW + getSoup.getStatus(player).getRemainingTime(TimeUnit.SECONDS));
                            return;
                        } else {
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
                            player.setHealth(20);
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 2));
                            player.removePotionEffect(PotionEffectType.REGENERATION);

                            player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.GRAY + "Your soup has been refilled!");
                        }
                    } else {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You are not in Soup mode!");
                    }
                } else {
                    event.setCancelled(true);
                    Kit.addSoup(KitPvp.soupInventory, 0, 53);
                    player.openInventory(KitPvp.soupInventory);
                }
            }
        }

        if (event.getInventory().getHolder() instanceof Dispenser) {
            event.setCancelled(true);
            Kit.addSoup(KitPvp.soupInventory, 0, 53);
            player.openInventory(KitPvp.soupInventory);
        }
    }
}
