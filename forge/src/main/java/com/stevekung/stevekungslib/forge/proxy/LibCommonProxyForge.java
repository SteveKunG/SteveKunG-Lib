package com.stevekung.stevekungslib.forge.proxy;

import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.forge.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.forge.utils.ForgeCommonUtils;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class LibCommonProxyForge
{
    public void init()
    {
        ForgeCommonUtils.registerModEventBus(this);
        ForgeCommonUtils.addModListener(this::commonSetup);
        ForgeCommonUtils.addModListener(this::clientSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER.setDebug(SteveKunGsLibConfig.GENERAL.enableDebugLog.get());
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }
}