package net.climaxmc.KitPvp.KitPvp;

import java.util.ArrayList;

import net.climaxmc.KitPvp.Main;
import net.climaxmc.KitPvp.KitPvp.Kits.*;

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
		}
	}
}
