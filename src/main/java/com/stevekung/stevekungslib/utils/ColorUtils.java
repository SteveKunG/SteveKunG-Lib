package com.stevekung.stevekungslib.utils;

import com.stevekung.stevekungslib.client.event.ClientEventHandler;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ColorUtils
{
    public static final int CUSTOM_COLOR_MARKER = 60160;
    public static FontRenderer unicodeFontRenderer;
    public static FontResourceManager unicodeFontResourceMananger;

    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        /*Minecraft mc = Minecraft.getInstance();

        ColorUtils.unicodeFontResourceMananger = new FontResourceManager(mc.textureManager, true);
        ((IReloadableResourceManager)mc.getResourceManager()).addReloadListener(ColorUtils.unicodeFontResourceMananger.func_216884_a());
        ColorUtils.unicodeFontRenderer = ColorUtils.unicodeFontResourceMananger.getFontRenderer(Minecraft.DEFAULT_FONT_RENDERER_NAME);

        if (mc.gameSettings.language != null)
        {
            ColorUtils.unicodeFontRenderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
        }*/
    }

    public static int rgbToDecimal(int r, int g, int b)
    {
        return b + 256 * g + 65536 * r;
    }

    public static int hexToDecimal(String color)
    {
        return ColorUtils.rgbToDecimal(Integer.valueOf(color.substring(1, 3), 16), Integer.valueOf(color.substring(3, 5), 16), Integer.valueOf(color.substring(5, 7), 16));
    }

    public static RGB hexToRGB(String color)
    {
        return new RGB(Integer.valueOf(color.substring(1, 3), 16), Integer.valueOf(color.substring(3, 5), 16), Integer.valueOf(color.substring(5, 7), 16), 255);
    }

    public static float[] rgbToFloatArray(int r, int g, int b)
    {
        return new float[] { r / 255.0F, g / 255.0F, b / 255.0F };
    }

    public static int to32BitColor(int a, int r, int g, int b)
    {
        a = a << 24;
        r = r << 16;
        g = g << 8;
        return a | r | g | b;
    }

    public static RGB stringToRGB(String color)
    {
        return ColorUtils.stringToRGB(color, false, null);
    }

    public static RGB stringToRGBA(String color)
    {
        return ColorUtils.stringToRGBA(color, false, null);
    }

    public static RGB colorToRGB(int red, int green, int blue, int alpha)
    {
        return new RGB(red, green, blue, alpha);
    }

    public static RGB stringToRGB(String color, boolean printException, String optionName)
    {
        try
        {
            String[] colorArray = color.split(",");
            float red = Float.parseFloat(colorArray[0]);
            float green = Float.parseFloat(colorArray[1]);
            float blue = Float.parseFloat(colorArray[2]);
            return new RGB(red, green, blue, 255.0F);
        }
        catch (Exception e)
        {
            if (printException)
            {
                SteveKunGLib.LOGGER.error("Invalid RGB Color format at option {}!", optionName);
                ClientUtils.printClientMessage("Invalid RGB Color format at option " + optionName + "!", JsonUtils.RED);
                e.printStackTrace();
            }
            return new RGB(true);
        }
    }

    public static RGB stringToRGBA(String color, boolean printException, String optionName)
    {
        try
        {
            String[] colorArray = color.split(",");
            float red = Float.parseFloat(colorArray[0]);
            float green = Float.parseFloat(colorArray[1]);
            float blue = Float.parseFloat(colorArray[2]);
            float alpha = Float.parseFloat(colorArray[3]);
            return new RGB(red, green, blue, alpha);
        }
        catch (Exception e)
        {
            if (printException)
            {
                SteveKunGLib.LOGGER.error("Invalid RGB Color format at option {}!", optionName);
                ClientUtils.printClientMessage("Invalid RGB Color format at option " + optionName + "!", JsonUtils.RED);
                e.printStackTrace();
            }
            return new RGB(true);
        }
    }

    public static RGB toRGB(int color)
    {
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        return new RGB(red, green, blue, alpha);
    }

    public static boolean isMarker(char charac)
    {
        return charac >= ColorUtils.CUSTOM_COLOR_MARKER && charac <= ColorUtils.CUSTOM_COLOR_MARKER + 255;
    }

    public static class RGB
    {
        float red;
        float green;
        float blue;
        float alpha;
        boolean error;

        public RGB(float red, float green, float blue, float alpha)
        {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        private RGB(boolean error)
        {
            this.error = error;
        }

        public int packedRed()
        {
            return (int)(this.red * 255);
        }

        public int packedGreen()
        {
            return (int)(this.green * 255);
        }

        public int packedBlue()
        {
            return (int)(this.blue * 255);
        }

        public int packedAlpha()
        {
            return (int)(this.alpha * 255);
        }

        public float floatRed()
        {
            return this.red / 255.0F;
        }

        public float floatGreen()
        {
            return this.green / 255.0F;
        }

        public float floatBlue()
        {
            return this.blue / 255.0F;
        }

        public float floatAlpha()
        {
            return this.alpha / 255.0F;
        }

        public int red()
        {
            return (int)this.red;
        }

        public int green()
        {
            return (int)this.green;
        }

        public int blue()
        {
            return (int)this.blue;
        }

        public int alpha()
        {
            return (int)this.alpha;
        }

        public String toColoredFont()
        {
            if (this.error)
            {
                if (ClientEventHandler.ticks % 16 >= 0 && ClientEventHandler.ticks % 16 <= 8)
                {
                    return this.formatColored(255, 85, 85);
                }
                else
                {
                    return this.formatColored(255, 255, 255);
                }
            }
            return this.formatColored(this.red(), this.green(), this.blue());
        }

        public int to32Bit()
        {
            return ColorUtils.to32BitColor(255, this.red(), this.green(), this.blue());
        }

        private String formatColored(int r, int g, int b)
        {
            return String.format("%c%c%c", (char)(ColorUtils.CUSTOM_COLOR_MARKER + (r & 255)), (char)(ColorUtils.CUSTOM_COLOR_MARKER + (g & 255)), (char)(ColorUtils.CUSTOM_COLOR_MARKER + (b & 255)));
        }
    }
}