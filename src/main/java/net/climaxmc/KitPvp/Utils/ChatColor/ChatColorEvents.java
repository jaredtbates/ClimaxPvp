package net.climaxmc.KitPvp.Utils.ChatColor;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.DeathEffectsMenu;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class ChatColorEvents implements Listener {

    private ClimaxPvp plugin;

    public ChatColorEvents(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        final Player player = (Player) event.getWhoClicked();

        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (event.getSlotType() == null || event.getCurrentItem() == null || event.getCurrentItem().getType() == null) {
                return;
            }
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                if (inventory.getName().equals("\u00A78\u00A7lChat Color")) {
                    event.setCancelled(true);

                    ChatColorFiles chatColorFiles = new ChatColorFiles();
                    PlayerData playerData = plugin.getPlayerData(player);

                    if (playerData.hasRank(Rank.BETA)) {
                        if (!event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                            for (DChatColor color : DChatColor.values()) {
                                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(color.getColor() + color.getName())) {
                                    chatColorFiles.setCurrentColor(player, color);
                                    player.closeInventory();
                                }
                            }
                        }
                    } else {
                        player.sendMessage(ChatColor.WHITE + "\u00BB " + ChatColor.RED + "You must have a Rank to use chat colors! Purchase @ donate.climaxmc.net!");
                    }
                    if (event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove")) {
                            chatColorFiles.removeCurrentColor(player);
                            player.closeInventory();
                        }
                    }
                    if (event.getCurrentItem().getType().equals(Material.STAINED_CLAY)) {
                        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Toggle Italics")) {
                            //chatColorFiles.toggleItalics(player);
                            player.closeInventory();
                        }
                    }
                }
                if (inventory.getName().contains("\u00A78\u00A7lCosmetics")) {
                    if (event.getCurrentItem() != null) {
                        if (event.getCurrentItem().getType().equals(Material.REDSTONE)) {
                            ChatColorMenu chatColorMenu = new ChatColorMenu(ClimaxPvp.getInstance());
                            chatColorMenu.openInventory(player);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerData(player);

        ChatColorFiles chatColorFiles = new ChatColorFiles();

        if (chatColorFiles.getCurrentColor(player) != null) {
            for (DChatColor color : DChatColor.values()) {
                if (color.getName().equals(chatColorFiles.getCurrentColor(player))) {
                    ClimaxPvp.currentChatColor.put(player.getUniqueId(), color);
                    chatColorFiles.setCurrentColor(player, color);
                }
            }
        }
    }
}
