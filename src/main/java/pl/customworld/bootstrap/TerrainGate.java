package pl.customworld.bootstrap;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import pl.customworld.config.TerrainSettingsState;

import static pl.customworld.bootstrap.PlayerRefs.nameAndId;

/**
 * Centralna logika „twardej” bramy: pierwszy spawn właściciela nie może iść na overworld,
 * dopóki ustawienia nie zostaną zatwierdzone; overworld nie powinien awansować chunków w górę
 * statusów generacji w tym czasie.
 */
public final class TerrainGate {

    private TerrainGate() {
    }

    /**
     * Czy zamiast podanego poziomu (zwykle overworld) gracz ma zostać umieszczony w {@link ConfigHoldLevelKey}.
     */
    public static boolean shouldRedirectSpawnToHold(ServerLevel requestedLevel, ServerPlayer player) {
        if (!isOverworld(requestedLevel)) {
            return false;
        }
        TerrainSettingsState state = TerrainSettingsState.get(requestedLevel);
        if (state.isFinalized()) {
            return false;
        }
        return isTerrainAuthority(player, requestedLevel.getServer());
    }

    /**
     * Zatrzymuje tick chunków overworld tylko gdy konfiguracja nie jest zatwierdzona
     * <strong>i</strong> nikt nie stoi w overworld. Inaczej klient SP zostaje na „Loading terrain”.
     */
    public static boolean shouldFreezeOverworldChunkPipeline(ServerLevel level) {
        if (!isOverworld(level)) {
            return false;
        }
        if (TerrainSettingsState.get(level).isFinalized()) {
            return false;
        }
        MinecraftServer server = level.getServer();
        for (ServerPlayer p : server.getPlayerList().getPlayers()) {
            if (p.level() == level) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTerrainAuthority(ServerPlayer player, MinecraftServer server) {
        if (server.isSingleplayerOwner(nameAndId(player))) {
            return true;
        }
        return TerrainDedicatedOwner.matches(player, server);
    }

    private static boolean isOverworld(ServerLevel level) {
        return level.dimension() == Level.OVERWORLD;
    }
}
