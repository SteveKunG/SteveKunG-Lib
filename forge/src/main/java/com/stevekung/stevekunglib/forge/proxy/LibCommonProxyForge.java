package com.stevekung.stevekunglib.forge.proxy;

import com.stevekung.stevekunglib.core.SteveKunGLib;
import com.stevekung.stevekunglib.forge.config.SteveKunGLibConfig;
import com.stevekung.stevekunglib.forge.utils.ForgeCommonUtils;
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
        SteveKunGLib.LOGGER.setDebug(SteveKunGLibConfig.GENERAL.enableDebugLog.get());
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }
}