package pl.customworld.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.random.RandomGenerator;

/**
 * Wartości suwaków (0.0–1.0 lub znormalizowany zakres). Generator i modyfikatory placementów
 * mapują je na konkretne parametry noise / count (clamp + skale projektowe).
 */
public record TerrainSettings(
        float oceanFrequency,
        float mountainFrequency,
        float terrainHeight,
        float noiseScale,
        float caveSize,
        float caveFrequency,
        float structureDensity,
        float biomeSize,
        float seaLevel,
        float terrainRoughness,
        float vegetationDensity
) {
    public static final Codec<TerrainSettings> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("ocean").forGetter(TerrainSettings::oceanFrequency),
            Codec.FLOAT.fieldOf("mountain").forGetter(TerrainSettings::mountainFrequency),
            Codec.FLOAT.fieldOf("height").forGetter(TerrainSettings::terrainHeight),
            Codec.FLOAT.fieldOf("noise_scale").forGetter(TerrainSettings::noiseScale),
            Codec.FLOAT.fieldOf("cave_size").forGetter(TerrainSettings::caveSize),
            Codec.FLOAT.fieldOf("cave_freq").forGetter(TerrainSettings::caveFrequency),
            Codec.FLOAT.fieldOf("structures").forGetter(TerrainSettings::structureDensity),
            Codec.FLOAT.fieldOf("biome_size").forGetter(TerrainSettings::biomeSize),
            Codec.FLOAT.fieldOf("sea_level").forGetter(TerrainSettings::seaLevel),
            Codec.FLOAT.fieldOf("roughness").forGetter(TerrainSettings::terrainRoughness),
            Codec.FLOAT.fieldOf("vegetation").forGetter(TerrainSettings::vegetationDensity)
    ).apply(instance, TerrainSettings::new));

    public static TerrainSettings defaults() {
        return new TerrainSettings(
                0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f, 0.5f, 0.5f
        );
    }

    public static TerrainSettings random(RandomGenerator rng) {
        return new TerrainSettings(
                rng.nextFloat(), rng.nextFloat(), rng.nextFloat(),
                rng.nextFloat(), rng.nextFloat(), rng.nextFloat(),
                rng.nextFloat(), rng.nextFloat(), rng.nextFloat(),
                rng.nextFloat(), rng.nextFloat()
        );
    }
}
