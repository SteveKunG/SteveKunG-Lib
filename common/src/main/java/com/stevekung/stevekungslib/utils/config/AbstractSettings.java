package com.stevekung.stevekungslib.utils.config;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;

public abstract class AbstractSettings<T extends Settings>
{
    private final Component translatedBaseMessage;
    private Optional<List<FormattedCharSequence>> optionValues = Optional.empty();

    public AbstractSettings(String translationKey)
    {
        this.translatedBaseMessage = new TranslatableComponent(translationKey);
    }

    public abstract AbstractWidget createWidget(T settings, int x, int y, int width);

    public void setOptionValues(List<FormattedCharSequence> values)
    {
        this.optionValues = Optional.of(values);
    }

    public Optional<List<FormattedCharSequence>> getSettingValues()
    {
        return this.optionValues;
    }

    public Component getBaseMessageTranslation()
    {
        return this.translatedBaseMessage;
    }

    public Component getPercentValueComponent(double percentage)
    {
        return new TranslatableComponent("options.percent_value", this.getBaseMessageTranslation(), (int)(percentage * 100.0D));
    }

    public Component getGenericValueComponent(Component valueMessage)
    {
        return new TranslatableComponent("options.generic_value", this.getBaseMessageTranslation(), valueMessage);
    }

    public Component getMessageWithValue(int value)
    {
        return this.getGenericValueComponent(new TextComponent(Integer.toString(value)));
    }
}