package net.climaxmc.Donations.Enums;

import lombok.Getter;
import net.climaxmc.API.ParticleEffect;
import net.climaxmc.Donations.Perk;
import org.bukkit.Material;

public enum Trail implements Perk {
    CLOUDS("Clouds", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.1, 2, 0.5), Material.COAL),
    FLAME("Flame", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 0, 0, 0), Material.LAVA_BUCKET),
    RAIN("Rain", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 0, 6, 0.2), Material.WATER_BUCKET, 0.5),
    MYSTIC("Mystic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0.2, 10, 0.1), Material.ENCHANTMENT_TABLE, 0.5),
    ENDER("Ender", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0.2, 20, 0.1), Material.ENDER_PEARL),
    HYPNOTIC("Hypnotic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SPELL_MOB, 20, 5, 0.2), Material.QUARTZ),
    LOVE("Love", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.HEART, 0, 1, 0.2), Material.GOLDEN_CARROT),
    NOTES("Notes", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.NOTE, 20, 2, 1), Material.NOTE_BLOCK),
    SLIME("Slime", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SLIME, 0, 5, 0.3), Material.SLIME_BALL);

    @Getter
    private String name;
    @Getter
    private ParticleEffect.ParticleData data;
    @Getter
    private Material material;
    @Getter
    private double yOffset;

    Trail(String name, ParticleEffect.ParticleData data, Material material) {
        this(name, data, material, 0);
    }

    Trail(String name, ParticleEffect.ParticleData data, Material material, double yOffset) {
        this.name = name;
        this.data = data;
        this.material = material;
        this.yOffset = yOffset;
    }

    @Override
    public String getDBName() {
        return "Trail_" + this.toString();
    }
}
