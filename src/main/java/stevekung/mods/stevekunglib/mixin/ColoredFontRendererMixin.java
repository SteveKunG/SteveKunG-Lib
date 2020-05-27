package stevekung.mods.stevekunglib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.gui.FontRenderer;

@Mixin(FontRenderer.class)
public abstract class ColoredFontRendererMixin
{
    private boolean dropShadow;
    private int state = 0;
    private int redN;
    private int greenN;
    private int blueN;
    private static final int MARKER = 59136;

    @Shadow
    private float alpha;

    @Shadow
    protected abstract void setColor(float r, float g, float b, float a);

    @Shadow
    protected abstract void enableAlpha();

    @Shadow
    protected abstract void resetStyles();

    @Shadow
    protected abstract int renderString(String text, float x, float y, int color, boolean dropShadow);

    @Shadow
    private static boolean isFormatColor(char colorChar)
    {
        return false;
    }

    @Shadow
    private static boolean isFormatSpecial(char formatChar)
    {
        return false;
    }

    @Inject(method = "renderString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"))
    private int renderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable info)
    {
        this.dropShadow = dropShadow;
        return 0;
    }

    @Inject(method = "renderDefaultChar(IZ)F", at = @At("HEAD"))
    private float renderDefaultChar(int charac, boolean italic, CallbackInfoReturnable info)
    {
        if (charac >= MARKER && charac <= MARKER + 255)
        {
            int value = charac & 255;

            switch (this.state)
            {
            case 0:
                this.redN = value;
                break;
            case 1:
                this.greenN = value;
                break;
            case 2:
                this.blueN = value;
                break;
            default:
                this.setColor(1.0F, 1.0F, 1.0F, this.alpha);
                return 0.0F;
            }

            this.state = ++this.state % 3;
            int color = this.redN << 16 | this.greenN << 8 | this.blueN;

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }
            if (this.dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }
            this.setColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color >> 0 & 255) / 255.0F, this.alpha);
            return 0.0F;
        }
        if (this.state != 0)
        {
            this.state = 0;
            this.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        }
        return 0.0F;
    }

    @Inject(method = "renderUnicodeChar(CZ)F", at = @At("HEAD"))
    private float renderUnicodeChar(char charac, boolean italic, CallbackInfoReturnable info)
    {
        if (charac >= MARKER && charac <= MARKER + 255)
        {
            int value = charac & 255;

            switch (this.state)
            {
            case 0:
                this.redN = value;
                break;
            case 1:
                this.greenN = value;
                break;
            case 2:
                this.blueN = value;
                break;
            default:
                this.setColor(1.0F, 1.0F, 1.0F, this.alpha);
                return 0.0F;
            }

            this.state = ++this.state % 3;
            int color = this.redN << 16 | this.greenN << 8 | this.blueN;

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }
            if (this.dropShadow)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }
            this.setColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color >> 0 & 255) / 255.0F, this.alpha);
            return 0.0F;
        }
        if (this.state != 0)
        {
            this.state = 0;
            this.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        }
        return 0.0F;
    }

    @Overwrite
    public static String getFormatFromString(String text)
    {
        String s = "";
        int i = -1;
        int j = text.length();

        while ((i = text.indexOf(167, i + 1)) != -1)
        {
            if (i < j - 1)
            {
                char c0 = text.charAt(i + 1);

                if (isFormatColor(c0))
                {
                    s = "\u00a7" + c0;
                }
                else if (isFormatSpecial(c0))
                {
                    s = s + "\u00a7" + c0;
                }
                else if (c0 >= MARKER && c0 <= MARKER + 255)
                {
                    s = String.format("%s%s%s", c0, text.charAt(i + 1), text.charAt(i + 2));
                    i += 2;
                }
            }
        }
        return s;
    }
}