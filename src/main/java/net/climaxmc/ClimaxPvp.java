package net.climaxmc;

import lombok.Getter;
import net.climaxmc.Creative.Creative;
import net.climaxmc.Donations.Donations;
import net.climaxmc.KitPvp.KitPvp;
import net.climaxmc.KitPvp.Commands.RepairCommand;
import net.climaxmc.KitPvp.Commands.SpawnCommand;
import net.climaxmc.OneVsOne.OneVsOne;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class ClimaxPvp extends JavaPlugin {
	@Getter
	private static ClimaxPvp instance;
	@Getter
	private String prefix = "§0§l[§cClimax§0§l] §r";
	@Getter
	private Economy economy = null;

	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		setupEconomy();
		new KitPvp(this);
		new OneVsOne(this);
		new Donations(this);
		new Creative(this);
		getCommand("repair").setExecutor(new RepairCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
	}
	
	public void onDisable() {
		
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}

    public void sendToSpawn(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20L);
        player.setMaxHealth(20L);
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack kitSelector = new ItemStack(Material.NETHER_STAR);
        ItemMeta kitSelectorMeta = kitSelector.getItemMeta();
        kitSelectorMeta.setDisplayName("§a§lKit Selector");
        List<String> kitSelectorLores = new ArrayList<String>();
        kitSelectorLores.add("§5§o(Right Click) to select a kit!");
        kitSelectorMeta.setLore(kitSelectorLores);
        kitSelector.setItemMeta(kitSelectorMeta);
        player.getInventory().setItem(0, kitSelector);
    }
}
