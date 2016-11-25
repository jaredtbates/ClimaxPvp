package net.climaxmc.KitPvp.Kits;

import net.climaxmc.Administration.Commands.CheckCommand;
import net.climaxmc.Administration.Commands.VanishCommand;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Utils.Ability;
import net.climaxmc.KitPvp.Utils.Settings.SettingsFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class IronGolemKit extends Kit {

    private final int cooldown = 4;
    private ItemStack ability = new ItemStack(Material.RED_ROSE);

    private Ability Throw = new Ability("Throw", 1, cooldown, TimeUnit.SECONDS);

    public IronGolemKit() {
        super("Iron Golem", new ItemStack(Material.RED_ROSE), "Punch people with your Rose to launch them in the air!", ChatColor.GOLD);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().addItem(sword);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setBoots(boots);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Throw §f» §8[§6" + cooldown + "§8]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);

        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));
        regenResistance(player);
        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        sword.addEnchantment(Enchantment.DURABILITY, 2);
        player.getInventory().addItem(sword);
        SettingsFiles settingsFiles = new SettingsFiles();
        if (!settingsFiles.getSpawnSoupValue(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            rod.addEnchantment(Enchantment.DURABILITY, 3);
            player.getInventory().addItem(rod);
        }
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setLeggings(leggings);
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        player.getInventory().setBoots(boots);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Throw §f» §8[§6" + cooldown + "§8]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player target = (Player) event.getEntity();
                if (KitManager.isPlayerInKit(player, this)) {
                    if (player.getItemInHand().getType().equals(Material.RED_ROSE)) {
                        event.setCancelled(true);
                        if (!Throw.tryUse(player)) {
                            return;
                        }
                        if (!KitPvp.getVanished().contains(target.getUniqueId())
                                && !KitPvp.getChecking().contains(target.getUniqueId())
                                && (KitPvp.currentTeams.get(player.getName()) != target.getName()
                                && KitPvp.currentTeams.get(target.getName()) != player.getName())) {
                            target.setVelocity(new Vector(0, 1.3, 0));

                            Throw.startCooldown(player, this, cooldown, ability);
                        }
                    }
                }
            }
        }
    }
}
