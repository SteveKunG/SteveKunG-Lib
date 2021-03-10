package com.stevekung.stevekungslib.utils;

import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.*;

public class TextComponentUtils
{
    public static final Gson GSON = new Gson();
    private static final Gson GSON_BUILDER = new GsonBuilder().setPrettyPrinting().create();

    public static TextComponent component(String text)
    {
        return new TextComponent(text);
    }

    public static String componentString(String text)
    {
        return TextComponentUtils.component(text).getString();
    }

    public static MutableComponent formatted(String text)
    {
        return TextComponentUtils.component(text).copy();
    }

    public static String formattedString(String text)
    {
        return TextComponentUtils.formatted(text).getString();
    }

    public static MutableComponent formatted(String text, ChatFormatting... format)
    {
        return TextComponentUtils.formatted(text).withStyle(format);
    }

    public static MutableComponent formatted(String text, String hex)
    {
        MutableComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(component.getStyle().withColor(TextColor.parseColor(hex)));
    }

    public static MutableComponent formatted(String text, int color)
    {
        MutableComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(component.getStyle().withColor(TextColor.fromRgb(color)));
    }

    public static MutableComponent formatted(String text, String hex, Style style)
    {
        MutableComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(style.withColor(TextColor.parseColor(hex)));
    }

    public static MutableComponent formatted(String text, int color, Style style)
    {
        MutableComponent component = TextComponentUtils.formatted(text);
        return component.setStyle(style.withColor(TextColor.fromRgb(color)));
    }

    public static String formattedString(String text, ChatFormatting... format)
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

    public static String fromJson(Component rawJson)
    {
        return Component.Serializer.fromJson(Component.Serializer.toJson(rawJson)).getString();
    }

    public static MutableComponent fromJson(String rawJson)
    {
        return Component.Serializer.fromJson(rawJson);
    }

    public static String fromJsonUnformatted(String rawJson)
    {
        return ChatFormatting.stripFormatting(TextComponentUtils.fromJson(rawJson).getString());
    }

    public static String toJson(String text)
    {
        return Component.Serializer.toJson(TextComponentUtils.component(text));
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