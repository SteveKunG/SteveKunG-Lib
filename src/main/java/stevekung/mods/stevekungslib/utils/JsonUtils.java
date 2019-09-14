package stevekung.mods.stevekungslib.utils;

import java.io.Writer;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import stevekung.mods.stevekungslib.client.event.ClientEventHandler;
import stevekung.mods.stevekungslib.utils.client.ClientUtils;

public class JsonUtils
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Style STYLE = new Style();
    public static final Style BLACK = JsonUtils.STYLE.setColor(TextFormatting.BLACK);
    public static final Style DARK_BLUE = JsonUtils.STYLE.setColor(TextFormatting.DARK_BLUE);
    public static final Style DARK_GREEN = JsonUtils.STYLE.setColor(TextFormatting.DARK_GREEN);
    public static final Style DARK_AQUA = JsonUtils.STYLE.setColor(TextFormatting.DARK_AQUA);
    public static final Style DARK_RED = JsonUtils.STYLE.setColor(TextFormatting.DARK_RED);
    public static final Style DARK_PURPLE = JsonUtils.STYLE.setColor(TextFormatting.DARK_PURPLE);
    public static final Style GOLD = JsonUtils.STYLE.setColor(TextFormatting.GOLD);
    public static final Style GRAY = JsonUtils.STYLE.setColor(TextFormatting.GRAY);
    public static final Style DARK_GRAY = JsonUtils.STYLE.setColor(TextFormatting.DARK_GRAY);
    public static final Style BLUE = JsonUtils.STYLE.setColor(TextFormatting.BLUE);
    public static final Style GREEN = JsonUtils.STYLE.setColor(TextFormatting.GREEN);
    public static final Style AQUA = JsonUtils.STYLE.setColor(TextFormatting.AQUA);
    public static final Style RED = JsonUtils.STYLE.setColor(TextFormatting.RED);
    public static final Style LIGHT_PURPLE = JsonUtils.STYLE.setColor(TextFormatting.LIGHT_PURPLE);
    public static final Style YELLOW = JsonUtils.STYLE.setColor(TextFormatting.YELLOW);
    public static final Style WHITE = JsonUtils.STYLE.setColor(TextFormatting.WHITE);
    public static final Style RESET = JsonUtils.STYLE.setColor(TextFormatting.RESET);

    public static ITextComponent rawTextToJson(String raw)
    {
        ITextComponent json = create("Cannot parse json format! ").setStyle(RED);

        try
        {
            json = ITextComponent.Serializer.fromJson("[{" + raw + "}]");
        }
        catch (JsonParseException e)
        {
            if (ClientEventHandler.ticks % 300 == 0)
            {
                ClientUtils.printClientMessage(create(e.getMessage()).setStyle(RED));
            }
        }
        return json;
    }

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