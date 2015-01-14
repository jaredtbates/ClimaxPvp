package net.climaxmc.KitPvp;

import java.util.ArrayList;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Kits.*;

public class KitManager {
	public static ArrayList<Kit> kits = new ArrayList<Kit>();
	
	public KitManager(ClimaxPvp plugin) {
		// Default Kits
		kits.add(new PvpKit());
		kits.add(new HeavyKit());
		kits.add(new ArcherKit());
		kits.add(new IronGolemKit());
		kits.add(new FishermanKit());
		kits.add(new NinjaKit());
		// Amateur Kits
		kits.add(new SoldierKit());
		kits.add(new EndermanKit());
		// Experienced Kits
		
		// Advanced Kits
		
		// Veteran Kits
		
		
		for (Kit kit : kits) {
			plugin.getServer().getPluginManager().registerEvents(kit, plugin);
			plugin.getCommand(kit.getName().replaceAll("\\s+", "")).setExecutor(kit);
		}
	}
}
