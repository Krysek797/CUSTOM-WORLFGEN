package pl.customworld.bootstrap;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.Level;
import pl.customworld.CustomWorldMod;
import pl.customworld.config.TerrainSettingsState;
import pl.customworld.net.TerrainConfigNetworking;

import java.util.Collections;

/**
 * Po dołączeniu właściciela do nieskonfigurowanego świata: w kolejnym kroku pętli serwera
 * ({@code server.execute}) teleport z overworld do {@link ConfigHoldLevelKey} i pakiet GUI.
 * <p>
 * Mixin na {@code PlayerList#placeNewPlayer} bywa zrywany przy zmianach sygnatury w 26.1.x —
 * ta ścieżka jest stabilniejsza (krótki postój generacji spawnu overworld).
 */
public final class TerrainBootstrapEvents {

    private TerrainBootstrapEvents() {
    }

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register(TerrainBootstrapEvents::onPlayReady);
    }

    private static void onPlayReady(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        ServerPlayer player = handler.player;
        TerrainSettingsState state = TerrainSettingsState.get(server.overworld());
        if (state.isFinalized()) {
            return;
        }
        if (!TerrainGate.isTerrainAuthority(player, server)) {
            return;
        }

        server.execute(() -> {
            if (TerrainSettingsState.get(server.overworld()).isFinalized()) {
                return;
            }
            if (!TerrainGate.isTerrainAuthority(player, server)) {
                return;
            }
            if (player.level().dimension() == ConfigHoldLevelKey.LEVEL) {
                TerrainConfigNetworking.sendOpenConfigToClient(player);
                return;
            }
            if (player.level().dimension() != Level.OVERWORLD) {
                return;
            }
            ServerLevel hold = server.getLevel(ConfigHoldLevelKey.LEVEL);
            if (hold == null) {
                CustomWorldMod.LOGGER.warn("Brak wymiaru custom_world:config_hold — sprawdź datapack.");
                return;
            }
            player.teleportTo(hold, 0.5, 4.0, 0.5, Collections.emptySet(), 0.0f, 0.0f, false);
            TerrainConfigNetworking.sendOpenConfigToClient(player);
        });
    }
}
