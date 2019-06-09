package stevekung.mods.stevekungslib.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import stevekung.mods.stevekungslib.utils.NumberUtils;

@OnlyIn(Dist.CLIENT)
public class GuiNumberField extends TextFieldWidget
{
    public GuiNumberField(FontRenderer font, int x, int y, int width, int height)
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