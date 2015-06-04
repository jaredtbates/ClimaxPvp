package net.climaxmc.Donations.Enums;

import lombok.Getter;
import net.climaxmc.API.ParticleEffect;
import net.climaxmc.Donations.Perk;
import org.bukkit.Material;

public enum Trail implements Perk {
    CLOUDS("Clouds", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.1, 2, 0.5), Material.COAL),
    FLAME("Flame", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 0, 0, 0), Material.LAVA_BUCKET),
    RAIN("Rain", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 0, 6, 0.2), Material.WATER_BUCKET),
    MYSTIC("Mystic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0.2, 10, 0.1), Material.ENCHANTMENT_TABLE),
    PURPLE_SNAKE("Purple Snake", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0.2, 20, 0.1), Material.ENDER_PEARL),
    HYPNOTIC("Hypnotic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SPELL, 1, 5, 0.2), Material.QUARTZ),
    LOVE("Love", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.HEART, 0, 1, 0.2), Material.GOLDEN_CARROT);

    @Getter
    private String name;
    @Getter
    private ParticleEffect.ParticleData data;
    @Getter
    private Material material;

    Trail(String name, ParticleEffect.ParticleData data, Material item) {
        this.name = name;
        this.data = data;
        this.material = item;
    }

    @Override
    public String getDBName() {
        return "Trail_" + this.toString();
    }
}
