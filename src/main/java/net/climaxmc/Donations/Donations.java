package net.climaxmc.Donations;

import lombok.Getter;
import net.climaxmc.API.ParticleEffect;
import net.climaxmc.ClimaxPvp;
import net.climaxmc.Donations.Commands.SpectateCommand;
import net.climaxmc.Donations.Commands.TrailsCommand;
import net.climaxmc.Donations.Listeners.InventoryClickListener;
import net.climaxmc.Donations.Listeners.PlayerMoveListener;

import java.util.HashMap;
import java.util.UUID;

public class Donations {
    @Getter
    private HashMap<UUID, ParticleEffect.ParticleData> particlesEnabled = new HashMap<UUID, ParticleEffect.ParticleData>();

    public Donations(ClimaxPvp plugin) {
        plugin.getCommand("trails").setExecutor(new TrailsCommand(plugin));
        plugin.getCommand("spectate").setExecutor(new SpectateCommand(plugin));
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveListener(plugin, this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryClickListener(plugin, this), plugin);
    }
}
