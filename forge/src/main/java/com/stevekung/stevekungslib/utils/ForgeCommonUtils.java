package com.stevekung.stevekungslib.utils;

import java.io.File;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

public class ForgeCommonUtils
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

    public static boolean post(Event event)
    {
        return MinecraftForge.EVENT_BUS.post(event);
    }

    public static <T extends Event> void addModListener(Consumer<T> consumer)
    {
        ForgeCommonUtils.getModEventBus().addListener(consumer);
    }

    public static void registerModEventBus(Object target)
    {
        ForgeCommonUtils.getModEventBus().register(target);
    }

    public static IEventBus getModEventBus()
    {
        return FMLJavaModLoadingContext.get().getModEventBus();
    }

    public static void registerConfig(ModConfig.Type type, ForgeConfigSpec.Builder builder)
    {
        ModLoadingContext.get().registerConfig(type, builder.build());
    }

    public static void registerConfigScreen(Supplier<BiFunction<Minecraft, Screen, Screen>> supplier)
    {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, supplier);
    }

    public static void registerClientOnly()
    {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (s, b) -> true));
    }

    public static Screen openConfigFile(Screen parent, String modId, ModConfig.Type type)
    {
        String configPath = ConfigTracker.INSTANCE.getConfigFileName(modId, type);

        if (configPath == null)
        {
            SteveKunGLib.LOGGER.error("Couldn't open {} config!", modId);
            return parent;
        }
        File config = new File(configPath);
        Util.getPlatform().openUri(config.toURI());
        return parent;
    }
}