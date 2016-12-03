package net.climaxmc.common.donations.trails;

import lombok.Getter;
import org.bukkit.Material;

public enum Trail {
    CLOUDS("Clouds", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.0, 1, 0.4), Material.COAL, 1000),
    FLAME("Flame", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 5, 1, 1.8), Material.LAVA_BUCKET, 500),
    RAIN("Rain", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 0, 1, 0.4), Material.WATER_BUCKET, 1.5, 1500),
    MYSTIC("Mystic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0.2, 4, 0.7), Material.ENCHANTMENT_TABLE, 0.5, 1500),
    ENDER("Ender", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0.1, 10, 0.4), Material.ENDER_PEARL, -0.5, 1500),
    HYPNOTIC("Hypnotic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SPELL_MOB, 30, 2, 0.9), Material.QUARTZ, 0.5, 700),
    LOVE("Love", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.HEART, 10, 1, 0.4), Material.GOLDEN_CARROT, 0.4, 400),
    NOTES("Notes", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.NOTE, 10, 2, 1), Material.NOTE_BLOCK, 1000),
    SLIME("Snow", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.1, 0, 1.8), Material.SNOW_BALL, 1.1, 500);

    @Getter
    private String name;
    @Getter
    private ParticleEffect.ParticleData data;
    @Getter
    private Material material;
    @Getter
    private double yOffset;
    @Getter
    private double cost;

    Trail(String name, ParticleEffect.ParticleData data, Material material, double cost) {
        this(name, data, material, 0, cost);
    }

    Trail(String name, ParticleEffect.ParticleData data, Material material, double yOffset, double cost) {
        this.name = name;
        this.data = data;
        this.material = material;
        this.yOffset = yOffset;
        this.cost = cost;
    }
}
