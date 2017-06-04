 package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

 public class FishermanKit extends Kit {
    public FishermanKit() {
        super("Fisherman", new ItemStack(Material.FISHING_ROD), "Hook a player and retract your line to Fish them to you!", ChatColor.GRAY);
    }

    protected void wear(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "This kit is disabled, sorry! -- Do /spawn to choose another kit!");
        /*ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 2, 35);*/
    }

    protected void wearNoSoup(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "This kit is disabled, sorry! -- Do /spawn to choose another kit!");
        /*for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        player.getInventory().setBoots(boots);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);*/
    }

    /*@EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (event.getCaught() instanceof Player) {
                if (!KitPvp.getVanished().contains(event.getCaught().getUniqueId())
                        && !CheckCommand.getChecking().contains(event.getCaught().getUniqueId())
                        && (KitPvp.currentTeams.get(player.getName()) != event.getCaught().getName()
                        && KitPvp.currentTeams.get(event.getCaught().getName()) != player.getName())) {
                    event.getCaught().teleport(player.getLocation());
                }
            }
        }
    }*/
}