package net.climaxmc.common.donations.trails;

import lombok.Getter;
import org.bukkit.Material;

public enum Trail {
    CLOUDS("Clouds", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.1, 2, 0.5), Material.COAL, 1000),
    FLAME("Flame", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 0, 0, 0), Material.LAVA_BUCKET, 500),
    RAIN("Rain", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 0, 6, 0.2), Material.WATER_BUCKET, 0.5, 1500),
    MYSTIC("Mystic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0.2, 10, 0.1), Material.ENCHANTMENT_TABLE, 0.5, 1500),
    ENDER("Ender", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0.2, 20, 0.1), Material.ENDER_PEARL, 1500),
    HYPNOTIC("Hypnotic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SPELL_MOB, 20, 5, 0.2), Material.QUARTZ, 700),
    LOVE("Love", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.HEART, 0, 1, 0.2), Material.GOLDEN_CARROT, 500),
    NOTES("Notes", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.NOTE, 20, 2, 1), Material.NOTE_BLOCK, 1000),
    SLIME("Slime", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SLIME, 0, 5, 0.3), Material.SLIME_BALL, 300);

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
