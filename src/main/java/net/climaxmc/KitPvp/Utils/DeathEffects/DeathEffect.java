package net.climaxmc.KitPvp.Utils.DeathEffects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.climaxmc.common.donations.trails.ParticleEffect;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum DeathEffect {
    FIRE("Fire", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.LAVA, 5, 2, 1.2), Material.BLAZE_POWDER, 1000),
    SNOW("Snow", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.FIREWORKS_SPARK, 0.1, 5, 1.1), Material.SNOW_BALL, 500),
    RAIN("Rain", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.DRIP_WATER, 3, 5, 0.7), Material.POTION, 700),
    MYSTIC("Mystic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.ENCHANTMENT_TABLE, 0.2, 6, 0.7), Material.ENCHANTMENT_TABLE, 1000),
    ENDER("Ender", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.PORTAL, 0.1, 9, 0.7), Material.ENDER_PEARL, 1000),
    HYPNOTIC("Hypnotic", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.SPELL_MOB, 30, 3, 1), Material.GHAST_TEAR, 700),
    LOVE("Love", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.HEART, 10, 1, 0.7), Material.GOLDEN_CARROT, 400),
    NOTES("Notes", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.NOTE, 10, 3, 1), Material.NOTE_BLOCK, 500),
    EXPLOSION("Explosion", new ParticleEffect.ParticleData(ParticleEffect.ParticleType.EXPLOSION_NORMAL, 0.3, 2, 0.4), Material.COAL, 700);

    private String name;
    private ParticleEffect.ParticleData data;
    private Material material;
    private double cost;
}