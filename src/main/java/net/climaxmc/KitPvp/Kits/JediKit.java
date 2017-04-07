package net.climaxmc.KitPvp.Kits;

import java.util.concurrent.TimeUnit;

import me.xericker.disguiseabilities.DisguiseAbilities;
import net.climaxmc.AntiNub.AntiNub;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;

import net.climaxmc.KitPvp.Utils.ChatUtils;
import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class JediKit extends Kit {

    private final int cooldown = 7;
    private ItemStack ability = new ItemStack(Material.BLAZE_ROD);

    private Ability push = new Ability(ChatUtils.color("&bPush &8[&7Shift&8]"), 1, cooldown, TimeUnit.SECONDS);

    public JediKit() {
        super("Jedi", new I(Material.BLAZE_ROD), "Sneak to push nearby players away!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        regenResistance(player);
        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        LeatherArmorMeta helmmeta = (LeatherArmorMeta) helm.getItemMeta();
        helmmeta.setColor(Color.GREEN);
        helm.setItemMeta(helmmeta);
        player.getInventory().setHelmet(helm);
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        chest.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) chest.getItemMeta();
        chestmeta.setColor(Color.GREEN);
        chest.setItemMeta(chestmeta);
        player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        player.getInventory().addItem(rod);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatUtils.color("&bPush &8[&7Shift&8] &f» &8[&6" + cooldown + "&8]"));
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (!push.tryUse(player)) {
                return;
            }
            player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Push" + ChatColor.GOLD + " Ability!");

            for (Entity nearby : player.getNearbyEntities(7, 7, 7)) {
                if (nearby.getType().equals(EntityType.PLAYER)) {
                    if (!ClimaxPvp.getInstance().isWithinProtectedRegion(nearby.getLocation()) && !ClimaxPvp.getInstance().isWithinProtectedRegion(player.getLocation())) {

                        knockback(player, (Player) nearby);
                        ((Player) nearby).playSound(nearby.getLocation(), Sound.ENDERDRAGON_HIT, 1F, 0.4F);

                        AntiNub.alertsEnabled.put(nearby.getUniqueId(), false);
                        ClimaxPvp.getInstance().getServer().getScheduler().runTaskLater(ClimaxPvp.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                AntiNub.alertsEnabled.put(nearby.getUniqueId(), true);
                            }
                        }, 20L * 3);
                    }
                }
            }
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_HIT, 1F, 0.4F);

            push.startCooldown(player, this, cooldown, ability);
        }
    }

    private void knockback(Player player, Player target)
    {
        Location l = target.getLocation().subtract(player.getLocation());
        double distance = target.getLocation().distance(player.getLocation());
        Vector v = l.toVector().multiply(3/distance).setY(0.15);
        target.setVelocity(v);
    }
}
