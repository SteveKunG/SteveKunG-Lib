package com.stevekung.stevekungslib.client.gui;

import com.stevekung.stevekungslib.utils.NumberUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class NumberWidget extends TextFieldWidget
{
    public NumberWidget(FontRenderer font, int x, int y, int width, int height)
    {
        super(font, x, y, width, height, "Number Field");
    }

    @Override
    public void writeText(String textToWrite)
    {
        for (int i = 0; i < textToWrite.length(); i++)
        {
            if (NumberUtils.isNumber(textToWrite.charAt(i)))
            {
                super.writeText(textToWrite);
            }
        }
    }
}