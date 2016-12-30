package net.climaxmc.KitPvp.Menus;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffect;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.DeathEffects.DeathEffectFiles;
import net.climaxmc.common.donations.trails.Trail;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DeathEffectsMenu implements Listener {

    private ClimaxPvp plugin;

    public DeathEffectsMenu(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "\u00A78\u00A7lDeath Effects");

        for (int slot = 0; slot <= 9; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        for (int slot = 18; slot <= 25; slot++) {
            inventory.setItem(slot, new I(Material.STAINED_GLASS_PANE).durability(15).name(" "));
        }

        inventory.setItem(26, new I(Material.STAINED_GLASS_PANE).durability(14).name(ChatColor.RED + "Remove effect"));

        for (DeathEffect effect : DeathEffect.values()) {
            addEffect(inventory, effect, player, (int) effect.getCost());
        }

        player.openInventory(inventory);
    }

    DeathEffectFiles deathEffectFiles = new DeathEffectFiles();

    int startSlot = 9;

    private void addEffect(Inventory inventory, DeathEffect effect, Player player, int cost) {
        ItemStack particle = new ItemStack(effect.getMaterial());
        ItemMeta particleMeta = particle.getItemMeta();
        if (!deathEffectFiles.isEffectUnlocked(player, effect)) {
            if (effect.getName().equals("Explosion")) {
                particleMeta.setDisplayName(ChatColor.RED + effect.getName());
                ArrayList<String> lores = new ArrayList<>();
                lores.add("\u00A7cNot unlocked!");
                lores.add("\u00A76Exclusive! Purchase Beta to unlock!");
                particleMeta.setLore(lores);
            } else {
                particleMeta.setDisplayName(ChatColor.RED + effect.getName());
                ArrayList<String> lores = new ArrayList<>();
                lores.add("\u00A7cNot unlocked!");
                lores.add("\u00A77Shift-Click to unlock: \u00A7a$" + cost);
                particleMeta.setLore(lores);
            }
        } else {
            if (effect.getName().equals("Explosion")) {
                particleMeta.setDisplayName(ChatColor.GREEN + effect.getName());
                ArrayList<String> lores = new ArrayList<>();
                lores.add("\u00A7aUnlocked!");
                lores.add("\u00A76Exclusive! Welcome Beta member!");
                particleMeta.setLore(lores);
            } else {
                particleMeta.setDisplayName(ChatColor.GREEN + effect.getName());
                ArrayList<String> lores = new ArrayList<>();
                lores.add("\u00A7aUnlocked!");
                lores.add("\u00A77Original Cost: $" + cost);
                particleMeta.setLore(lores);
            }
        }
        particle.setItemMeta(particleMeta);
        inventory.setItem(startSlot++, particle);
    }
}
