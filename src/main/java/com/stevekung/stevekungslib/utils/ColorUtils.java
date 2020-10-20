package com.stevekung.stevekungslib.utils;

import net.minecraft.util.text.Color;

public class ColorUtils
{
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public ColorUtils(String color)
    {
        this(ColorUtils.safeParse(color));
    }

    public ColorUtils(int color)
    {
        this((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >> 24 & 255) / 255.0F);
    }

    public ColorUtils(float red, float green, float blue)
    {
        this(red, green, blue, 255);
    }

    public ColorUtils(float red, float green, float blue, float alpha)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
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

    public static int to32Bit(int red, int green, int blue, int alpha)
    {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static float[] toFloatArray(int red, int green, int blue)
    {
        return new float[] { red / 255.0F, green / 255.0F, blue / 255.0F };
    }

    public static int toDecimal(int red, int green, int blue)
    {
        return blue + 256 * green + 65536 * red;
    }

    public static String toHex(int red, int green, int blue)
    {
        return String.format("#%02x%02x%02x", red, green, blue);
    }

    public static int hexToDecimal(String color)
    {
        return Color.fromHex(color).getColor();
    }

    public static String decimalToRgb(int color)
    {
        ColorUtils utils = new ColorUtils(color);
        StringBuilder builder = new StringBuilder();
        builder.append(utils.packedRed());
        builder.append(",");
        builder.append(utils.packedGreen());
        builder.append(",");
        builder.append(utils.packedBlue());
        return builder.toString();
    }

    public static int rgbToDecimal(String color)
    {
        try
        {
            String[] colorArray = color.split(",");
            int red = Integer.parseInt(colorArray[0]);
            int green = Integer.parseInt(colorArray[1]);
            int blue = Integer.parseInt(colorArray[2]);
            boolean hasAlpha = colorArray.length == 4;

            if (hasAlpha)
            {
                int alpha = Integer.parseInt(colorArray[3]);
                return alpha << 24 | red << 16 | green << 8 | blue;
            }
            else
            {
                return blue + 256 * green + 65536 * red;
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
    }

    private static int safeParse(String color)
    {
        if (color.startsWith("#"))
        {
            return ColorUtils.hexToDecimal(color);
        }
        else
        {
            try
            {
                String[] colorArray = color.split(",");
                int red = Integer.parseInt(colorArray[0]);
                int green = Integer.parseInt(colorArray[1]);
                int blue = Integer.parseInt(colorArray[2]);
                boolean hasAlpha = colorArray.length == 4;

                if (hasAlpha)
                {
                    int alpha = Integer.parseInt(colorArray[3]);
                    return alpha << 24 | red << 16 | green << 8 | blue;
                }
                else
                {
                    return blue + 256 * green + 65536 * red;
                }
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
            {
                return 0;
            }
        }
    }
}