package pl.customworld;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.customworld.bootstrap.TerrainBootstrapEvents;
import pl.customworld.config.TerrainSettingsState;
import pl.customworld.net.TerrainConfigNetworking;

/**
 * Punkt wejścia serwera: rejestracja sieci, hooków cyklu życia świata
 * i integracja z {@link pl.customworld.world.CustomChunkGenerator}.
 */
public final class CustomWorldMod implements ModInitializer {

    public static final String MOD_ID = "custom_world";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        TerrainConfigNetworking.registerServer();
        TerrainSettingsState.register();
        TerrainBootstrapEvents.register();
        // TODO: po C2S „Done”: setFinalized(true), teleport właściciela na overworld spawn, zapis SavedData
        // TODO: rejestracja ChunkGenerator (MapCodec + Registry) + WorldPreset wskazujący na custom noise
    }
}
