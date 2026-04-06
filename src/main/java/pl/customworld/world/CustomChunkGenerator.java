package pl.customworld.world;

import pl.customworld.config.TerrainSettings;

/**
 * Generator chunków oparty na vanilla noise, z mnożnikami / offsetami z {@link TerrainSettings}.
 * <p>
 * Strategia implementacji (26.1, bez nowych biomów):
 * <ul>
 *   <li>Zarejestrować typ w rejestrze generatorów (MapCodec) i podpiąć go w dimension json / WorldPreset.</li>
 *   <li>Odczyt {@link TerrainSettings} z {@link pl.customworld.config.TerrainSettingsState} (SavedData) lub
 *       z kontekstu seed + immutable snapshot ustawiony przy finalize.</li>
 *   <li>Delegacja do logiki zbliżonej do {@code NoiseBasedChunkGenerator}: modyfikacja
 *       DensityFunction / NoiseRouter (skala, amplitude) zamiast kopiowania całego kodu, o ile API na to pozwala.</li>
 *   <li>Ocean frequency / mountain frequency: przez modyfikację konturowania (np. przesunięcie poziomu wody
 *       i wzmocnienie warstw continental/ridges) — mapowanie z suwaków na konkretne współczynniki w jednym miejscu.</li>
 * </ul>
 */
public final class CustomChunkGenerator {
    // TODO: extends ChunkGenerator + konstruktor z RegistryAccess i holder NoiseGeneratorSettings
    // TODO: apply TerrainSettings do sampled density / aquifers / noodle caves zgodnie z suwakami

    private CustomChunkGenerator() {
    }
}
