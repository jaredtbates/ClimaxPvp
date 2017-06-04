package net.climaxmc.KitPvp.events;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EventSelectorInv implements Listener {

    private ClimaxPvp plugin;
    public EventSelectorInv(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inv = plugin.getServer().createInventory(null, 27, ChatUtils.color("&7Event Selection"));

        EventManager eventManager = plugin.eventManager;

        int startSlot = 10;
        for (EventType eventType : EventType.values()) {
            inv.setItem(startSlot++, eventType.getItem());
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

        if (event.getClickedInventory().getName().equals(ChatUtils.color("&7Event Selection"))) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
            EventType type = EventType.TOURNAMENT;
            for (EventType eventType : EventType.values()) {
                if (eventType.getName().equals(name)) {
                    type = eventType;
                }
            }
            EventManager.eventSelectionType.put(player.getUniqueId(), type);
            new EventKitSelectorInv(plugin).openInventory(player);
        }
    }
}