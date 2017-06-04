package net.climaxmc.KitPvp.Kits;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kit;
import net.climaxmc.KitPvp.Utils.Ability;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import java.util.concurrent.TimeUnit;

public class BlazeKit extends Kit {

    private final int cooldown = 15, abilitySlot = 2;
    private ItemStack ability = new ItemStack(Material.BLAZE_POWDER);

    private Ability blaze = new Ability("Blaze", 1, 15, TimeUnit.SECONDS);

    public BlazeKit() {
        super("Blaze", new ItemStack(Material.BLAZE_POWDER), "Use your Blaze ability to set everyone within 5 Blocks on fire!", ChatColor.RED);
    }

    protected void wear(Player player) {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        player.getInventory().addItem(sword);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);

        addSoup(player.getInventory(), 2, 35);

        ItemMeta blazeMeta = ability.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(blazeMeta);
        player.getInventory().addItem(ability);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 1);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 1);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
    }

    protected void wearNoSoup(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        regenResistance(player);
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        sword.addEnchantment(Enchantment.DURABILITY, 3);
        player.getInventory().addItem(sword);
        if (!ClimaxPvp.getInstance().spawnSoupTrue.containsKey(player)) {             ClimaxPvp.getInstance().spawnSoupTrue.put(player, false);         }
        if (!ClimaxPvp.getInstance().spawnSoupTrue.get(player)) {
            ItemStack rod = new ItemStack(Material.FISHING_ROD);
            player.getInventory().addItem(rod);
        }
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);

        ItemMeta blazeMeta = ability.getItemMeta();
        blazeMeta.setDisplayName(ChatColor.AQUA + "Blaze \u00A7f» \u00A78[\u00A76" + cooldown + "\u00A78]");
        ability.setItemMeta(blazeMeta);
        player.getInventory().addItem(ability);

        ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
        helm.addEnchantment(Enchantment.DURABILITY, 2);
        helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta helmMeta = (LeatherArmorMeta) helm.getItemMeta();
        helmMeta.setColor(Color.YELLOW);
        helm.setItemMeta(helmMeta);
        player.getInventory().setHelmet(helm);
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.YELLOW);
        boots.setItemMeta(bootsMeta);
        player.getInventory().setBoots(boots);
    }

    /*@EventHandler
    public void onInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (KitManager.isPlayerInKit(player, this)) {
            if (player.getInventory().getItemInHand().getType() == Material.BLAZE_POWDER) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (!blaze.tryUse(player)) {
                        return;
                    }
                    player.sendMessage(ChatColor.GOLD + "You used the " + ChatColor.AQUA + "Blaze" + ChatColor.GOLD + " Ability!");
                    for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                        if (entity instanceof Player) {
                            Player players = (Player) entity;
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));
                            player.getWorld().playSound(player.getLocation(), Sound.BLAZE_BREATH, 2, 2);
                            if (!KitPvp.getVanished().contains(players.getUniqueId())
                                    && !CheckCommand.getChecking().contains(players.getUniqueId())
                                    && (KitPvp.currentTeams.get(player.getName()) != players.getName()
                                    && KitPvp.currentTeams.get(players.getName()) != player.getName())) {
                                players.setFireTicks(170);
                            }
                        }
                    }
                }
            }
        }
    }*/
}
