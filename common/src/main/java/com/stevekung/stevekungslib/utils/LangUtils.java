package com.stevekung.stevekungslib.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

public class LangUtils
{
    public static Component translate(String text)
    {
        return LangUtils.translate(text, new Object[0]);
    }

    public static Component translate(String key, Object... obj)
    {
        return new TranslatableComponent(key, obj);
    }

    public static String translateString(String text)
    {
        return LangUtils.translate(text).getString();
    }

    public static MutableComponent formatted(String text, Object... obj)
    {
        return LangUtils.translate(text, obj).copy();
    }

    public static String formattedString(String text)
    {
        return LangUtils.formatted(text).getString();
    }

    public static MutableComponent formatted(String text, ChatFormatting format, Object... obj)
    {
        return LangUtils.formatted(text, obj).withStyle(format);
    }

    public static MutableComponent formatted(String text, String hex)
    {
        var component = LangUtils.formatted(text);
        return component.setStyle(component.getStyle().withColor(TextColor.parseColor(hex)));
    }

    public static MutableComponent formatted(String text, int color)
    {
        var component = LangUtils.formatted(text);
        return component.setStyle(component.getStyle().withColor(TextColor.fromRgb(color)));
    }

    public static MutableComponent formatted(String text, String hex, Style style, Object... obj)
    {
        var component = LangUtils.formatted(text, obj);
        return component.setStyle(style.withColor(TextColor.parseColor(hex)));
    }

    public static MutableComponent formatted(String text, int color, Style style, Object... obj)
    {
        var component = LangUtils.formatted(text, obj);
        return component.setStyle(style.withColor(TextColor.fromRgb(color)));
    }

    public static String formattedString(String text, ChatFormatting format, Object... obj)
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