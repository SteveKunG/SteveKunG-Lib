package com.stevekung.stevekungslib.mixin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.blaze3d.platform.GlStateManager;
import com.stevekung.stevekungslib.utils.ColorUtils;
import com.stevekung.stevekungslib.utils.client.GLConstants;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer
{
    private int state = 0;
    private int red;
    private int green;
    private int blue;

    @Shadow
    protected abstract void renderGlyph(TexturedGlyph texturedGlyph, boolean bold, boolean italic, float boldOffset, float shadowX, float shadowY, BufferBuilder bufferBuilder, float red, float green, float blue, float alpha);

    @Shadow
    @Final
    private Font font;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Overwrite
    private float renderStringAtPos(String text, float x, float y, int color, boolean dropShadow)
    {
        float shadowAlpha = dropShadow ? 0.25F : 1.0F;
        float redRaw = (color >> 16 & 255) / 255.0F * shadowAlpha;
        float greenRaw = (color >> 8 & 255) / 255.0F * shadowAlpha;
        float blueRaw = (color & 255) / 255.0F * shadowAlpha;
        float red = redRaw;
        float green = greenRaw;
        float blue = blueRaw;
        float alpha = (color >> 24 & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        ResourceLocation identifier = null;
        builder.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        boolean obfuscated = false;
        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean strikethrough = false;
        List<MixinFontRenderer.Entry> list = new ArrayList<>();

        for (int i = 0; i < text.length(); ++i)
        {
            char charac = text.charAt(i);

            if (charac >= ColorUtils.CUSTOM_COLOR_MARKER && charac <= ColorUtils.CUSTOM_COLOR_MARKER + 255)
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
                    return 0.0F;
                }
                this.state = ++this.state % 3;
                color = this.red << 16 | this.green << 8 | this.blue;
                red = (color >> 16 & 255) / 255.0F * shadowAlpha;
                green = (color >> 8 & 255) / 255.0F * shadowAlpha;
                blue = (color & 255) / 255.0F * shadowAlpha;
            }

            if (charac == 167 && i + 1 < text.length())
            {
                TextFormatting formatting = TextFormatting.fromFormattingCode(text.charAt(i + 1));

                if (formatting != null)
                {
                    if (formatting.isNormalStyle())
                    {
                        obfuscated = false;
                        bold = false;
                        strikethrough = false;
                        underline = false;
                        italic = false;
                        red = redRaw;
                        green = greenRaw;
                        blue = blueRaw;
                    }

                    if (formatting.getColor() != null)
                    {
                        int value = formatting.getColor();
                        red = (value >> 16 & 255) / 255.0F * shadowAlpha;
                        green = (value >> 8 & 255) / 255.0F * shadowAlpha;
                        blue = (value & 255) / 255.0F * shadowAlpha;
                    }
                    else if (formatting == TextFormatting.OBFUSCATED)
                    {
                        obfuscated = true;
                    }
                    else if (formatting == TextFormatting.BOLD)
                    {
                        bold = true;
                    }
                    else if (formatting == TextFormatting.STRIKETHROUGH)
                    {
                        strikethrough = true;
                    }
                    else if (formatting == TextFormatting.UNDERLINE)
                    {
                        underline = true;
                    }
                    else if (formatting == TextFormatting.ITALIC)
                    {
                        italic = true;
                    }
                }
                ++i;
            }
            else
            {
                IGlyph glyph = this.font.findGlyph(charac);
                TexturedGlyph texturedGlyph = obfuscated && charac != ' ' ? this.font.obfuscate(glyph) : this.font.getGlyph(charac);
                ResourceLocation id = texturedGlyph.getTextureLocation();
                float boldOffset;
                float shadowOffset;

                if (id != null)
                {
                    if (identifier != id)
                    {
                        tessellator.draw();
                        this.textureManager.bindTexture(id);
                        builder.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                        identifier = id;
                    }
                    boldOffset = bold ? glyph.getBoldOffset() : 0.0F;
                    shadowOffset = dropShadow ? glyph.getShadowOffset() : 0.0F;
                    this.renderGlyph(texturedGlyph, bold, italic, boldOffset, x + shadowOffset, y + shadowOffset, builder, red, green, blue, alpha);
                }

                boldOffset = glyph.getAdvance(bold);
                shadowOffset = dropShadow ? 1.0F : 0.0F;

                if (strikethrough)
                {
                    list.add(new MixinFontRenderer.Entry(x + shadowOffset - 1.0F, y + shadowOffset + 4.5F, x + shadowOffset + boldOffset, y + shadowOffset + 4.5F - 1.0F, red, green, blue, alpha));
                }
                if (underline)
                {
                    list.add(new MixinFontRenderer.Entry(x + shadowOffset - 1.0F, y + shadowOffset + 9.0F, x + shadowOffset + boldOffset, y + shadowOffset + 9.0F - 1.0F, red, green, blue, alpha));
                }
                x += boldOffset;
            }
        }

        tessellator.draw();

        if (!list.isEmpty())
        {
            GlStateManager.disableTexture();
            builder.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_COLOR);
            Iterator<MixinFontRenderer.Entry> it = list.iterator();

            while (it.hasNext())
            {
                MixinFontRenderer.Entry rectangle = it.next();
                rectangle.pipe(builder);
            }
            tessellator.draw();
            GlStateManager.enableTexture();
        }
        return x;
    }

    @OnlyIn(Dist.CLIENT)
    static class Entry
    {
        protected final float xMin;
        protected final float yMin;
        protected final float xMax;
        protected final float yMax;
        protected final float red;
        protected final float green;
        protected final float blue;
        protected final float alpha;

        private Entry(float xMin, float yMin, float xMax, float yMax, float red, float green, float blue, float alpha)
        {
            this.xMin = xMin;
            this.yMin = yMin;
            this.xMax = xMax;
            this.yMax = yMax;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public void pipe(BufferBuilder builder)
        {
            builder.pos(this.xMin, this.yMin, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            builder.pos(this.xMax, this.yMin, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            builder.pos(this.xMax, this.yMax, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
            builder.pos(this.xMin, this.yMax, 0.0D).color(this.red, this.green, this.blue, this.alpha).endVertex();
        }
    }
}