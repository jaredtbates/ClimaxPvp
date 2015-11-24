package net.climaxmc.KitPvp.Menus.PlayerProfile;// AUTHOR: gamer_000 (11/21/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Utils.I;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class CurrencyMenu {

        ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK, 1);
    {
        ItemMeta im = goldBlock.getItemMeta();
        im.setDisplayName(GOLD + "Gold Blocks: " + GRAY + "{amount}");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "1 of these = 64 " + GOLD + "Gold Ingots");
        lore.add(GRAY + "64 of these = 1 " + AQUA + "Diamond");
        im.setLore(lore);
        goldBlock.setItemMeta(im);
    }

        ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT, 1);
    {
        ItemMeta im = goldIngot.getItemMeta();
        im.setDisplayName(GOLD + "Gold Ingots: " + GRAY + "{amount}");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "64 of these = 1 " + GOLD + "Gold Block");
        lore.add(GRAY + "Can be obtained by " + RED + "killing players" + GRAY + ",");
        lore.add(RED + "Completing Challenges" + GRAY + ", and " + RED + "Opening Crates" + GRAY + "!");
        im.setLore(lore);
        goldIngot.setItemMeta(im);
    }

        ItemStack diamondBlock = new ItemStack(Material.DIAMOND_BLOCK, 1);
    {
        ItemMeta im = diamondBlock.getItemMeta();
        im.setDisplayName(AQUA + "Diamond Blocks: " + GRAY + "{amount}");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "1 of these = 64 " + AQUA + "Diamonds");
        lore.add(GRAY + "64 of these = 1 " + GREEN + "Emerald");
        im.setLore(lore);
        diamondBlock.setItemMeta(im);
    }

        ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
    {
        ItemMeta im = diamond.getItemMeta();
        im.setDisplayName(AQUA + "Diamonds: " + GRAY + "{amount}");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "1 of these = 64 " + GOLD + "Gold Blocks");
        lore.add(GRAY + "64 of these = 1 " + AQUA + "Diamond Blocks");
        im.setLore(lore);
        diamond.setItemMeta(im);
    }

        ItemStack emerald = new ItemStack(Material.EMERALD, 1);
    {
        ItemMeta im = emerald.getItemMeta();
        im.setDisplayName(GREEN + "Emeralds: " + GRAY + "{amount}");
        List<String> lore = new ArrayList<>();
        lore.add(GRAY + "1 of these = 64 " + AQUA + "Diamond Blocks");
        lore.add(GRAY + "Used to purchase " + LIGHT_PURPLE + "LEGENDARY " + GRAY + "cosmetics!");
        im.setLore(lore);
        emerald.setItemMeta(im);
    }

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    {
        ItemMeta im = sword.getItemMeta();
        im.setDisplayName(RED + "???");
        sword.setItemMeta(im);
    }

        ItemStack painting = new ItemStack(Material.PAINTING, 1);
    {
        ItemMeta im = painting.getItemMeta();
        im.setDisplayName(RED + "???");
        painting.setItemMeta(im);
    }

        ItemStack chest = new ItemStack(Material.CHEST, 1);
    {
        ItemMeta im = chest.getItemMeta();
        im.setDisplayName(RED + "???");
        chest.setItemMeta(im);
    }

        ItemStack barrier = new ItemStack(Material.BARRIER, 1);
    {
        ItemMeta im = barrier.getItemMeta();
        im.setDisplayName(RED + "Go Back");
        barrier.setItemMeta(im);
    }

        Inventory currencyMenu = Bukkit.createInventory(null, 45, "Your Bank");
    {

        currencyMenu.setItem(11, goldIngot);
        currencyMenu.setItem(12, goldBlock);
        currencyMenu.setItem(13, emerald);
        currencyMenu.setItem(14, diamondBlock);
        currencyMenu.setItem(15, diamond);
        currencyMenu.setItem(30, sword);
        currencyMenu.setItem(31, painting);
        currencyMenu.setItem(32, chest);
        currencyMenu.setItem(40, barrier);
    }

    public void open(Player player) {
        player.openInventory(currencyMenu);
    }
}
