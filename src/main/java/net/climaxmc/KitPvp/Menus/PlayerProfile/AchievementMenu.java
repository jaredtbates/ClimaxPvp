package net.climaxmc.KitPvp.Menus.PlayerProfile;// AUTHOR: gamer_000 (12/23/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.Achievement;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AchievementMenu implements Listener {
    private ClimaxPvp plugin;

    public AchievementMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        PlayerData playerData = plugin.getPlayerData(player);
        if (event.getInventory().getName().contains("Profile Menu")) {
            if (item.getType().equals(Material.IRON_INGOT)) {
                Inventory achievementMenu = Bukkit.createInventory(null, 54, "Achievements");
                {
                    int i = 10;
                    for(Achievement achievement : Achievement.values()) {
                        if (playerData.getAchievements() == null) {
                            I a = new I(Material.IRON_INGOT);
                            a.name(achievement.getName());
                            a.lore(achievement.getDescription());

                            achievementMenu.setItem(i, a);
                            if (i == 8 || i == 16 || i == 25 || i == 34 || i == 43 || i == 52) {
                                i = i + 3;
                            } else {
                                i++;
                            }
                        } else {
                            if (playerData.getAchievements().contains(achievement.toString())) {
                                I a = new I(Material.GOLD_INGOT);
                                a.name(achievement.getName());
                                a.lore(achievement.getDescription());

                                achievementMenu.setItem(i, a);
                                if (i == 8 || i == 16 || i == 25 || i == 34 || i == 43 || i == 52) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }
                            } else {
                                I a = new I(Material.IRON_INGOT);
                                a.name(achievement.getName());
                                a.lore(achievement.getDescription());

                                achievementMenu.setItem(i, a);
                                if (i == 8 || i == 16 || i == 25 || i == 34 || i == 43 || i == 52) {
                                    i = i + 3;
                                } else {
                                    i++;
                                }
                            }
                        }
                    }
                    player.openInventory(achievementMenu);
                }
            }
        }
    }

}
