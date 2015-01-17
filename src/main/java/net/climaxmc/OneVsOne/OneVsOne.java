package net.climaxmc.OneVsOne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.OneVsOne.Commands.OneVsOneCommand;
import net.climaxmc.OneVsOne.Listeners.PlayerDeathListener;
import net.climaxmc.OneVsOne.Listeners.PlayerInteractEntityListener;

public class OneVsOne {
	private ClimaxPvp plugin;
	private OneVsOne instance;

	@Getter private HashMap<UUID, UUID> challenged = new HashMap<UUID, UUID>();
	
	public OneVsOne(ClimaxPvp plugin) {
		this.plugin = plugin;
		instance = this;
		plugin.getCommand("1v1").setExecutor(new OneVsOneCommand(plugin, instance));
		plugin.getServer().getPluginManager().registerEvents(new PlayerInteractEntityListener(plugin, this), plugin);
		plugin.getServer().getPluginManager().registerEvents(new PlayerDeathListener(plugin, this), plugin);
	}
}
