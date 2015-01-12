package net.climaxmc.OneVsOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.Commands.ArenaCommands;
import net.climaxmc.OneVsOne.Commands.OneVsOneCommand;

public class OneVsOne {
	ClimaxPvp plugin;
	
	public static ArrayList<UUID> in1v1 = new ArrayList<UUID>();
	public static HashMap<UUID, UUID> challenged = new HashMap<UUID, UUID>();
	
	public OneVsOne(ClimaxPvp plugin) {
		this.plugin = plugin;
		plugin.getCommand("1v1").setExecutor(new OneVsOneCommand(plugin));
		plugin.getCommand("setlobby").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena1spawn1").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena1spawn2").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena2spawn1").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena2spawn2").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena3spawn1").setExecutor(new ArenaCommands(plugin));
		plugin.getCommand("setarena3spawn2").setExecutor(new ArenaCommands(plugin));
	}
}
