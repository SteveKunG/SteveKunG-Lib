package com.stevekung.stevekunglib.utils.config;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.TextComponent;

public abstract class AbstractSettingsSlider<T extends Settings> extends AbstractSliderButton
{
    protected final T settings;

    protected AbstractSettingsSlider(T settings, int x, int y, int width, int height, double defaultValue)
    {
        super(x, y, width, height, TextComponent.EMPTY, defaultValue);
        this.settings = settings;
    }
}