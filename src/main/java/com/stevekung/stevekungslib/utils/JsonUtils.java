package com.stevekung.stevekungslib.utils;

import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class JsonUtils
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static StringTextComponent create(String text)
    {
        return new StringTextComponent(text);
    }

    public static ClickEvent click(ClickEvent.Action action, String url)
    {
        return new ClickEvent(action, url);
    }

    public static HoverEvent hover(HoverEvent.Action action, ITextComponent text)
    {
        return new HoverEvent(action, text);
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
            GSON.toJson(src, typeOfSrc, jsonWriter);
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
        jsonWriter.setSerializeNulls(GSON.serializeNulls());
        return jsonWriter;
    }
}