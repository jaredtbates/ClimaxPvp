package net.climaxmc.KitPvp;

import lombok.Data;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.CachedPlayerData;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

/**
 * Represents a kit
 *
 * @author computerwizjared
 */
@Data
public abstract class Kit implements Listener, CommandExecutor {
    /**
     * Name of the kit
     */
    private String name = "";
    /**
     * Item representing the kit
     */
    private ItemStack item = new ItemStack(Material.AIR);
    /**
     * Lore of the kit
     */
    private String lore = "";
    /**
     * Type of the kit
     */
    private ChatColor color = ChatColor.GRAY;

    /**
     * Defines a kit
     *
     * @param name  Name of the kit
     * @param item  Item representing the kit
     * @param color Color of the kit
     */
    public Kit(String name, ItemStack item, ChatColor color) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + "Kit " + name);
        item.setItemMeta(meta);
        this.name = name;
        this.item = item;
        this.color = color;
    }

    /**
     * Defines a kit
     *
     * @param name  Name of the kit
     * @param item  Item representing the kit
     * @param lore  Lore of the kit
     * @param color Color of the kit
     */
    public Kit(String name, ItemStack item, String lore, ChatColor color) {
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(lore);
        meta.setLore(lores);
        meta.setDisplayName(color + "Kit " + name);
        item.setItemMeta(meta);
        this.name = name;
        this.item = item;
        this.lore = lore;
        this.color = color;
    }

    /**
     * Gives a player specified amount of soups
     *
     * @param inventory Inventory to give soups
     * @param startslot Inventory slot to start giving soups
     * @param finalslot Inventory slot to end giving soups
     */
    public static void addSoup(Inventory inventory, int startslot, int finalslot) {
        for (int slot = startslot; slot <= finalslot; slot++) {
            inventory.setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
        }
    }

    /**
     * Wear the kit
     *
     * @param player Player to wear kit
     */
    protected abstract void wear(Player player);

    /**
     * Wear the no soup version of the kit
     *
     * @param player Player to wear kit
     */
    protected abstract void wearNoSoup(Player player);

    /**
     * Wear the kit with permissions and messages
     *
     * @param player Player to wear kit
     */
    public void wearCheckLevel(Player player) {
        if (!KitManager.isPlayerInKit(player)) {
            CachedPlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
            if (playerData.getLevelColor().contains(String.valueOf(color.getChar())) || playerData.getTemporaryPlayerData().containsKey("Admin Mode")) {
                KitManager.getPlayersInKits().put(player.getUniqueId(), this);

                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }

                player.sendMessage(ChatColor.GOLD + "You have chosen " + getColor() + getName());

                player.getInventory().clear();

                Location noSoupLocation = ClimaxPvp.getInstance().getWarpLocation("NoSoup");
                if (noSoupLocation != null) {
                    if (player.getLocation().distance(noSoupLocation) < 100) {
                        wearNoSoup(player);
                        return;
                    }
                }

                wear(player);
            } else {
                player.sendMessage(ChatColor.RED + "You must have level " + color + color.name() + ChatColor.RED + "!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You have not died yet!");
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase(getName().replaceAll("\\s+", ""))) {
                wearCheckLevel(player);
            }
        }
        return false;
    }
}
