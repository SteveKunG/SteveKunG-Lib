package com.stevekung.stevekungslib.utils.config;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class SliderPercentageSettings<T extends Settings> extends AbstractSettings<T>
{
    protected final float stepSize;
    protected final double minValue;
    protected double maxValue;
    private final Function<T, Double> getter;
    private final BiConsumer<T, Double> setter;
    private final BiFunction<T, SliderPercentageSettings<T>, Component> getDisplayStringFunc;

    public SliderPercentageSettings(String translationKey, double minValue, double maxValue, float stepSize, Function<T, Double> getter, BiConsumer<T, Double> setter, BiFunction<T, SliderPercentageSettings<T>, Component> getDisplayString)
    {
        super(translationKey);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepSize = stepSize;
        this.getter = getter;
        this.setter = setter;
        this.getDisplayStringFunc = getDisplayString;
    }

    @Override
    public AbstractWidget createWidget(T settings, int x, int y, int width)
    {
        return new SettingsSlider<>(settings, x, y, width, 20, this);
    }

    public double normalizeValue(double value)
    {
        return Mth.clamp((this.snapToStepClamp(value) - this.minValue) / (this.maxValue - this.minValue), 0.0D, 1.0D);
    }

    public double denormalizeValue(double value)
    {
        return this.snapToStepClamp(Mth.lerp(Mth.clamp(value, 0.0D, 1.0D), this.minValue, this.maxValue));
    }

    private double snapToStepClamp(double value)
    {
        if (this.stepSize > 0.0F)
        {
            value = this.stepSize * Math.round(value / this.stepSize);
        }
        return Mth.clamp(value, this.minValue, this.maxValue);
    }

    public double getMinValue()
    {
        return this.minValue;
    }

    public double getMaxValue()
    {
        return this.maxValue;
    }

    public void setMaxValue(float value)
    {
        this.maxValue = value;
    }

    public void set(T settings, double value)
    {
        this.setter.accept(settings, value);
    }

    public double get(T settings)
    {
        return this.getter.apply(settings);
    }

    public Component getMessage(T settings)
    {
        return this.getDisplayStringFunc.apply(settings, this);
    }
}