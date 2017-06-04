package net.climaxmc.KitPvp.events;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventKitSelectorInv implements Listener {

    private ClimaxPvp plugin;
    public EventKitSelectorInv(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 27, ChatUtils.color("&7Event Kit Selector"));

        EventManager eventManager = plugin.eventManager;

        for (Kit kit : KitManager.getKits()) {
            inv.addItem(kit.getItem());
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().getType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null && event.getClickedInventory().getName() == null
                && event.getCurrentItem() == null && event.getCurrentItem().getItemMeta() == null) {
            return;
        }

        if (event.getClickedInventory().getName().equals(ChatUtils.color("&7Event Kit Selector"))) {
            event.setCancelled(true);
            player.closeInventory();
            ItemStack item = event.getCurrentItem();
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
            EventManager eventManager = plugin.eventManager;
            eventManager.startEvent(player.getUniqueId(), EventManager.eventSelectionType.get(player.getUniqueId()), KitManager.getKit(name));
        }
    }
}