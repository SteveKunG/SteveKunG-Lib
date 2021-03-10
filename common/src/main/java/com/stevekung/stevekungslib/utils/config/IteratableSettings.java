package com.stevekung.stevekungslib.utils.config;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

public class IteratableSettings<T extends Settings> extends AbstractSettings<T>
{
    private final BiConsumer<T, Integer> setter;
    private final BiFunction<T, IteratableSettings<T>, Component> getter;

    public IteratableSettings(String translationKey, BiConsumer<T, Integer> setter, BiFunction<T, IteratableSettings<T>, Component> getter)
    {
        super(translationKey);
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public AbstractWidget createWidget(T settings, int x, int y, int width)
    {
        return new SettingsButton<>(x, y, width, 20, this, this.getMessage(settings), button ->
        {
            this.setValueIndex(settings, 1);
            button.setMessage(this.getMessage(settings));
        });
    }

    public void setValueIndex(T settings, int value)
    {
        this.setter.accept(settings, value);
        settings.save();
    }

    public Component getMessage(T settings)
    {
        return this.getter.apply(settings, this);
    }
}