package com.stevekung.stevekungslib.proxy;

import com.stevekung.stevekungslib.config.SteveKunGsLibConfig;
import com.stevekung.stevekungslib.core.SteveKunGLib;
import com.stevekung.stevekungslib.utils.CommonUtils;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class LibCommonProxy
{
    public void init()
    {
        CommonUtils.registerModEventBus(this);
        CommonUtils.addModListener(this::commonSetup);
        CommonUtils.addModListener(this::clientSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event)
    {
        SteveKunGLib.LOGGER.setDebug(SteveKunGsLibConfig.GENERAL.enableDebugLog.get());
    }

    public void clientSetup(FMLClientSetupEvent event)
    {
    }
}