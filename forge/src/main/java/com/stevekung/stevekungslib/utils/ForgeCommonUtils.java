package com.stevekung.stevekungslib.utils;

import java.io.File;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

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

    @Deprecated
    public static void registerConfig(ModConfig.Type type, ForgeConfigSpec.Builder builder)
    {
        ModLoadingContext.get().registerConfig(type, builder.build());
    }

    public static void registerConfig(ModConfig.Type type, ForgeConfigSpec builder)
    {
        ModLoadingContext.get().registerConfig(type, builder);
    }

    public static void registerConfigScreen(BiFunction<Minecraft, Screen, Screen> screenFunction)
    {
        if (!ModList.get().isLoaded("configured"))
        {
            //TODO
            //            ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, new ConfigGuiHandler.ConfigGuiFactory((mc, parent) -> new VideoSettingsScreen(parent, mc.options)));
        }
    }

    public static void registerClientOnly()
    {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (remote, isServer) -> true));
    }

    public static Screen openConfigFile(Screen parent, String modId, ModConfig.Type type)
    {
        var configPath = ConfigTracker.INSTANCE.getConfigFileName(modId, type);

        if (configPath == null)
        {
            SteveKunGLib.LOGGER.error("Couldn't open {} config!", modId);
            return parent;
        }
        var config = new File(configPath);
        Util.getPlatform().openUri(config.toURI());
        return parent;
    }
}