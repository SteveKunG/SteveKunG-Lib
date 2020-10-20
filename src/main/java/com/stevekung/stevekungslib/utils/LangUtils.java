package com.stevekung.stevekungslib.utils;

import net.minecraft.util.text.*;

public class LangUtils
{
    public static ITextComponent translate(String text)
    {
        return LangUtils.translate(text, new Object[0]);
    }

    public static ITextComponent translate(String key, Object... obj)
    {
        return new TranslationTextComponent(key, obj);
    }

    public static String translateString(String text)
    {
        return LangUtils.translate(text).getString();
    }

    public static IFormattableTextComponent formatted(String text, Object... obj)
    {
        return LangUtils.translate(text, obj).deepCopy();
    }

    public static String formattedString(String text)
    {
        return LangUtils.formatted(text).getString();
    }

    public static IFormattableTextComponent formatted(String text, TextFormatting format, Object... obj)
    {
        return LangUtils.formatted(text, obj).mergeStyle(format);
    }

    public static IFormattableTextComponent formatted(String text, String hex)
    {
        IFormattableTextComponent component = LangUtils.formatted(text);
        return component.setStyle(component.getStyle().setColor(Color.fromHex(hex)));
    }

    public static IFormattableTextComponent formatted(String text, int color)
    {
        IFormattableTextComponent component = LangUtils.formatted(text);
        return component.setStyle(component.getStyle().setColor(Color.fromInt(color)));
    }

    public static IFormattableTextComponent formatted(String text, String hex, Style style, Object... obj)
    {
        IFormattableTextComponent component = LangUtils.formatted(text, obj);
        return component.setStyle(style.setColor(Color.fromHex(hex)));
    }

    public static IFormattableTextComponent formatted(String text, int color, Style style, Object... obj)
    {
        IFormattableTextComponent component = LangUtils.formatted(text, obj);
        return component.setStyle(style.setColor(Color.fromInt(color)));
    }

    public static String formattedString(String text, TextFormatting format, Object... obj)
    {
        return LangUtils.formatted(text, format, obj).getString();
    }

    public static String formattedString(String text, String hex)
    {
        return LangUtils.formatted(text, hex).getString();
    }

    public static String formattedString(String text, int color)
    {
        return LangUtils.formatted(text, color).getString();
    }
}