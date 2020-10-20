package com.stevekung.stevekungslib.utils.config;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;

public abstract class AbstractSettingsSlider<T extends Settings> extends AbstractSlider
{
    protected final T settings;

    protected AbstractSettingsSlider(T settings, int x, int y, int width, int height, double defaultValue)
    {
        super(x, y, width, height, StringTextComponent.EMPTY, defaultValue);
        this.settings = settings;
    }
}