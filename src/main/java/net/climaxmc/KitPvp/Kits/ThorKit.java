package net.climaxmc.KitPvp.Kits;

import net.climaxmc.KitPvp.Utils.I;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.KitManager;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

public class ThorKit extends Kit {

    private final int cooldown = 7;
    private ItemStack ability = new ItemStack(Material.GOLD_AXE);

    private Ability lightning = new Ability("Lightning Strike", 1, cooldown, TimeUnit.SECONDS);

    public ThorKit() {
        super("Thor", new ItemStack(Material.IRON_AXE), "Use your Axe to Strike Lightning!", ChatColor.GREEN);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Lightning Strike \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setHelmet(helm);
        ItemStack chestplate = new I(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        addSoup(player.getInventory(), 2, 35);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        player.getInventory().addItem(sword);
        if (!ClimaxPvp.getInstance().spawnSoupTrue.containsKey(player)) {
            ClimaxPvp.getInstance().spawnSoupTrue.put(player, false);
        }
        if (!ClimaxPvp.getInstance().spawnSoupTrue.get(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            player.getInventory().addItem(rod);
        }

        ItemMeta abilitymeta = ability.getItemMeta();
        abilitymeta.setDisplayName(ChatColor.AQUA + "Lightning Strike \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(abilitymeta);
        player.getInventory().addItem(ability);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().setHelmet(helm);
        ItemStack chestplate = new I(Material.DIAMOND_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER) || !event.getEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.GOLD_AXE) {
                if (!lightning.tryUse(player)) {
                    return;
                }
                player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Lightning Strike" + ChatColor.GOLD + " Ability!");

                if (ClimaxPvp.getInstance().isWithinProtectedRegion(target.getLocation())) {
                    return;
                }

                target.getWorld().strikeLightning(target.getLocation());

                lightning.startCooldown(player, this, cooldown, ability);
            }
        }
        if (event.getDamager().getType().equals(EntityType.LIGHTNING)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLightningDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof LightningStrike){
                Player player = (Player) event.getEntity();
                player.damage(4);
            }
        }
    }
}
