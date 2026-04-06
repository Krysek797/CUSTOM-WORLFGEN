package pl.customworld.net;

import pl.customworld.config.TerrainSettings;

/**
 * Fabric Networking API: CustomPayload (S2C otwórz ekran, C2S zatwierdź ustawienia).
 * Serwer weryfikuje nadawcę (właściciel integrated / pierwszy operator / zapisane UUID w level.dat).
 */
public final class TerrainConfigNetworking {

    private TerrainConfigNetworking() {
    }

    public static void registerServer() {
        // TODO: PayloadTypeRegistry.playS2C / playC2S + PlayNetworking.registerGlobalReceiver
    }

    public static void registerClient() {
        // TODO: rejestracja odbiornika S2C → Minecraft.getInstance().setScreen(new CustomWorldScreen(...))
    }

    public static void sendOpenConfigToClient(Object player) {
        // TODO: PlayNetworking.send(player, ...)
    }

    public static void sendSettingsToServer(TerrainSettings settings) {
        // TODO: PlayNetworking.send(ClientPlayNetworking, ...)
    }
}
