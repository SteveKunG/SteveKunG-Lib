package com.stevekung.stevekungslib.utils.config;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class BooleanSettings<T extends Settings> extends AbstractSettings<T>
{
    private final Predicate<T> getter;
    private final BiConsumer<T, Boolean> setter;

    public BooleanSettings(String translationKey, Predicate<T> getter, BiConsumer<T, Boolean> setter)
    {
        super(translationKey);
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public AbstractWidget createWidget(T settings, int x, int y, int width)
    {
        return new SettingsButton<>(x, y, width, 20, this, this.getMessage(settings), button ->
        {
            this.nextValue(settings);
            button.setMessage(this.getMessage(settings));
        });
    }

    public Component getMessage(T settings)
    {
        return CommonComponents.optionStatus(this.getBaseMessageTranslation(), this.get(settings));
    }

    public void set(T settings, String value)
    {
        this.set(settings, "true".equals(value));
    }

    public void nextValue(T settings)
    {
        this.set(settings, !this.get(settings));
        settings.save();
    }

    public boolean get(T settings)
    {
        return this.getter.test(settings);
    }

    private void set(T settings, boolean value)
    {
        this.setter.accept(settings, value);
    }
}