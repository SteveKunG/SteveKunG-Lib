package stevekung.mods.stevekunglib.utils;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class LangUtils
{
    public static String translate(String key)
    {
        return LangUtils.translate(key, new Object[0]);
    }

    public static String translate(String key, Object... obj)
    {
        return new TextComponentTranslation(key, obj).getFormattedText();
    }

    public static ITextComponent translateComponent(String key)
    {
        return LangUtils.translateComponent(key, new Object[0]);
    }

    public static ITextComponent translateComponent(String key, Object... obj)
    {
        return new TextComponentTranslation(key, obj);
    }
}