package pl.customworld.client;

import net.fabricmc.api.ClientModInitializer;
import pl.customworld.net.TerrainConfigNetworking;

/**
 * Klient: rejestracja odbiornika pakietów otwierającego {@link pl.customworld.client.gui.CustomWorldScreen}.
 */
public final class CustomWorldClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TerrainConfigNetworking.registerClient();
    }
}
