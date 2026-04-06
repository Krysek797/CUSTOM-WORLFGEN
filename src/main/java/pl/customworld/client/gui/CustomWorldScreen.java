package pl.customworld.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import pl.customworld.config.TerrainSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * GUI dwóch kolumn suwaków + Random / Done. Po Done wysyła {@link pl.customworld.net.TerrainConfigNetworking}
 * pakiet C2S z ustawieniami (tylko po stronie klienta właściciela z modem).
 */
public final class CustomWorldScreen extends Screen {

    private final Screen parent;
    private final TerrainSettings initial;
    private final Consumer<TerrainSettings> onDone;
    private final List<TerrainSlider> sliders = new ArrayList<>();

    public CustomWorldScreen(Screen parent, TerrainSettings initial, Consumer<TerrainSettings> onDone) {
        super(Component.translatable("custom_world.screen.title"));
        this.parent = parent;
        this.initial = initial;
        this.onDone = onDone;
    }

    @Override
    protected void init() {
        super.init();
        sliders.clear();
        clearWidgets();

        int colW = (this.width / 2) - 30;
        int leftX = 20;
        int rightX = this.width / 2 + 10;
        int y = 40;
        int gap = 24;

        addSlider(leftX, y, colW, "custom_world.ocean", sliders, initial.oceanFrequency());
        y += gap;
        addSlider(leftX, y, colW, "custom_world.mountain", sliders, initial.mountainFrequency());
        y += gap;
        addSlider(leftX, y, colW, "custom_world.height", sliders, initial.terrainHeight());
        y += gap;
        addSlider(leftX, y, colW, "custom_world.noise_scale", sliders, initial.noiseScale());
        y += gap;
        addSlider(leftX, y, colW, "custom_world.cave_size", sliders, initial.caveSize());
        y += gap;
        addSlider(leftX, y, colW, "custom_world.cave_freq", sliders, initial.caveFrequency());

        y = 40;
        addSlider(rightX, y, colW, "custom_world.structures", sliders, initial.structureDensity());
        y += gap;
        addSlider(rightX, y, colW, "custom_world.biome_size", sliders, initial.biomeSize());
        y += gap;
        addSlider(rightX, y, colW, "custom_world.sea_level", sliders, initial.seaLevel());
        y += gap;
        addSlider(rightX, y, colW, "custom_world.roughness", sliders, initial.terrainRoughness());
        y += gap;
        addSlider(rightX, y, colW, "custom_world.vegetation", sliders, initial.vegetationDensity());

        int bottom = this.height - 30;
        addRenderableWidget(Button.builder(Component.translatable("custom_world.random"), b -> randomize())
                .bounds(this.width / 2 - 160, bottom, 100, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), b -> applyDone())
                .bounds(this.width / 2 - 50, bottom, 100, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.cancel"), b -> this.minecraft.setScreen(parent))
                .bounds(this.width / 2 + 60, bottom, 100, 20).build());
    }

    private void addSlider(int x, int y, int w, String key, List<TerrainSlider> list, float initialValue) {
        TerrainSlider s = new TerrainSlider(x, y, w, key, 0f, 1f, initialValue);
        addRenderableWidget(s);
        list.add(s);
    }

    private void randomize() {
        var rng = java.util.random.RandomGenerator.getDefault();
        for (TerrainSlider s : sliders) {
            s.setValue(rng.nextFloat());
        }
    }

    private void applyDone() {
        if (sliders.size() < 11) {
            return;
        }
        TerrainSettings ts = new TerrainSettings(
                sliders.get(0).getValue(),
                sliders.get(1).getValue(),
                sliders.get(2).getValue(),
                sliders.get(3).getValue(),
                sliders.get(4).getValue(),
                sliders.get(5).getValue(),
                sliders.get(6).getValue(),
                sliders.get(7).getValue(),
                sliders.get(8).getValue(),
                sliders.get(9).getValue(),
                sliders.get(10).getValue()
        );
        onDone.accept(ts);
        this.minecraft.setScreen(null);
    }
}
