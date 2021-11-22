package com.stevekung.stevekunglib.utils.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class TextFieldSettingsWidget<T extends Settings> extends EditBox
{
    private final TextFieldSettings<T> textFieldSetting;
    private Component displayName;

    public TextFieldSettingsWidget(int x, int y, int width, TextFieldSettings<T> textFieldSetting)
    {
        super(Minecraft.getInstance().font, x, y, width, 20, TextComponent.EMPTY);
        this.textFieldSetting = textFieldSetting;
        this.setVisible(true);
        this.setMaxLength(13);
    }

    public void setValue(T settings, String value)
    {
        this.textFieldSetting.set(settings, value);
        settings.save();
    }

    public void setDisplayName(Component name)
    {
        this.displayName = name;
    }

    public Component getDisplayName()
    {
        return this.displayName;
    }
}