package stevekung.mods.stevekunglib.utils.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColoredFontRenderer extends FontRenderer
{
    private final TextureManager textureManager;
    private final Font font;
    private static final int MARKER = 59136;
    private int state = 0;

    public ColoredFontRenderer(TextureManager textureManager, Font font)
    {
        super(textureManager, font);
        System.out.println(this.getClass().getName() + " is loaded!");
        this.textureManager = textureManager;
        this.font = font;
    }

    @Override
    public String trimStringToWidth(String str, int wrapWidth)
    {
        int i = this.sizeStringToWidth(str, wrapWidth);

        if (str.length() <= i)
        {
            return str;
        }
        else
        {
            String str2 = str.substring(0, i);
            char charAt = str.charAt(i);
            boolean flag = charAt == ' ' || charAt == '\n';
            String text = this.getCustomFormatFromString(str2) + str.substring(i + (flag ? 1 : 0));
            return str2 + "\n" + this.wrapFormattedStringToWidth(text, wrapWidth);
        }
    }

    @Override
    protected int renderString(String text, float x, float y, int color, boolean dropShadow)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {
            if (this.bidiFlag)
            {
                text = this.bidiReorder(text);
            }

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }

            if (dropShadow)
            {
                this.renderStringAtPos(text, x, y, color, true);
            }
            x = this.renderStringAtPos(text, x, y, color, false);
            return (int)x + (dropShadow ? 1 : 0);
        }
    }

    private float renderStringAtPos(String text, float x, float y, int color, boolean dropShadow)
    {
        float shadowAlpha = dropShadow ? 0.25F : 1.0F;
        float rawRed = (float)(color >> 16 & 255) / 255.0F * shadowAlpha;
        float rawGreen = (float)(color >> 8 & 255) / 255.0F * shadowAlpha;
        float rawBlue = (float)(color & 255) / 255.0F * shadowAlpha;
        float red = rawRed;
        float green = rawGreen;
        float blue = rawBlue;
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        ResourceLocation resource = null;
        buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        boolean obfuscated = false;
        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean strike = false;
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < text.length(); ++i)
        {
            char charAt = text.charAt(i);
            int charIndex = i + 1;

            if (charAt == 167 && charIndex < text.length())
            {
                TextFormatting format = TextFormatting.fromFormattingCode(text.charAt(charIndex));

                if (format != null)
                {
                    if (format.isNormalStyle())
                    {
                        obfuscated = false;
                        bold = false;
                        strike = false;
                        underline = false;
                        italic = false;
                        red = rawRed;
                        green = rawGreen;
                        blue = rawBlue;
                    }

                    if (format.getColor() != null)
                    {
                        int rawColor = format.getColor();
                        red = (float)(rawColor >> 16 & 255) / 255.0F * shadowAlpha;
                        green = (float)(rawColor >> 8 & 255) / 255.0F * shadowAlpha;
                        blue = (float)(rawColor & 255) / 255.0F * shadowAlpha;
                    }
                    else if (charIndex >= ColoredFontRenderer.MARKER && charIndex <= ColoredFontRenderer.MARKER + 255)
                    {
                        int value = charIndex & 255;

                        switch (this.state)
                        {
                        case 0:
                            red = value;
                            break;
                        case 1:
                            green = value;
                            break;
                        case 2:
                            blue = value;
                            break;
                        }
                        this.state = ++this.state % 3;
                    }
                    else if (format == TextFormatting.OBFUSCATED)
                    {
                        obfuscated = true;
                    }
                    else if (format == TextFormatting.BOLD)
                    {
                        bold = true;
                    }
                    else if (format == TextFormatting.STRIKETHROUGH)
                    {
                        strike = true;
                    }
                    else if (format == TextFormatting.UNDERLINE)
                    {
                        underline = true;
                    }
                    else if (format == TextFormatting.ITALIC)
                    {
                        italic = true;
                    }
                }
                ++i;
            }
            else
            {
                IGlyph glyph = this.font.getGlyph(charAt);

                if (obfuscated && charAt != ' ')
                {
                    glyph = this.font.obfuscate(glyph);
                }

                float width = glyph.getWidth();
                ResourceLocation glyphResource = glyph.getTextureLocation();
                float boldOffset;

                if (glyphResource != null)
                {
                    if (resource != glyphResource)
                    {
                        tessellator.draw();
                        this.textureManager.bindTexture(glyphResource);
                        buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                        resource = glyphResource;
                    }
                    boldOffset = bold ? glyph.getBoldXOffset() : 0.0F;
                    float boldShadow = dropShadow ? glyph.getShadowOffset() : 0.0F;
                    this.renderGlyph(glyph, bold, italic, boldOffset, x + boldShadow, y + boldShadow, buffer, red, green, blue, alpha);
                    width += boldOffset;
                }

                boldOffset = dropShadow ? 1.0F : 0.0F;

                if (strike)
                {
                    entries.add(new Entry(x + boldOffset - 1.0F, y + boldOffset + (float)this.FONT_HEIGHT / 2.0F, x + boldOffset + width, y + boldOffset + (float)this.FONT_HEIGHT / 2.0F - 1.0F, red, green, blue, alpha));
                }

                if (underline)
                {
                    entries.add(new Entry(x + boldOffset - 1.0F, y + boldOffset + (float)this.FONT_HEIGHT, x + boldOffset + width, y + boldOffset + (float)this.FONT_HEIGHT - 1.0F, red, green, blue, alpha));
                }
                x += width;
            }
        }

        tessellator.draw();

        if (!entries.isEmpty())
        {
            GlStateManager.disableTexture2D();
            buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_COLOR);

            for (Entry entry : entries)
            {
                entry.pipe(buffer);
            }
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }
        return x;
    }

    public static String color(int r, int g, int b)
    {
        return String.format("%c%c%c", (char)(ColoredFontRenderer.MARKER + (r & 255)), (char)(ColoredFontRenderer.MARKER + (g & 255)), (char)(ColoredFontRenderer.MARKER + (b & 255)));
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
        StringBuilder builder = new StringBuilder();
        int i = -1;
        int j = text.length();

        while ((i = text.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = text.charAt(i + 1);

                if (this.isFormatColor(c0))
                {
                    builder = new StringBuilder("\u00a7" + c0);
                }
                else if (this.isFormatSpecial(c0))
                {
                    builder.append("\u00a7").append(c0);
                }
                else if (c0 >= ColoredFontRenderer.MARKER && c0 <= ColoredFontRenderer.MARKER + 255)
                {
                    builder = new StringBuilder(String.format("%s%s%s", c0, text.charAt(i + 1), text.charAt(i + 2)));
                    i += 2;
                }
            }
        }
        return builder.toString();
    }

    private void renderGlyph(IGlyph glyph, boolean bold, boolean italic, float boldOffset, float xBoldShadow, float yBoldShadow, BufferBuilder buffer, float red, float green, float blue, float alpha)
    {
        glyph.render(this.textureManager, italic, xBoldShadow, yBoldShadow, buffer, red, green, blue, alpha);

        if (bold)
        {
            glyph.render(this.textureManager, italic, xBoldShadow + boldOffset, yBoldShadow, buffer, red, green, blue, alpha);
        }
    }
}