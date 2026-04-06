package pl.customworld.client.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

/**
 * Suwak z zakresem logicznym [minValue, maxValue] i aktualną wartością float w [0,1] lub dowolnym mapowaniu.
 */
public final class TerrainSlider extends AbstractSliderButton {

    private final String labelKey;
    private final float minValue;
    private final float maxValue;
    private float current;

    public TerrainSlider(int x, int y, int width, String labelKey, float minValue, float maxValue, float initial) {
        super(x, y, width, 20, Component.empty(), normalize(initial, minValue, maxValue));
        this.labelKey = labelKey;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.current = initial;
        updateMessage();
    }

    private static double normalize(float value, float min, float max) {
        if (max <= min) {
            return 0;
        }
        return (value - min) / (max - min);
    }

    public float getValue() {
        return current;
    }

    public void setValue(float value) {
        this.current = clamp(value, minValue, maxValue);
        this.value = normalize(current, minValue, maxValue);
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.translatable(labelKey, String.format("%.2f", current)));
    }

    @Override
    protected void applyValue() {
        current = (float) (minValue + value * (maxValue - minValue));
        updateMessage();
    }

    private static float clamp(float v, float lo, float hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}
