package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class VikingKit extends Kit {

    public VikingKit() {
        super("Viking", new ItemStack(Material.DIAMOND_AXE), "You damage all players within 3.3 blocks of yourself!", ChatColor.RED);
    }

    protected void wear(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "This kit is disabled, sorry! -- Do /spawn to choose another kit!");
        /*for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta axemeta = axe.getItemMeta();
        axemeta.setDisplayName(ChatColor.GOLD + "Viking Axe");
        axe.setItemMeta(axemeta);
        player.getInventory().addItem(axe);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.MAROON);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        addSoup(player.getInventory(), 1, 35);*/
    }

    protected void wearNoSoup(Player player) {
        player.sendMessage(ChatColor.DARK_RED + "This kit is disabled, sorry! -- Do /spawn to choose another kit!");
        /*for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta axemeta = axe.getItemMeta();
        axemeta.setDisplayName(ChatColor.GOLD + "Viking Axe");
        axe.setItemMeta(axemeta);
        player.getInventory().addItem(axe);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.MAROON);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 3);
        LeatherArmorMeta bootsmeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsmeta.setColor(Color.MAROON);
        boots.setItemMeta(bootsmeta);
        player.getInventory().setBoots(boots);
        ItemStack fishingRod = new ItemStack(Material.FISHING_ROD);
        fishingRod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(fishingRod);
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        rod.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(rod);*/
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (KitManager.isPlayerInKit(player, this)) {
                if (player.getInventory().getItemInHand().getType() == Material.DIAMOND_AXE) {
                    player.getNearbyEntities(3.3, 3.3, 3.3).stream().filter(entity -> entity instanceof Player).filter(entity -> !(entity == event.getDamager())).forEach(entity -> {
                        Player players = (Player) entity;
                        event.setCancelled(true);
                        if (!VanishCommand.getVanished().contains(players.getUniqueId())
                                && !CheckCommand.getChecking().contains(players.getUniqueId())
                                && (KitPvp.currentTeams.get(player.getName()) != players.getName()
                                && KitPvp.currentTeams.get(players.getName()) != player.getName())) {
                            players.damage(4);
                            Vector vector = player.getEyeLocation().getDirection();
                            vector.multiply(0.6F);
                            vector.setY(0.2);
                            players.setVelocity(vector);
                        }
                    });
                }
            }
        }
    }
}
