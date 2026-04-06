package pl.customworld.bootstrap;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import pl.customworld.CustomWorldMod;

/**
 * Wymiar „czekania” na GUI: płaski void — datapack {@code custom_world:config_hold}.
 */
public final class ConfigHoldLevelKey {

    public static final ResourceKey<Level> LEVEL = ResourceKey.create(
            Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(CustomWorldMod.MOD_ID, "config_hold")
    );

    private ConfigHoldLevelKey() {
    }
}
