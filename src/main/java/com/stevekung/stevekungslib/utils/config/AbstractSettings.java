package com.stevekung.stevekungslib.utils.config;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class AbstractSettings<T extends Settings>
{
    private final ITextComponent translatedBaseMessage;
    private Optional<List<IReorderingProcessor>> optionValues = Optional.empty();

    public AbstractSettings(String translationKey)
    {
        this.translatedBaseMessage = new TranslationTextComponent(translationKey);
    }

    public abstract Widget createWidget(T settings, int x, int y, int width);

    public void setOptionValues(List<IReorderingProcessor> values)
    {
        this.optionValues = Optional.of(values);
    }

    public Optional<List<IReorderingProcessor>> getSettingValues()
    {
        return this.optionValues;
    }

    public ITextComponent getBaseMessageTranslation()
    {
        return this.translatedBaseMessage;
    }

    public ITextComponent getPercentValueComponent(double percentage)
    {
        return new TranslationTextComponent("options.percent_value", this.getBaseMessageTranslation(), (int)(percentage * 100.0D));
    }

    public ITextComponent getGenericValueComponent(ITextComponent valueMessage)
    {
        return new TranslationTextComponent("options.generic_value", this.getBaseMessageTranslation(), valueMessage);
    }

    public ITextComponent getMessageWithValue(int value)
    {
        return this.getGenericValueComponent(new StringTextComponent(Integer.toString(value)));
    }
}