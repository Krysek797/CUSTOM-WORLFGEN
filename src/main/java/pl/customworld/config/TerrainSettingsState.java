package pl.customworld.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import pl.customworld.CustomWorldMod;

/**
 * Stan trwały overworld (26.1: {@link SavedDataType} + {@link Codec}).
 */
public final class TerrainSettingsState extends SavedData {

    private boolean finalized;
    private TerrainSettings settings;

    public TerrainSettingsState() {
        this(false, TerrainSettings.defaults());
    }

    public TerrainSettingsState(boolean finalized, TerrainSettings settings) {
        this.finalized = finalized;
        this.settings = settings;
    }

    public static final Codec<TerrainSettingsState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("finalized").forGetter(TerrainSettingsState::isFinalized),
            TerrainSettings.CODEC.fieldOf("settings").forGetter(TerrainSettingsState::settings)
    ).apply(instance, TerrainSettingsState::new));

    private static final SavedDataType<TerrainSettingsState> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(CustomWorldMod.MOD_ID, "custom_world_terrain"),
            TerrainSettingsState::new,
            CODEC,
            null
    );

    public static TerrainSettingsState get(ServerLevel overworld) {
        return overworld.getDataStorage().computeIfAbsent(TYPE);
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
        setDirty();
    }

    public TerrainSettings settings() {
        return settings;
    }

    public void setSettings(TerrainSettings settings) {
        this.settings = settings;
        setDirty();
    }

    public static void register() {
        // leniwe przez computeIfAbsent(TYPE)
    }
}
