package com.stevekung.stevekungslib.utils.config;

import java.util.function.BiConsumer;
import java.util.function.Function;

import net.minecraft.client.gui.components.AbstractWidget;

public class TextFieldSettings<T extends Settings> extends AbstractSettings<T>
{
    private final Function<T, String> getter;
    private final BiConsumer<T, String> setter;

    public TextFieldSettings(String key, Function<T, String> getter, BiConsumer<T, String> setter)
    {
        super(key);
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public AbstractWidget createWidget(T settings, int x, int y, int width)
    {
        TextFieldSettingsWidget<T> textField = new TextFieldSettingsWidget<>(x, y, width, this);
        this.set(settings, this.get(settings));
        textField.setValue(this.get(settings));
        textField.setDisplayName(this.getBaseMessageTranslation());
        return textField;
    }

    public void set(T settings, String value)
    {
        this.setter.accept(settings, value);
    }

    public String get(T settings)
    {
        return this.getter.apply(settings);
    }
}