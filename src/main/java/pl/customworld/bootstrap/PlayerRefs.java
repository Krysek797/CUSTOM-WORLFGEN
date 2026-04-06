package pl.customworld.bootstrap;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.NameAndId;

/**
 * 26.1: {@code isSingleplayerOwner}/{@code isOp} przyjmują {@link NameAndId} zamiast {@code GameProfile}.
 */
public final class PlayerRefs {

    private PlayerRefs() {
    }

    public static NameAndId nameAndId(ServerPlayer player) {
        return new NameAndId(player.getGameProfile());
    }
}
