package net.climaxmc.KitPvp;

import lombok.Data;
import net.climaxmc.API.PlayerData;
import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;
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
    private ChatColor color = ChatColor.GRAY;

    /**
     * Defines a kit
     *
     * @param name Name of the kit
     * @param item Item representing the kit
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
     * @param name Name of the kit
     * @param item Item representing the kit
     * @param lore Lore of the kit
     * @param color Color of the kit
     */
    public Kit(String name, ItemStack item, String lore, ChatColor color) {
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = new ArrayList<String>();
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
        PlayerData playerData = ClimaxPvp.getInstance().getPlayerData(player);
        if (player.hasPermission("ClimaxPvp.Kit." + getName().replaceAll("\\s+", "")) || playerData.hasData("Admin Mode")) {
            if (!KitPvp.inKit.contains(player.getUniqueId())) {
                KitPvp.inKit.add(player.getUniqueId());
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
                player.getInventory().clear();
                wear(player);
                player.sendMessage(ChatColor.GOLD + "You have chosen " + getColor() + getName());
            } else {
                player.sendMessage(ChatColor.RED + "You have not died yet!");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission for kit " + getName() + ChatColor.RED + "!");
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
}
