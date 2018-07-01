package stevekung.mods.stevekunglib.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColoredFontRendererUnicode extends FontRenderer
{
    private static final int MARKER = 59136;
    private boolean dropShadow;
    private int state = 0;
    private int red;
    private int green;
    private int blue;

    public ColoredFontRendererUnicode(GameSettings gameSettings, ResourceLocation location, TextureManager textureManager)
    {
        super(gameSettings, location, textureManager, true);
    }

    @Override
    protected String wrapFormattedStringToWidth(String str, int wrapWidth)
    {
        int i = this.sizeStringToWidth(str, wrapWidth);

        if (str.length() <= i)
        {
            return str;
        }
        else
        {
            String s = str.substring(0, i);
            char c0 = str.charAt(i);
            boolean flag = c0 == ' ' || c0 == '\n';
            String s1 = this.getCustomFormatFromString(s) + str.substring(i + (flag ? 1 : 0));
            return s + "\n" + this.wrapFormattedStringToWidth(s1, wrapWidth);
        }
    }

    @Override
    public int renderString(String text, float x, float y, int color, boolean dropShadow)
    {
        this.dropShadow = dropShadow;
        return super.renderString(text, x, y, color, dropShadow);
    }

    @Override
    protected float renderUnicodeChar(char charac, boolean italic)
    {
        return this.renderColoredChar(charac, super.renderUnicodeChar(charac, italic));
    }

    @Override
    protected float renderDefaultChar(int charac, boolean italic)
    {
        return this.renderColoredChar(charac, super.renderDefaultChar(charac, italic));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        super.onResourceManagerReload(resourceManager);
        this.setUnicodeFlag(true);
        this.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
    }

    public static String color(int r, int g, int b)
    {
        return String.format("%c%c%c", (char) (ColoredFontRendererUnicode.MARKER + (r & 255)), (char) (ColoredFontRendererUnicode.MARKER + (g & 255)), (char) (ColoredFontRendererUnicode.MARKER + (b & 255)));
    }

    private float renderColoredChar(int charac, float defaultValue)
    {
        if (charac >= ColoredFontRendererUnicode.MARKER && charac <= ColoredFontRendererUnicode.MARKER + 255)
        {
            int value = charac & 255;

            switch (this.state)
            {
            case 0:
                this.red = value;
                break;
            case 1:
                this.green = value;
                break;
            case 2:
                this.blue = value;
                break;
            default:
                this.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                return 0.0F;
            }

            this.state = ++this.state % 3;
            int color = this.red << 16 | this.green << 8 | this.blue | 255 << 24;

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }
            if (this.dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }
            this.setColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color >> 0 & 255) / 255.0F, (color >> 24 & 255) / 255.0F);
            return 0.0F;
        }
        if (this.state != 0)
        {
            this.state = 0;
            this.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        return defaultValue;
    }

    private boolean isFormatColor(char colorChar)
    {
        return colorChar >= '0' && colorChar <= '9' || colorChar >= 'a' && colorChar <= 'f' || colorChar >= 'A' && colorChar <= 'F';
    }

    private boolean isFormatSpecial(char formatChar)
    {
        return formatChar >= 'k' && formatChar <= 'o' || formatChar >= 'K' && formatChar <= 'O' || formatChar == 'r' || formatChar == 'R';
    }

    private String getCustomFormatFromString(String text)
    {
        String s = "";
        int i = -1;
        int j = text.length();

        while ((i = text.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = text.charAt(i + 1);

                if (this.isFormatColor(c0))
                {
                    s = "\u00a7" + c0;
                }
                else if (this.isFormatSpecial(c0))
                {
                    s = s + "\u00a7" + c0;
                }
                else if (c0 >= ColoredFontRendererUnicode.MARKER && c0 <= ColoredFontRendererUnicode.MARKER + 255)
                {
                    s = String.format("%s%s%s", c0, text.charAt(i + 1), text.charAt(i + 2));
                    i += 2;
                }
            }
        }
        return s;
    }
}