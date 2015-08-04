package net.climaxmc.KitPvp.Listeners;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class PlayerRespawnListener implements Listener {
    private ClimaxPvp plugin;

    public PlayerRespawnListener(ClimaxPvp plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if (event.getRegainReason() == RegainReason.SATIATED)
            event.setCancelled(true);
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        event.setRespawnLocation(event.getPlayer().getWorld().getSpawnLocation());

        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20F);
        player.setMaxHealth(20F);
        player.setFoodLevel(17);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setFlySpeed(0.1F);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        kitSelectorMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit Selector");
        List<String> kitSelectorLores = new ArrayList<>();
        kitSelectorLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Select from a variety");
        kitSelectorLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "of different kits!");
        kitSelectorMeta.setLore(kitSelectorLores);
        kitSelector.setItemMeta(kitSelectorMeta);
        player.getInventory().setItem(0, kitSelector);

        if (KitManager.getPreviousKit().containsKey(player.getUniqueId())) {
            Kit kit = KitManager.getPreviousKit().get(player.getUniqueId());
            ItemStack previousKit = new ItemStack(Material.REDSTONE);
            ItemMeta previousKitMeta = previousKit.getItemMeta();
            previousKitMeta.setDisplayName(ChatColor.GREEN + "Previous Kit: " + kit.getColor() + kit.getName());
            List<String> previousKitLores = new ArrayList<>();
            previousKitLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Equip your previous kit.");
            previousKitMeta.setLore(previousKitLores);
            previousKit.setItemMeta(previousKitMeta);
            player.getInventory().setItem(1, previousKit);
        }

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Help/Rules");
        bookMeta.setTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Help/Rules");
        bookMeta.setAuthor(ChatColor.GOLD + "" + ChatColor.BOLD + "Climax" + ChatColor.RED + "" + ChatColor.BOLD + "MC");

        int fullPageNumber = plugin.getBook().length() / 256;
        List<String> pages = new ArrayList<>();
        for (int i = 0; i < fullPageNumber; i++) {
            pages.add(plugin.getBook().substring(256 * i, 255 * (i + 1)));
        }
        pages.add(plugin.getBook().substring(fullPageNumber * 256, plugin.getBook().length() - 1));
        bookMeta.setPages(pages);
        book.setItemMeta(bookMeta);
        player.getInventory().setItem(7, book);

        ItemStack particles = new ItemStack(Material.SEEDS);
        ItemMeta particlesMeta = particles.getItemMeta();
        particlesMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Trail Selector");
        List<String> particlesLores = new ArrayList<>();
        particlesLores.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Select a cool looking trail!");
        particlesMeta.setLore(particlesLores);
        particles.setItemMeta(particlesMeta);
        player.getInventory().setItem(8, particles);

        ItemStack challenges = new ItemStack(Material.DIAMOND);
        ItemMeta challengesItemMeta = challenges.getItemMeta();
        challengesItemMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Challenges");
        List<String> challengesLore = new ArrayList<>();
        challengesLore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "View your challenges,");
        challengesLore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "start new ones, and receive rewards!");
        challengesItemMeta.setLore(challengesLore);
        challenges.setItemMeta(challengesItemMeta);
        player.getInventory().setItem(4, challenges);
    }
}
