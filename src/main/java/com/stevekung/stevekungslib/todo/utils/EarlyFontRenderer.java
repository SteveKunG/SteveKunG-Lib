package com.stevekung.stevekungslib.todo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;
import com.stevekung.stevekungslib.utils.client.GLConstants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.FallbackResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class EarlyFontRenderer
{
    private Optional<TextureManager> tm = Optional.empty();
    private final PreloadedTexture pt;
    private final ResourceLocation fontTexture;
    private final int char_height = 8;
    private final int row_height = 9;
    private final byte[] char_widths = new byte[] {4, 2, 4, 6, 6, 6, 6, 2, 4, 4, 4, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 4, 2, 4, 7, 9};
    private final int[] char_positions_x = new int[96], char_positions_y = new int[96];
    private final Random rand = new Random();
    private static final EarlyFontRenderer INSTANCE = new EarlyFontRenderer(new ResourceLocation("textures/font/ascii.png"));

    public static EarlyFontRenderer get()
    {
        return INSTANCE;
    }

    public EarlyFontRenderer(ResourceLocation rl)
    {
        this.fontTexture = rl;
        this.pt = new PreloadedTexture(this.fontTexture);

        for (int i = 0; i < 96; i++)
        {
            this.char_positions_x[i] = 8 * (i % 16);
            this.char_positions_y[i] = 8 * (i / 16 + 2);
        }
    }

    public void drawString(int x, int y, String s, int color)
    {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        boolean bold = false;
        boolean italic = false;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;

        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.enableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        Tessellator tessellator = Tessellator.getInstance();
        GlStateManager.bindTexture(this.pt.getGlTextureId());
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        List<Line> lines = new LinkedList<>();
        int sx = x;

        for(int i = 0; i < s.length(); i++)
        {
            char ch;

            switch (ch = s.charAt(i))
            {
            case '\n':
                x = sx;
                y += this.row_height;
                break;
            case '\t':
                int tab_width = this.char_widths[' ' - 32] * 4;
                x = (x + tab_width - 1) / tab_width * tab_width; //round up to nearest multiple
                break;
            case '?':
                if (1 + i >= s.length())
                {
                    break;
                }

                ch = s.charAt(i + 1);
                TextFormatting format = TextFormatting.fromFormattingCode(ch);

                if (format != null)
                {
                    if (format.getColor() != null)
                    {
                        red = (format.getColor() >> 16 & 0xFF) / 255.0F;
                        green = (format.getColor() >> 8 & 0xFF) / 255.0F;
                        blue = (format.getColor() & 0xFF) / 255.0F;
                        bold = false;
                        italic = false;
                        underlined = false;
                        strikethrough = false;
                        obfuscated = false;
                    }
                    else
                    {
                        switch (format)
                        {
                        case BOLD:
                            bold = true;
                            break;
                        case ITALIC:
                            italic = true;
                            break;
                        case OBFUSCATED:
                            obfuscated = true;
                            break;
                        case STRIKETHROUGH:
                            strikethrough = true;
                            break;
                        case UNDERLINE:
                            underlined = true;
                            break;
                        case RESET:
                        default:
                            red = (color >> 16 & 0xFF) / 255.0F;
                            green = (color >> 8 & 0xFF) / 255.0F;
                            blue = (color & 0xFF) / 255.0F;
                            bold = false;
                            italic = false;
                            underlined = false;
                            strikethrough = false;
                            obfuscated = false;
                            break;
                        }
                    }
                    i++;
                    break;
                }
            default:
                if (ch < 32 | ch >= 127)
                {
                    ch = '?';
                }
                if (obfuscated)
                {
                    int cwc = this.char_widths[ch - 32];
                    ch = (char) (this.rand.ints(0, 95).filter(v -> this.char_widths[v] == cwc).findAny().orElse(0) + 32);
                }

                int cx = this.char_positions_x[ch - 32];
                int cy = this.char_positions_y[ch - 32];
                int boldWidth = this.char_widths[ch - 32] + (bold ? 1 : 0);
                this.blitWithColor(x, y, cx, cy, this.char_widths[ch - 32], this.char_height, red, green, blue, alpha, italic, buffer);

                if (bold)
                {
                    this.blitWithColor(x + 1, y, cx, cy, this.char_widths[ch - 32], this.char_height, red, green, blue, alpha, italic, buffer);
                }
                if (underlined)
                {
                    lines.add(new Line(x, y + 8, boldWidth, 1, red, green, blue, alpha));
                }
                if (strikethrough)
                {
                    lines.add(new Line(x, y + 3.5, boldWidth, 1, red, green, blue, alpha));
                }
                x += boldWidth;
                break;
            }
        }

        tessellator.draw();

        if (!lines.isEmpty())
        {
            GlStateManager.disableTexture();
            buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_COLOR);

            for (Line line : lines)
            {
                buffer.pos(line.x, line.y, 0).color(line.r, line.g, line.b, line.a).endVertex();
                buffer.pos(line.x, line.y + line.h, 0).color(line.r, line.g, line.b, line.a).endVertex();
                buffer.pos(line.x + line.w, line.y + line.h, 0).color(line.r, line.g, line.b, line.a).endVertex();
                buffer.pos(line.x + line.w, line.y, 0).color(line.r, line.g, line.b, line.a).endVertex();
            }
            tessellator.draw();
            GlStateManager.enableTexture();
        }
    }

    private void blitWithColor(int x, int y, int tx, int ty, int tw, int th, float red, float green, float blue, float alpha, boolean italic, BufferBuilder buffer){
        buffer.pos(x+(italic?1:0), y, 0.0D).tex(tx/128., ty/128.).color(red, green, blue, alpha).endVertex();
        buffer.pos(x+(italic?-1:0), y+th, 0.0D).tex(tx/128., (ty+th)/128.).color(red, green, blue, alpha).endVertex();
        buffer.pos(x+tw+(italic?-1:0), y+th, 0.0D).tex((tx+tw)/128., (ty+th)/128.).color(red, green, blue, alpha).endVertex();
        buffer.pos(x+tw+(italic?1:0), y, 0.0D).tex((tx+tw)/128., ty/128.).color(red, green, blue, alpha).endVertex();
    }

    public void loadEarlyTexture()
    {
        try
        {
            this.pt.loadTexture(new FallbackResourceManager(ResourcePackType.CLIENT_RESOURCES));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static class PreloadedTexture extends SimpleTexture
    {
        public PreloadedTexture(ResourceLocation rl)
        {
            super(rl);
        }

        @Override
        protected SimpleTexture.TextureData func_215246_b(IResourceManager unused)
        {
            Minecraft mc = Minecraft.getInstance();
            VanillaPack pack = mc.getPackFinder().getVanillaPack();

            try (InputStream inputstream = pack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, this.textureLocation))
            {
                return new SimpleTexture.TextureData(null, NativeImage.read(inputstream));
            }
            catch (IOException e)
            {
                return new SimpleTexture.TextureData(e);
            }
        }
    }

    private static class Line
    {
        double x, y, w, h;
        float r, g, b, a;

        public Line(double x, double y, double w, double h, float r, float g, float b, float a)
        {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
        }
    }

}