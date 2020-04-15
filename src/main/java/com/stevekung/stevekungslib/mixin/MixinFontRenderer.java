package com.stevekung.stevekungslib.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.*;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stevekung.stevekungslib.utils.ColorUtils;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.text.TextFormatting;

@Mixin(value = FontRenderer.class, priority = 100)
public abstract class MixinFontRenderer
{
    private int state;
    private int red;
    private int green;
    private int blue;

    @Shadow
    protected abstract void drawGlyph(TexturedGlyph texturedGlyph, boolean bold, boolean italic, float boldOffset, float shadowX, float shadowY, Matrix4f matrix4f, IVertexBuilder builder, float red, float green, float blue, float alpha, int p_228077_13_);

    @Shadow
    @Final
    @Mutable
    private Font font;

    @Shadow
    @Final
    @Mutable
    private TextureManager textureManager;

    /**
     * @reason No way to modify r,g,b before renderGlyph()
     * @author SteveKunG
     */
    @Overwrite
    private float renderStringAtPos(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix4f, IRenderTypeBuffer renderBuffer, boolean depthRender, int p_228081_9_, int p_228081_10_)
    {
        float shadowAlpha = dropShadow ? 0.25F : 1.0F;
        float redRaw = (color >> 16 & 255) / 255.0F * shadowAlpha;
        float greenRaw = (color >> 8 & 255) / 255.0F * shadowAlpha;
        float blueRaw = (color & 255) / 255.0F * shadowAlpha;
        float x2 = x;
        float red = redRaw;
        float green = greenRaw;
        float blue = blueRaw;
        float alpha = (color >> 24 & 255) / 255.0F;
        boolean obfuscated = false;
        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean strikethrough = false;
        List<TexturedGlyph.Effect> list = Lists.newArrayList();

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
                        int j = formatting.getColor();
                        red = (j >> 16 & 255) / 255.0F * shadowAlpha;
                        green = (j >> 8 & 255) / 255.0F * shadowAlpha;
                        blue = (j & 255) / 255.0F * shadowAlpha;
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
                IGlyph iglyph = this.font.findGlyph(charac);
                TexturedGlyph texturedGlyph = obfuscated && charac != ' ' ? this.font.obfuscate(iglyph) : this.font.getGlyph(charac);

                if (!(texturedGlyph instanceof EmptyGlyph))
                {
                    float boldOffset = bold ? iglyph.getBoldOffset() : 0.0F;
                    float shadowOffset = dropShadow ? iglyph.getShadowOffset() : 0.0F;
                    IVertexBuilder ivertexbuilder = renderBuffer.getBuffer(texturedGlyph.getRenderType(depthRender));
                    this.drawGlyph(texturedGlyph, bold, italic, boldOffset, x2 + shadowOffset, y + shadowOffset, matrix4f, ivertexbuilder, red, green, blue, alpha, p_228081_10_);
                }

                float advance = iglyph.getAdvance(bold);
                float shadowOffset = dropShadow ? 1.0F : 0.0F;

                if (strikethrough)
                {
                    list.add(new TexturedGlyph.Effect(x2 + shadowOffset - 1.0F, y + shadowOffset + 4.5F, x2 + shadowOffset + advance, y + shadowOffset + 4.5F - 1.0F, -0.01F, red, green, blue, alpha));
                }
                if (underline)
                {
                    list.add(new TexturedGlyph.Effect(x2 + shadowOffset - 1.0F, y + shadowOffset + 9.0F, x2 + shadowOffset + advance, y + shadowOffset + 9.0F - 1.0F, -0.01F, red, green, blue, alpha));
                }
                x2 += advance;
            }
        }

        if (p_228081_9_ != 0)
        {
            float f11 = (p_228081_9_ >> 24 & 255) / 255.0F;
            float f12 = (p_228081_9_ >> 16 & 255) / 255.0F;
            float f13 = (p_228081_9_ >> 8 & 255) / 255.0F;
            float f14 = (p_228081_9_ & 255) / 255.0F;
            list.add(new TexturedGlyph.Effect(x - 1.0F, y + 9.0F, x2 + 1.0F, y - 1.0F, 0.01F, f12, f13, f14, f11));
        }

        if (!list.isEmpty())
        {
            TexturedGlyph texturedglyph1 = this.font.getWhiteGlyph();
            IVertexBuilder ivertexbuilder1 = renderBuffer.getBuffer(texturedglyph1.getRenderType(depthRender));

            for (TexturedGlyph.Effect effect : list)
            {
                texturedglyph1.renderEffect(effect, matrix4f, ivertexbuilder1, p_228081_10_);
            }
        }
        return x2;
    }

    // TODO If @ModifyVariable support with @Inject
    /*@Inject(method = "renderStringAtPos(Ljava/lang/String;FFIZ)F", at = @At(value = "INVOKE", target = "java/lang/String.length()I", ordinal = 1, remap = false, shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderStringAtPos(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Float> info, float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7,
            Tessellator tessellator, BufferBuilder bufferbuilder, ResourceLocation resourcelocation, boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, List<FontRenderer.Entry> list,
            int i, char c0)
    {
        if (c0 >= ColorUtils.CUSTOM_COLOR_MARKER && c0 <= ColorUtils.CUSTOM_COLOR_MARKER + 255)
        {
            int value = c0 & 255;

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
                info.setReturnValue(0.0F);
            }
            this.state = ++this.state % 3;
            color = this.red << 16 | this.green << 8 | this.blue;
            f4 = (color >> 16 & 255) / 255.0F * f;
            f5 = (color >> 8 & 255) / 255.0F * f;
            f6 = (color & 255) / 255.0F * f;
        }
    }*/
}