package net.climaxmc.KitPvp;

import java.util.ArrayList;

import net.climaxmc.KitPvp.Kits.ArcherKit;
import net.climaxmc.KitPvp.Kits.FishermanKit;
import net.climaxmc.KitPvp.Kits.HeavyKit;
import net.climaxmc.KitPvp.Kits.IronGolemKit;
import net.climaxmc.KitPvp.Kits.PvpKit;
import net.climaxmc.KitPvp.Kits.SoldierKit;

public class KitManager {
	public static ArrayList<Kit> kits = new ArrayList<Kit>();
	
	public KitManager(Main plugin) {
		kits.add(new PvpKit());
		kits.add(new HeavyKit());
		kits.add(new ArcherKit());
		kits.add(new SoldierKit());
		kits.add(new IronGolemKit());
		kits.add(new FishermanKit());
		
		for (Kit kit : kits) {
			plugin.getServer().getPluginManager().registerEvents(kit, plugin);
			plugin.getCommand(kit.getName().replaceAll("\\s+", "")).setExecutor(kit);
		}
	}
}
