package com.stevekung.stevekungslib.client.gui.components;

import com.stevekung.stevekungslib.utils.NumberUtils;
import com.stevekung.stevekungslib.utils.TextComponentUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;

public class NumberEditBox extends EditBox
{
    public NumberEditBox(Font font, int x, int y, int width, int height)
    {
        super(font, x, y, width, height, TextComponentUtils.component("Number Edit Box"));
    }

    @Override
    public void insertText(String textToWrite)
    {
        if (NumberUtils.isNumeric(textToWrite))
        {
            super.insertText(textToWrite);
        }
    }
}