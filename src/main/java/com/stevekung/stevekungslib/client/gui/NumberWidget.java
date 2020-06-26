package com.stevekung.stevekungslib.client.gui;

import com.stevekung.stevekungslib.utils.JsonUtils;
import com.stevekung.stevekungslib.utils.NumberUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class NumberWidget extends TextFieldWidget
{
    public NumberWidget(FontRenderer font, int x, int y, int width, int height)
    {
        super(font, x, y, width, height, JsonUtils.create("Number Field"));
    }

    @Override
    public void writeText(String textToWrite)
    {
        if (NumberUtils.isNumeric(textToWrite))
        {
            super.writeText(textToWrite);
        }
    }
}