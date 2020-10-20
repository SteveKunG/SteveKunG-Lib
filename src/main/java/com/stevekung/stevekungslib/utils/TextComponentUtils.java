package com.stevekung.stevekungslib.utils;

import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import net.minecraft.util.text.*;

public class TextComponentUtils
{
    public static final Gson GSON = new Gson();
    private static final Gson GSON_BUILDER = new GsonBuilder().setPrettyPrinting().create();

    public static StringTextComponent component(String text)
    {
        return new StringTextComponent(text);
    }

    public static String componentString(String text)
    {
        return TextComponentUtils.component(text).getString();
    }

    public static IFormattableTextComponent formatted(String text)
    {
        return TextComponentUtils.component(text).deepCopy();
    }

    public static String formattedString(String text)
    {
        return TextComponentUtils.formatted(text).getString();
    }

    public static IFormattableTextComponent formatted(String text, TextFormatting... format)
    {
        return TextComponentUtils.formatted(text).mergeStyle(format);
    }

    public static IFormattableTextComponent formatted(String text, String hex)
    {
        IFormattableTextComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(component.getStyle().setColor(Color.fromHex(hex)));
    }

    public static IFormattableTextComponent formatted(String text, int color)
    {
        IFormattableTextComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(component.getStyle().setColor(Color.fromInt(color)));
    }

    public static IFormattableTextComponent formatted(String text, String hex, Style style)
    {
        IFormattableTextComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(style.setColor(Color.fromHex(hex)));
    }

    public static IFormattableTextComponent formatted(String text, int color, Style style)
    {
        IFormattableTextComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(style.setColor(Color.fromInt(color)));
    }

    public static String formattedString(String text, TextFormatting... format)
    {
        return TextComponentUtils.formatted(text, format).getString();
    }

    public static String formattedString(String text, String hex)
    {
        return TextComponentUtils.formatted(text, hex).getString();
    }

    public static String formattedString(String text, int color)
    {
        return TextComponentUtils.formatted(text, color).getString();
    }

    public static String fromJson(ITextComponent rawJson)
    {
        return ITextComponent.Serializer.getComponentFromJson(ITextComponent.Serializer.toJson(rawJson)).getString();
    }

    public static IFormattableTextComponent fromJson(String rawJson)
    {
        return ITextComponent.Serializer.getComponentFromJson(rawJson);
    }

    public static String fromJsonUnformatted(String rawJson)
    {
        return TextFormatting.getTextWithoutFormattingCodes(TextComponentUtils.fromJson(rawJson).getString());
    }

    public static String toJson(String text)
    {
        return ITextComponent.Serializer.toJson(TextComponentUtils.component(text));
    }

    public static void toJson(Object src, Appendable writer)
    {
        if (src != null)
        {
            toJson(src, src.getClass(), writer);
        }
        else
        {
            toJson(JsonNull.INSTANCE, writer);
        }
    }

    private static void toJson(Object src, Type typeOfSrc, Appendable writer)
    {
        try
        {
            JsonWriter jsonWriter = newJsonWriter(Streams.writerForAppendable(writer));
            GSON_BUILDER.toJson(src, typeOfSrc, jsonWriter);
        }
        catch (JsonIOException e)
        {
            throw new JsonIOException(e);
        }
    }

    private static JsonWriter newJsonWriter(Writer writer)
    {
        JsonWriter jsonWriter = new JsonWriter(writer);
        jsonWriter.setIndent("    ");
        jsonWriter.setSerializeNulls(GSON_BUILDER.serializeNulls());
        return jsonWriter;
    }
}