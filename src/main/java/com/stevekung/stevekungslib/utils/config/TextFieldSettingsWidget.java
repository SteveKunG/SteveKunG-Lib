package com.stevekung.stevekungslib.utils.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TextFieldSettingsWidget<T extends Settings> extends TextFieldWidget
{
    private final TextFieldSettings<T> textFieldSetting;
    private ITextComponent displayName;

    public TextFieldSettingsWidget(int x, int y, int width, TextFieldSettings<T> textFieldSetting)
    {
        super(Minecraft.getInstance().fontRenderer, x, y, width, 20, StringTextComponent.EMPTY);
        this.textFieldSetting = textFieldSetting;
        this.setVisible(true);
        this.setMaxStringLength(13);
    }

    public void setValue(T settings, String value)
    {
        this.textFieldSetting.set(settings, value);
        settings.save();
    }

    public void setDisplayName(ITextComponent name)
    {
        this.displayName = name;
    }

    public ITextComponent getDisplayName()
    {
        return this.displayName;
    }
}