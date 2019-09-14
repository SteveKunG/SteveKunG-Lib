package com.stevekung.stevekungslib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import net.minecraft.util.Util;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonUtils
{
    private static final ExecutorService POOL = Executors.newCachedThreadPool();

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

    public static String ticksToElapsedTime(int ticks)
    {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public static void openLink(String url)
    {
        Util.getOSType().openURI(url);
    }

    public static void execute(Runnable runnable)
    {
        CommonUtils.POOL.execute(runnable);
    }
}