package stevekung.mods.stevekunglib.utils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.awt.*;
import java.net.URI;

public class CommonUtils
{
    public static void registerEventHandler(Object event)
    {
        MinecraftForge.EVENT_BUS.register(event);
    }

    public static void unregisterEventHandler(Object event)
    {
        MinecraftForge.EVENT_BUS.unregister(event);
    }

    public static void registerGuiHandler(Object obj, IGuiHandler handler)
    {
        //        NetworkRegistry.INSTANCE.registerGuiHandler(obj, handler);TODO
    }

    public static String ticksToElapsedTime(int ticks)
    {
        int i = ticks / 20;
        int j = i / 60;
        i = i % 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
    }

    public static void openLink(String url)
    {
        try
        {
            URI uri = new URI(url);
            Desktop.getDesktop().browse(uri);
        }
        catch (Exception e)
        {
            LoggerSL.info("Couldn't open link {}", url);
            e.printStackTrace();
        }
    }
}