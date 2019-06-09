package stevekung.mods.stevekungslib.utils;

import java.util.function.Consumer;

import net.minecraft.util.Util;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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

    public static <T extends Event> void addListener(Consumer<T> consumer)
    {
        MinecraftForge.EVENT_BUS.addListener(consumer);
    }

    public static <T extends Event> void addModListener(Consumer<T> consumer)
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(consumer);
    }

    public static void registerModEventBus(Object target)
    {
        FMLJavaModLoadingContext.get().getModEventBus().register(target);
    }

    public static void registerConfig(ModConfig.Type type, ForgeConfigSpec.Builder builder)
    {
        ModLoadingContext.get().registerConfig(type, builder.build());
    }

    public static void registerGuiHandler(Object obj, IGuiHandler handler)
    {
        //NetworkRegistry.INSTANCE.registerGuiHandler(obj, handler);TODO
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
        Util.getOSType().openURI(url);
    }
}