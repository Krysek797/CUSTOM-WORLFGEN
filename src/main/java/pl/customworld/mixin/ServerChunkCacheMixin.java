package pl.customworld.mixin;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.customworld.bootstrap.TerrainGate;

/**
 * Drugi filtr: dopóki teren nie jest zatwierdzony, nie tykaj cache chunków <strong>overworld</strong>,
 * żeby vanilla nie awansowała statusów generacji (gracz powinien być w {@code config_hold}).
 * <p>
 * Ryzyko: anulowanie całego {@code tick} może wpływać na światło / entity w rzadkich przypadkach —
 * po zatwierdzeniu {@link pl.customworld.config.TerrainSettingsState#isFinalized()} mixin jest przezroczysty.
 * 26.1: jeśli metoda nie nazywa się {@code tick}, popraw {@code method} wg mcsrc.dev.
 */
@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin {

    @Shadow
    @Final
    ServerLevel level;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void customworld$skipOverworldTickWhileAwaitingConfig(CallbackInfo ci) {
        if (TerrainGate.shouldFreezeOverworldChunkPipeline(this.level)) {
            ci.cancel();
        }
    }
}
