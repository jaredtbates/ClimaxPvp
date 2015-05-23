package net.climaxmc.KitPvp;

import lombok.Data;
import org.bukkit.Material;
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
    private KitType type = KitType.DEFAULT;

    /**
     * Defines a kit
     *
     * @param name Name of the kit
     * @param item Item representing the kit
     * @param type Type of the kit
     */
    public Kit(String name, ItemStack item, KitType type) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eKit " + name);
        item.setItemMeta(meta);
        this.name = name;
        this.item = item;
        this.type = type;
    }

    /**
     * Defines a kit
     *
     * @param name Name of the kit
     * @param item Item representing the kit
     * @param lore Lore of the kit
     * @param type Type of the kit
     */
    public Kit(String name, ItemStack item, String lore, KitType type) {
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<String>();
        lores.add(lore);
        meta.setLore(lores);
        meta.setDisplayName("§eKit " + name);
        item.setItemMeta(meta);
        this.name = name;
        this.item = item;
        this.lore = lore;
        this.type = type;
    }

    /**
     * Gives a player specified amount of soups
     *
     * @param inventory Inventory to give soups
     * @param startslot Inventory slot to start giving soups
     * @param finalslot Inventory slot to end giving soups
     */
    public static void addSoup(Inventory inventory, int startslot, int finalslot) {
        for (int x = startslot; x <= finalslot; x++) {
            inventory.setItem(x, new ItemStack(Material.MUSHROOM_SOUP));
        }
    }

    /**
     * Wear the kit
     *
     * @param player Player to wear kit
     */
    public abstract void wear(Player player);

    /**
     * Wear the kit with permissions and messages
     *
     * @param player Player to wear kit
     */
    public void wearCheckPerms(Player player) {
        if (player.hasPermission("ClimaxPvp.Kit." + getName().replaceAll("\\s+", ""))) {
            if (!KitPvp.inKit.contains(player.getUniqueId())) {
                KitPvp.inKit.add(player.getUniqueId());
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.getInventory().clear();
                wear(player);
                player.sendMessage("§6You have chosen §a" + getName());
            } else {
                player.sendMessage("§cYou have not died yet!");
            }
        } else {
            player.sendMessage("§cYou do not have permission for kit " + getName() + "§c!");
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase(getName())) {
                wearCheckPerms(player);
            }
        }
        return false;
    }

    /**
     * Available types of kits
     */
    public enum KitType {
        DEFAULT, AMATEUR, EXPERIENCED, ADVANCED, VETERAN
    }
}
