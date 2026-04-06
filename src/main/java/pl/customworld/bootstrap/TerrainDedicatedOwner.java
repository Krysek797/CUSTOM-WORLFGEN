package pl.customworld.bootstrap;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import static pl.customworld.bootstrap.PlayerRefs.nameAndId;

/**
 * Na serwerze dedykowanym: dopasowanie gracza do skonfigurowanego właściciela terenu (np. plik konfiguracyjny).
 * Do implementacji: odczyt UUID przy starcie serwera i cache w polu statycznym.
 */
public final class TerrainDedicatedOwner {

    private TerrainDedicatedOwner() {
    }

    public static boolean matches(ServerPlayer player, MinecraftServer server) {
        if (!server.isDedicatedServer()) {
            return false;
        }
        // Tymczasowo: operator — docelowo plik z UUID właściciela terenu
        return server.getPlayerList().isOp(nameAndId(player));
    }
}
