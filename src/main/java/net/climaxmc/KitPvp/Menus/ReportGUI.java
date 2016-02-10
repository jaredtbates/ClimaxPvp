package net.climaxmc.KitPvp.Menus;
/* Created by GamerBah on 1/24/2016 */


import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Commands.ReportCommand;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.common.database.Rank;
import net.gpedro.integrations.slack.SlackMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ReportGUI implements Listener {

    private ClimaxPvp plugin;

    public ReportGUI(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public void openInventory(Player player, Player target) {
        Inventory inv = plugin.getServer().createInventory(null, 45, "Climax Reports: " + target.getName());
        inv.setItem(10, new I(Material.BOOK).name(ChatColor.RED + "Kill Aura")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "If a player can hit you when they aren't")
                .lore(ChatColor.GRAY + "looking at you, select this.").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.YELLOW + "■■ □ □"));
        inv.setItem(11, new I(Material.BOOK).name(ChatColor.RED + "Forcefield")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "A player will automatically attack you if you")
                .lore(ChatColor.GRAY + "are in range. Is very similar to Kill Aura.").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.GREEN + "■ □ □ □"));
        inv.setItem(12, new I(Material.BOOK).name(ChatColor.RED + "Speed Hacks")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "If a player seems to be running")
                .lore(ChatColor.GRAY + "faster than they should, select this.").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.GOLD + "■■■ □"));
        inv.setItem(13, new I(Material.BOOK).name(ChatColor.RED + "Auto Click")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "Players with an Auto Click will continue")
                .lore(ChatColor.GRAY + "to swing their sword even after they get a kill.").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.GOLD + "■■■ □"));
        inv.setItem(14, new I(Material.BOOK).name(ChatColor.RED + "Fly Hacks")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "If a player is flying around like they")
                .lore(ChatColor.GRAY + "are in creative mode, select this.").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.GREEN + "■ □ □ □"));
        inv.setItem(15, new I(Material.BOOK).name(ChatColor.RED + "Auto Critical")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "Very hard to detect! Players with an")
                .lore(ChatColor.GRAY + "Auto Crit can be seen jumping ever")
                .lore(ChatColor.GRAY + "so slightly when they swing!").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.RED + "■■■■"));
        inv.setItem(16, new I(Material.BOOK).name(ChatColor.RED + "Anti-Knockback")
                .lore(ChatColor.AQUA + "Click to add to your report!").lore(" ")
                .lore(ChatColor.GRAY + "This one is self-explanatory.").lore(" ")
                .lore(ChatColor.RED + "NOTE: Lag can sometimes cause issues with")
                .lore(ChatColor.RED + "knockback. Be sure to check the player's ping!").lore(" ")
                .lore(ChatColor.WHITE + "Difficulty to Detect: " + ChatColor.YELLOW + "■■ □ □"));
        inv.setItem(32, new I(Material.WOOL).name(ChatColor.RED + "" + ChatColor.BOLD + "CANCEL REPORT")
                .durability(14));

        setWool(inv, target, ReportCommand.reportBuilders.get(player.getUniqueId()));

        player.openInventory(inv);

    }

    public ItemStack setSelected(ItemStack itemStack) {
        String name = itemStack.getItemMeta().getDisplayName()
                .replace(ChatColor.RED + "", ChatColor.GREEN + "");
        List<String> lore = itemStack.getItemMeta().getLore();
        int size = lore.size();
        lore.add(size++, " ");
        lore.add(size++, ChatColor.GREEN + "" + ChatColor.BOLD + "SELECTED");
        lore.set(0, ChatColor.AQUA + "Click to remove from your report!");
        ItemStack newItem = new I(Material.ENCHANTED_BOOK).name(name);
        ItemMeta im = newItem.getItemMeta();
        im.setLore(lore);
        newItem.setItemMeta(im);

        return newItem;
    }

    public ItemStack setUnSelected(ItemStack itemStack) {
        String name = itemStack.getItemMeta().getDisplayName()
                .replace(ChatColor.GREEN + "", ChatColor.RED + "");
        List<String> lore = itemStack.getItemMeta().getLore();
        int size = lore.size();
        lore.remove(size - 1);
        lore.remove(size - 2);
        lore.set(0, ChatColor.AQUA + "Click to add to your report!");
        ItemStack newItem = new I(Material.BOOK).name(name);
        ItemMeta im = newItem.getItemMeta();
        im.setLore(lore);
        newItem.setItemMeta(im);

        return newItem;
    }

    public void setWool(Inventory inventory, Player target, String message) {
        ItemStack wool = new I(Material.WOOL)
                .name(ChatColor.GREEN + "" + ChatColor.BOLD + "ACCEPT & SEND").durability(5);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Reporting: " + ChatColor.YELLOW + target.getName());
        if (message != null) {
            lore.add(ChatColor.GRAY + "For: " + ChatColor.GOLD + message);
        } else {
            lore.add(ChatColor.GRAY + "For: " + ChatColor.RED + "Nothing here yet! Click the books");
            lore.add(ChatColor.RED + "        to add reasons to your report!");
        }
        lore.add(" ");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "NOTICE:");
        lore.add(ChatColor.RED + "Please do not falsely report a player");
        lore.add(ChatColor.RED + "with the intention of getting them banned.");
        lore.add(ChatColor.RED + "ClimaxMC Staff do not take this lightly,");
        lore.add(ChatColor.RED + "and will deal with the situation if needed.");
        ItemMeta im = wool.getItemMeta();
        im.setLore(lore);
        wool.setItemMeta(im);

        inventory.setItem(30, wool);
    }

    public void report(Player player, Player target, String message) {
        player.sendMessage(ChatColor.GREEN + " You have successfully reported "
                + ChatColor.DARK_AQUA + target.getName() + ChatColor.GREEN + "!");

        plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff)
                .hasRank(Rank.HELPER)).forEach(staff -> staff.sendMessage(" " + ChatColor.RED + player.getName()
                + " has reported " + ChatColor.BOLD + target.getName() + ChatColor.RED + " for: " + message + "!"));

        plugin.getServer().getOnlinePlayers().stream().filter(staff -> plugin.getPlayerData(staff)
                .hasRank(Rank.HELPER)).forEach(staff -> staff.playSound(staff.getLocation(), Sound.NOTE_PIANO, 2, 2));

        plugin.getSlack().call(new SlackMessage("Climax Reports", ">>>*" + player.getName() + "* _has reported_ *" + target.getName() + "* _for:_ " + message));

        player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

        ReportCommand.cooldown.put(player.getUniqueId(), 60);
        ReportCommand.reportBuilders.remove(player.getUniqueId());
        ReportCommand.reportArray.remove(player.getUniqueId());

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (ReportCommand.cooldown.get(player.getUniqueId()) >= 0) {
                    ReportCommand.cooldown.replace(player.getUniqueId(), ReportCommand.cooldown.get(player.getUniqueId()) - 1);
                }

                if (ReportCommand.cooldown.get(player.getUniqueId()) == 0) {
                    ReportCommand.cooldown.remove(player.getUniqueId());
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1.5F);
                    player.sendMessage(ChatColor.GREEN + " You are now able to report another player!");
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin, 1L, 20L).getTaskId();
    }

}
